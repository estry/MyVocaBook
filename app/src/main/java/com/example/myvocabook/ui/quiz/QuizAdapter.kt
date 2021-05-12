package com.example.myvocabook.ui.quiz

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myvocabook.R
import java.util.*

class QuizAdapter(
    var context: Context?,
    var words: ArrayList<QuizData>,
    var selectedNums: ArrayList<Int>
) :
    RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    interface OnItemClickListenr {
        fun onItemClick(holder: QuizViewHolder, view: View, data: QuizData, position: Int)
    }

    var itemClickListener: OnItemClickListenr? = null

    @SuppressLint("ResourceAsColor")
    inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val problemView: TextView = itemView.findViewById(R.id.problemTextView)
        val ansView1: TextView = itemView.findViewById(R.id.ansView1)
        val ansView2: TextView = itemView.findViewById(R.id.ansView2)
        val ansView3: TextView = itemView.findViewById(R.id.ansView3)
        val ansView4: TextView = itemView.findViewById(R.id.ansView4)
        val ansBtn: Button = itemView.findViewById(R.id.ansBtn)

        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(this, it, words[adapterPosition], adapterPosition)
            }
            ansView1.setOnClickListener {
                if (selectedNums[adapterPosition] == -1) {
                    selectedNums[adapterPosition] = 1
                    ansView1.setBackgroundResource(R.color.yellow)
                } else if (selectedNums[adapterPosition] == 1) {
                    selectedNums[adapterPosition] = -1
                    ansView1.setBackgroundResource(R.color.mycolor)
                }
            }
            ansView2.setOnClickListener {
                if (selectedNums[adapterPosition] == -1) {
                    selectedNums[adapterPosition] = 2
                    ansView2.setBackgroundResource(R.color.yellow)
                } else if (selectedNums[adapterPosition] == 2) {
                    selectedNums[adapterPosition] = -1
                    ansView2.setBackgroundResource(R.color.mycolor)
                }
            }
            ansView3.setOnClickListener {
                if (selectedNums[adapterPosition] == -1) {
                    selectedNums[adapterPosition] = 3
                    ansView3.setBackgroundResource(R.color.yellow)
                } else if (selectedNums[adapterPosition] == 3) {
                    selectedNums[adapterPosition] = -1
                    ansView3.setBackgroundResource(R.color.mycolor)
                }
            }
            ansView4.setOnClickListener {
                if (selectedNums[adapterPosition] == -1) {
                    selectedNums[adapterPosition] = 4
                    ansView4.setBackgroundResource(R.color.yellow)
                } else if (selectedNums[adapterPosition] == 4) {
                    selectedNums[adapterPosition] = -1
                    ansView4.setBackgroundResource(R.color.mycolor)
                }
            }
            ansBtn.setOnClickListener {
                val ansPos = words[adapterPosition].ansPos
                val text = words[adapterPosition].ans[ansPos]
                Toast.makeText(context, text, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quiz_row, parent, false)
        return QuizViewHolder(view)
    }

    override fun getItemCount(): Int {
        return words.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        //Log.d("bind_test", words[position].problem)
        holder.problemView.text = words[position].problem
        when (selectedNums[position]) {
            1 -> holder.ansView1.setBackgroundResource(R.color.yellow)
            2 -> holder.ansView2.setBackgroundResource(R.color.yellow)
            3 -> holder.ansView3.setBackgroundResource(R.color.yellow)
            4 -> holder.ansView4.setBackgroundResource(R.color.yellow)
            -1 -> {
                holder.ansView1.setBackgroundResource(R.color.mycolor)
                holder.ansView2.setBackgroundResource(R.color.mycolor)
                holder.ansView3.setBackgroundResource(R.color.mycolor)
                holder.ansView4.setBackgroundResource(R.color.mycolor)
            }
        }
        holder.ansView1.text = words[position].ans[0]
        holder.ansView2.text = words[position].ans[1]
        holder.ansView3.text = words[position].ans[2]
        holder.ansView4.text = words[position].ans[3]
    }
}