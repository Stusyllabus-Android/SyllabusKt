package com.stu.syllabuskt.personal.grade

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stu.syllabuskt.R
import kotlinx.android.synthetic.main.fragment_personal.view.*
import kotlinx.android.synthetic.main.item_grade_title.view.*

/**
 * Create by yuan on 2020/12/16
 */
class GradeItemAdapter(val context: Context, val gradeItemList: ArrayList<GradeItemBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var arrowIconClickListener: ArrowIconClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == GradeItem.Title.ordinal) {
            GradeTitle(LayoutInflater.from(context).inflate(R.layout.item_grade_title, parent, false))
        } else GradeContent(LayoutInflater.from(context).inflate(R.layout.item_grade_content, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return gradeItemList[position].itemType.ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (gradeItemList[position].itemType == GradeItem.Title) {
            (holder as GradeTitle).apply {
                gradeItemList[position].let {
                    semesterTV.text = it.firstText
                    numOfLessonsTextView.text = "共${(gradeItemList.size - 1)}门课程"
                    expandIV.apply {
                        setImageResource(R.drawable.icon_arrow_down)
                        setOnClickListener {
                            arrowIconClickListener?.onClick()
                        }
                    }
                }
            }
        } else if (gradeItemList[position].itemType == GradeItem.Content) {
            (holder as GradeContent).apply {
                gradeItemList[position].let {
                    lessonNameTV.text = it.firstText
                    gradeTV.text = it.secondText
                    if (position == gradeItemList.size - 1) divLine.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return gradeItemList.size
    }

    fun setOnArrowIconClickListener(arrowIconClickListener: ArrowIconClickListener) {
        this.arrowIconClickListener = arrowIconClickListener
    }

    inner class GradeTitle(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var semesterTV: TextView = itemView.findViewById(R.id.semesterTV)
        val numOfLessonsTextView: TextView = itemView.findViewById(R.id.numOfLessons)
        val expandIV: ImageView = itemView.findViewById(R.id.expandIcon)
    }

    inner class GradeContent(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lessonNameTV: TextView = itemView.findViewById(R.id.lessonNameTV)
        val gradeTV: TextView = itemView.findViewById(R.id.gradeTV)
        val divLine: View = itemView.findViewById(R.id.divLine)
    }

    interface ArrowIconClickListener {
        fun onClick()
    }
}