package com.stu.syllabuskt.personal.theme

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.stu.syllabuskt.R

/**
 * Create by yuan on 2020/12/13
 */
class ThemePickerAdapter(val context: Context): RecyclerView.Adapter<ThemePickerAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_theme_picker, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val themeBean = ThemeUtil.mThemeBeanList[position]
        holder.let {
            it.mThemePickerView.background = (context.resources.getDrawable(R.drawable.bg_theme_picker) as GradientDrawable).apply { setColor(context.resources.getColor(themeBean.colorPrimary)) }
            it.mThemePickerView.setOnClickListener {
                onItemClickListener?.onClick(themeBean.name)
            }
            holder.mShowCurrent.visibility = if (themeBean.name == ThemeUtil.getMCurrentThemeName()) View.VISIBLE else View.GONE
        }

    }

    override fun getItemCount(): Int {
        return ThemeUtil.mThemeBeanMap?.size ?: 0
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var mThemePickerView: View = itemView.findViewById(R.id.themePickerView)
        var mShowCurrent: ImageView = itemView.findViewById(R.id.showCurrent)

    }

    interface OnItemClickListener {
        fun onClick(name: String)
    }

}