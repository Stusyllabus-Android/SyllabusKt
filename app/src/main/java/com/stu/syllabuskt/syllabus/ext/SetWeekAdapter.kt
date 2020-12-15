package com.stu.syllabuskt.syllabus.ext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stu.syllabuskt.R

/**
 * Create by yuan on 2020/12/14
 */
class SetWeekAdapter(val context: Context) : RecyclerView.Adapter<SetWeekAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_week_select, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.apply {
            text = "${position + 1}"
            setOnClickListener { onItemClickListener?.onItemClickListener(position) }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int {
        return 20
    }

    inner class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(R.id.weekTextView)
    }

    interface OnItemClickListener {
        fun onItemClickListener(position: Int)
    }
}