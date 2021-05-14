package com.example.myvocabook.ui.voca

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvocabook.R
import com.example.myvocabook.database.AppDataBase
import com.example.myvocabook.ui.web.WebFragment
import com.example.myvocabook.ui.web.WebViewModel
import kotlinx.coroutines.*
import java.util.*

class VocaActivity : AppCompatActivity() {
    var day = "day1"
    var words: ArrayList<VocaData> = ArrayList()
    val webViewModel: WebViewModel by viewModels<WebViewModel>()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: VocaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voca)
        initData()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView4)
        recyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        adapter = VocaAdapter(this, words)
        adapter.itemClickListener = object : VocaAdapter.OnItemClickListener {
            override fun onItemClick(
                holder: VocaAdapter.VocaViewHolder,
                view: View,
                data: VocaData,
                position: Int
            ) {
                if (holder.meanLayout.visibility == View.VISIBLE) {
                    holder.meanLayout.visibility = View.GONE
                    data.isOpen = false
                } else {
                    holder.meanLayout.visibility = View.VISIBLE
                    data.isOpen = true
                }
                holder.exampleBtn.setOnClickListener {
                    val text = holder.wordView.text.toString()
                    webViewModel.setLiveData(text)

                    val fragment = supportFragmentManager.beginTransaction()
                    val webFragment = WebFragment()
                    fragment.replace(R.id.framelayout3, webFragment)
                    fragment.addToBackStack(null)
                    fragment.commit()
                }
            }
        }
        recyclerView.adapter = adapter
    }

    private fun initData() {
        val intent = intent
        day = intent.getStringExtra("day").toString()
        CoroutineScope(Dispatchers.IO).launch {
            delay(10L)
            adapter.vocas.clear()
            words.clear()
            val output = AppDataBase.getInstance(baseContext)
                .vocabularyDao().getWordFromDay(day)
            for (voca in output) {
                /*adapter.vocas.add(
                    VocaData(
                        voca.index!!.toLong(),
                        voca.day.toString(),
                        voca.word.toString(),
                        voca.meaning.toString(),
                        voca.isBookmark
                    )
                )*/
                words.add(
                    VocaData(
                        voca.index!!.toLong(),
                        voca.day.toString(),
                        voca.word.toString(),
                        voca.meaning.toString(),
                        voca.isBookmark
                    )
                )
            }
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
            }
        }
    }
}