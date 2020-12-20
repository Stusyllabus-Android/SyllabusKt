package com.stu.syllabuskt.personal.card

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stu.syllabuskt.R
import com.stu.syllabuskt.bean.ExpenseRecord

/**
 * Create by yuan on 2020/12/18
 */
class FlowItemAdapter(val mContext: Context, private val recordList: List<ExpenseRecord>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_card_flow, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        recordList[position].let {
            (holder as ItemViewHolder).apply {
                if (it.collector.contains("食堂")) {
                    imageView.setImageDrawable(mContext.resources.getDrawable(R.drawable.icon_carteen))
                } else if (it.collector.contains("转帐")) {
                    imageView.setImageDrawable(mContext.resources.getDrawable(R.drawable.icon_income))
                } else if (it.collector.contains("热水")) {
                    imageView.setImageDrawable(mContext.resources.getDrawable(R.drawable.icon_hot_water))
                } else if (it.collector.contains("业务")) {
                    imageView.setImageDrawable(mContext.resources.getDrawable(R.drawable.icon_network))
                } else if (it.collector.contains("POS")) {
                    imageView.setImageDrawable(mContext.resources.getDrawable(R.drawable.icon_market))
                }
                siteTextView.text = it.collector.trim()
                dateTextView.text = it.time.substring(0, it.time.indexOf("+")).replace("T", " ")
                costTextView.text = (it.cost / 100.0).toString() + "元"
                consumeType.text = it.type.trim()
            }
        }
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.consumeTypeImageView)
        val siteTextView = itemView.findViewById<TextView>(R.id.site)
        val dateTextView = itemView.findViewById<TextView>(R.id.date)
        val costTextView = itemView.findViewById<TextView>(R.id.cost)
        val consumeType = itemView.findViewById<TextView>(R.id.consumeTypeTextView)
    }

}