package com.example.myvocabook.ui.bookmark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myvocabook.AppDataBase
import com.example.myvocabook.R
import com.example.myvocabook.Vocabulary
import com.example.myvocabook.ui.bookmark.BookmarkAdapter.BookmarkViewHolder
import com.example.myvocabook.ui.voca.VocaData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class BookmarkAdapter(var context: Context?, var bookmarks: ArrayList<VocaData>) :
    RecyclerView.Adapter<BookmarkViewHolder>() {
    var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(holder: BookmarkViewHolder, view: View, data: VocaData, position: Int)
    }

    inner class BookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordView: TextView = itemView.findViewById(R.id.wordText2)
        val meanView: TextView = itemView.findViewById(R.id.meanView2)
        val bookmark: ImageView = itemView.findViewById(R.id.markImg2)
        val exampleBtn: Button = itemView.findViewById(R.id.exBtn2)
        val meanLayout: LinearLayout = itemView.findViewById(R.id.meanLinearLayout2)

        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(
                    this,
                    it,
                    bookmarks[adapterPosition],
                    adapterPosition
                )
            }
            bookmark.setOnClickListener {
                if (!bookmarks[adapterPosition].isBookmark!!) {
                    bookmarks[adapterPosition].isBookmark = true
                    bookmark.setImageResource(R.drawable.ic_baseline_star_24)
                    updateBookmark(adapterPosition)
                } else if (bookmarks[adapterPosition].isBookmark!!) {
                    bookmarks[adapterPosition].isBookmark = false
                    bookmark.setImageResource(R.drawable.ic_baseline_star_border_24)
                    updateBookmark(adapterPosition)
                }
            }
        }
    }

    fun updateBookmark(position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            context?.let {
                AppDataBase.getInstance(it)
                    .vocabularyDao()
                    .updateBookmark(
                        Vocabulary(bookmarks[position].index,
                            bookmarks[position].day,
                            bookmarks[position].word,
                            bookmarks[position].meaning,
                            bookmarks[position].isBookmark)
                    )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bookmark_row, parent, false)
        return BookmarkViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookmarks.size
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.wordView.text = bookmarks[position].word
        holder.meanView.text = bookmarks[position].meaning
        holder.meanView.textSize = 12F
        if(bookmarks[position].isOpen) {
            holder.meanLayout.visibility = View.VISIBLE
        }
        else {
            holder.meanLayout.visibility = View.GONE
        }
        if (bookmarks[position].isBookmark!!) {
            holder.bookmark.setImageResource(R.drawable.ic_baseline_star_24)
        } else if (!bookmarks[position].isBookmark!!) {
            holder.bookmark.setImageResource(R.drawable.ic_baseline_star_border_24)
        }
    }
}