package com.example.myvocabook.ui.voca

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.myvocabook.database.AppDataBase
import com.example.myvocabook.R
import com.example.myvocabook.database.Vocabulary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VocaAdapter(var context: Context?, var vocas: ArrayList<VocaData>) :
    RecyclerView.Adapter<VocaAdapter.VocaViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(holder: VocaViewHolder, view: View, data: VocaData, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null


    inner class VocaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordView: TextView = itemView.findViewById(R.id.wordText)
        val meanView: TextView = itemView.findViewById(R.id.meanView)
        val bookmark: ImageView = itemView.findViewById(R.id.markImg)
        val exampleBtn: Button = itemView.findViewById(R.id.exBtn)
        val meanLayout: LinearLayout = itemView.findViewById(R.id.meanLinearLayout)

        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(this, it, vocas[adapterPosition], adapterPosition)
            }
            bookmark.setOnClickListener {
                if (!vocas[adapterPosition].isBookmark!!) {
                    Toast.makeText(context, "add to bookmark", Toast.LENGTH_SHORT).show()
                    vocas[adapterPosition].isBookmark = true
                    bookmark.setImageResource(R.drawable.ic_baseline_star_24)
                    updateBookmark(adapterPosition)
                } else if (vocas[adapterPosition].isBookmark!!) {
                    Toast.makeText(context, "delete from bookmark", Toast.LENGTH_SHORT).show()
                    vocas[adapterPosition].isBookmark = false
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
                        Vocabulary(
                            vocas[position].index,
                            vocas[position].day,
                            vocas[position].word,
                            vocas[position].meaning,
                            vocas[position].isBookmark
                        )
                    )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.voca_row, parent, false)
        return VocaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vocas.size
    }

    override fun onBindViewHolder(holder: VocaViewHolder, position: Int) {
        holder.wordView.text = vocas[position].word
        holder.meanView.text = vocas[position].meaning
        holder.meanView.textSize = 12F
        if (vocas[position].isOpen) {
            holder.meanLayout.visibility = View.VISIBLE
        } else {
            holder.meanLayout.visibility = View.GONE
        }
        if (vocas[position].isBookmark!!) {
            holder.bookmark.setImageResource(R.drawable.ic_baseline_star_24)
        } else if (!vocas[position].isBookmark!!) {
            holder.bookmark.setImageResource(R.drawable.ic_baseline_star_border_24)
        }
    }
}