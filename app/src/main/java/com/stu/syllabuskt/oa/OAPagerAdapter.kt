package com.stu.syllabuskt.oa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.stu.syllabuskt.R
import com.stu.syllabuskt.bean.OABean
import com.stu.syllabuskt.utils.ToastUtil

/**
 * Create by yuan on 2020/12/5
 */
class OAPagerAdapter(val context: Context, val oaList: List<OABean>?) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return oaList?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return LayoutInflater.from(context).inflate(R.layout.item_oa, container, false).apply {
            oaList?.get(position)?.let {
                this.findViewById<TextView>(R.id.oa_title).text = it.title
                this.findViewById<TextView>(R.id.oa_department).text = it.department
                this.findViewById<TextView>(R.id.oa_publish_date).text = it.publishDate
            }
        }.setOnClickListener { ToastUtil.showLong(context, "click oa list item") }

    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}