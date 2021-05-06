package com.example.myvocabook.ui.todaywords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myvocabook.R

class TodaywordsFragment : Fragment() {

    private lateinit var todayWordsViewModel: TodayWordsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        todayWordsViewModel =
            ViewModelProvider(this).get(TodayWordsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_todaywords, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        todayWordsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}