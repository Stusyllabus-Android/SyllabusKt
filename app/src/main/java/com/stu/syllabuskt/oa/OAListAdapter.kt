package com.stu.syllabuskt.oa

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stu.syllabuskt.R
import com.stu.syllabuskt.bean.OABean
import java.text.SimpleDateFormat

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
            var time=formatTime(it.publishDate)
            holder.oaPublishDate.text = time
        }
        holder.relativeLayout.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, OADetailActivity::class.java).apply {
                putExtra("id",oaList?.get(position)?.id)
            }
            mContext.startActivity(intent)
        });
    }

    override fun getItemCount(): Int {
        return oaList?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var relativeLayout: RelativeLayout
        var oaTitle: TextView
        var oaDepartment: TextView
        var oaPublishDate: TextView

        init {
            itemView.apply {
                relativeLayout = findViewById(R.id.item_oa_relativeLayout)
                oaTitle = findViewById(R.id.oa_title)
                oaDepartment = findViewById(R.id.oa_department)
                oaPublishDate = findViewById(R.id.oa_publish_date)
            }
        }
    }

    private fun formatTime(s: String): String? {
        var time =""
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val date = sdf.parse(s)
            time=sdf.format(date)
        } catch (e: Exception) {
            e.toString()
        }
        return time;
    }
}