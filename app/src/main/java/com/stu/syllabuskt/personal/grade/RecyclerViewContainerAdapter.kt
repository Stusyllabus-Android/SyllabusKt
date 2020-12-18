package com.stu.syllabuskt.personal.grade

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stu.syllabuskt.R

/**
 * Create by yuan on 2020/12/17
 */
class RecyclerViewContainerAdapter(val context: Context, private val formattedData: ArrayList<ArrayList<GradeItemBean>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GradeItemContainer(LayoutInflater.from(context).inflate(R.layout.item_grade_item_container, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder.itemView as RecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = GradeItemAdapter(context, formattedData[position], formattedData[position].size - 1)
        }
    }

    override fun getItemCount(): Int {
        return formattedData.size
    }

    inner class GradeItemContainer(itemView: View) : RecyclerView.ViewHolder(itemView)
}