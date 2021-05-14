package com.example.myvocabook.ui.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvocabook.R
import com.example.myvocabook.database.AppDataBase
import com.example.myvocabook.ui.voca.VocaData
import com.example.myvocabook.ui.web.WebFragment
import com.example.myvocabook.ui.web.WebViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class BookmarkFragment : Fragment() {

    val webModel: WebViewModel by activityViewModels()
    var bookmarks: ArrayList<VocaData> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.bookmarkRecyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = BookmarkAdapter(context, bookmarks)
        adapter.itemClickListener = object : BookmarkAdapter.OnItemClickListener {
            override fun onItemClick(
                holder: BookmarkAdapter.BookmarkViewHolder,
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
                    Log.d("ex_test", text)
                    webModel.setLiveData(text)

                    val fragment = childFragmentManager.beginTransaction()
                    fragment.addToBackStack(null)
                    val webFragment = WebFragment()
                    fragment.replace(R.id.framelayout2, webFragment)
                    fragment.commit()
                }
            }
        }
        recyclerView.adapter = adapter
        makeBookmark()
    }
    private fun makeBookmark() {
        CoroutineScope(Dispatchers.IO).launch {
            adapter.bookmarks.clear()
            val output = AppDataBase.getInstance(requireContext())
                .vocabularyDao().getAllByBookmark()
            for (word in output) {
                adapter.bookmarks.add(
                    VocaData(
                        word.index!!.toLong(),
                        word.day.toString(),
                        word.word.toString(),
                        word.meaning.toString(),
                        word.isBookmark
                    )
                )
            }
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
            }
        }
    }
}