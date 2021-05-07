package com.example.myvocabook.ui.voca

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.myvocabook.R

class VocaAdapter(var context: Context?, var vocas: ArrayList<VocaData>) :
    RecyclerView.Adapter<VocaAdapter.VocaViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(holder: VocaViewHolder, view: View, data: VocaData, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null


    inner class VocaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordView: TextView = itemView.findViewById<TextView>(R.id.wordText)
        val meanView: TextView = itemView.findViewById<TextView>(R.id.meanView)
        val bookmark: ImageView = itemView.findViewById(R.id.markImg)
        val exampleBtn: Button = itemView.findViewById<Button>(R.id.exBtn)
        val meanLayout: LinearLayout = itemView.findViewById(R.id.meanLinearLayout)

        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(this, it, vocas[adapterPosition], adapterPosition)
            }
            bookmark.setOnClickListener {
                Toast.makeText(context, "add to bookmark", Toast.LENGTH_SHORT).show()
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
        holder.meanView.textSize = 12F;
        if (vocas[position].isOpen) {
            //holder.meanView.visibility = View.VISIBLE
            holder.meanLayout.visibility = View.VISIBLE
        } else {
            //holder.meanView.visibility = View.GONE
            holder.meanLayout.visibility = View.GONE
        }
    }
}