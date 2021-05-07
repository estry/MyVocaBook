package com.example.myvocabook.ui.words

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myvocabook.R

class GridAdapter(
    var context: Context?, var days: ArrayList<String>
) : RecyclerView.Adapter<GridAdapter.GridViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(holder: GridViewHolder, view: View, data: String, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.textView)
        init {
            textView.setOnClickListener {
                itemClickListener?.onItemClick(this, it, days[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.daylayout, parent, false)
        return GridViewHolder(view)
    }

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val textView = holder.itemView.findViewById<TextView>(R.id.textView)
        textView.text = days[position]
    }
}