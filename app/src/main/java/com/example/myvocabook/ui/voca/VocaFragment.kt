package com.example.myvocabook.ui.voca

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvocabook.AppDataBase
import com.example.myvocabook.R
import com.example.myvocabook.ui.words.WordsViewModel
import kotlinx.coroutines.*
import java.lang.reflect.InvocationTargetException
import java.util.*

class VocaFragment : Fragment() {

    var words: ArrayList<VocaData> = ArrayList()
    val wordsViewModel: WordsViewModel by viewModels({ requireParentFragment() })
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: VocaAdapter
    var day = "day1"
    lateinit var result: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        result = arguments?.getString("day").toString()
        return inflater.inflate(R.layout.fragment_voca, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView = view.findViewById<RecyclerView>(R.id.vocaRecyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = VocaAdapter(context, words)
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
            }
        }
        recyclerView.adapter = adapter
        CoroutineScope(Dispatchers.Main).launch {
            wordsViewModel.selectedDay.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                Log.d("day", it)
                day = it
            })
        }
        CoroutineScope(Dispatchers.IO).launch {
            delay(10L)
            adapter.vocas.clear()
            try {
                Log.d("day_test", "$day")
                var output = AppDataBase.getInstance(
                    requireContext()
                )!!
                    .vocabularyDao()
                    .getWordFromDay(day)
                for (voca in output) {
                    adapter.vocas.add(
                        VocaData(
                            voca.index!!.toLong(),
                            voca.day.toString(),
                            voca.word.toString(),
                            voca.meaning.toString(),
                            voca.isBookmark
                        )
                    )
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