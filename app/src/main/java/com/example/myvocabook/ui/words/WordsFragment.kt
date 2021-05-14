package com.example.myvocabook.ui.words

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvocabook.R
import com.example.myvocabook.database.AppDataBase
import com.example.myvocabook.ui.voca.VocaActivity
import com.example.myvocabook.ui.web.WebViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.InvocationTargetException

class WordsFragment : Fragment() {
    private val days = arrayListOf<String>()
    val wordsViewModel: WordsViewModel by viewModels()
    lateinit var adapter: GridAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_words, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager =
            GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        adapter = GridAdapter(context, days)
        adapter.itemClickListener = object : GridAdapter.OnItemClickListener {
            override fun onItemClick(
                holder: GridAdapter.GridViewHolder,
                view: View,
                data: String,
                position: Int
            ) {
                /*val day = adapter.days[position]
                wordsViewModel.setLiveData(day)

                val fragment = childFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val vocaFragment = VocaFragment()
                fragment.replace(R.id.framelayout, vocaFragment)
                fragment.commit()*/
                val day = adapter.days[position]
                val intent = Intent(context, VocaActivity::class.java)
                intent.putExtra("day", day)
                startActivity(intent)
            }
        }
        recyclerView.adapter = adapter
        CoroutineScope(Dispatchers.IO).launch {
            adapter.days.clear()
            try {
                val output = AppDataBase.getInstance(requireContext())
                    .vocabularyDao()
                    .getDay()
                for (voca in output) {
                    adapter.days.add(voca.day.toString())
                }
            } catch (e: InvocationTargetException) {
                Log.d("db_error", "db_error in words fragment")
            }
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
            }
        }

    }
}