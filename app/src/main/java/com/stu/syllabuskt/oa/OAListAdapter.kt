package com.stu.syllabuskt.oa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stu.syllabuskt.R
import com.stu.syllabuskt.bean.OABean

/**
 * Create by yuan on 2020/12/7
 */
class OAListAdapter(private val mContext: Context, private val oaList: List<OABean>?) : RecyclerView.Adapter<OAListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_oa, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        oaList?.get(position)?.let {
            holder.oaTitle.text = it.title
            holder.oaDepartment.text = it.department
            holder.oaPublishDate.text = it.publishDate
        }
    }

    override fun getItemCount(): Int {
        return oaList?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var oaTitle: TextView
        var oaDepartment: TextView
        var oaPublishDate: TextView

        init {
            itemView.apply {
                oaTitle = findViewById(R.id.oa_title)
                oaDepartment = findViewById(R.id.oa_department)
                oaPublishDate = findViewById(R.id.oa_publish_date)
            }
        }
    }
}