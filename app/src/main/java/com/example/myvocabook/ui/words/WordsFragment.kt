package com.example.myvocabook.ui.words

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
import com.example.myvocabook.AppDataBase
import com.example.myvocabook.R
import com.example.myvocabook.ui.voca.VocaFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.InvocationTargetException

class WordsFragment : Fragment() {
    val days = arrayListOf<String>(
        /*"day1", "day2", "day3", "day4", "day5", "day6", "day7", "day8", "day9", "day10",
        "day11", "day12", "day13", "day14", "day15", "day16", "day17", "day18", "day19", "day20",
        "day21", "day22", "day23", "day24", "day25", "day26", "day27", "day28", "day29", "day30"*/
    )
    val wordsViewModel : WordsViewModel by viewModels()
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
                var day = adapter.days[position]
                // Log.d("click_test", "$day")
                wordsViewModel.setLiveData(day)
                val bundle = Bundle()
                bundle.putString("day", day)

                VocaFragment().arguments = bundle
                val fragment = childFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val vocaFragment = VocaFragment()
                fragment.replace(R.id.framelayout, vocaFragment)
                fragment.commit()
            }
        }
        recyclerView.adapter = adapter
        CoroutineScope(Dispatchers.IO).launch {
            adapter.days.clear()
            try {
                var output = AppDataBase.getInstance(requireContext())!!
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