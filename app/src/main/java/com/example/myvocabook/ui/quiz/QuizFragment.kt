package com.example.myvocabook.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvocabook.R
import com.example.myvocabook.database.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class QuizFragment : Fragment() {

    private lateinit var quizViewModel: QuizViewModel
    var words: ArrayList<QuizData> = ArrayList()
    var selectedNums: ArrayList<Int> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: QuizAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        quizViewModel =
            ViewModelProvider(this).get(QuizViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_quiz, container, false)

        quizViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.quizRecyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = QuizAdapter(context, words, selectedNums)
        adapter.itemClickListener = object : QuizAdapter.OnItemClickListenr {
            override fun onItemClick(
                holder: QuizAdapter.QuizViewHolder,
                view: View,
                data: QuizData,
                position: Int
            ) {

            }
        }
        recyclerView.adapter = adapter
        recyclerView.smoothScrollBy(1, 1, LinearInterpolator(), 50)
        makeQuiz()
    }
    private fun makeQuiz(){
        CoroutineScope(Dispatchers.IO).launch {
            val output = AppDataBase.getInstance(requireContext())
                .vocabularyDao().getAll()
            var nums = TreeSet<Int>()
            while (nums.size < 30) {
                val random = Random()
                val num = random.nextInt(output.size)
                nums.add(num)
                selectedNums.add(-1)
            }

            for (i in nums) {
                val word = output[i].word.toString()
                val ans = ArrayList<String>()
                val set = TreeSet<Int>()
                val answer = output[i].meaning.toString()
                ans.add(answer)
                while (set.size < 3) {
                    val random = Random()
                    val num = random.nextInt(output.size)
                    if (num == i)
                        continue
                    set.add(num)
                }
                for (j in set) {
                    ans.add(output[j].meaning.toString())
                }
                ans.shuffle()
                var idx = -1
                for (t in ans) {
                    if (t == answer)
                        idx = ans.indexOf(t)
                }
                adapter.words.add(QuizData(word, ans, idx))
            }
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
            }
        }
    }
}