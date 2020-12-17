package com.stu.syllabuskt.personal.grade

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stu.syllabuskt.R

/**
 * Create by yuan on 2020/12/16
 */
class GradeItemAdapter(
    val context: Context,
    var gradeItemList: ArrayList<GradeItemBean>,
    private val numOfLesson: Int,
    private var onlyShowTitleNow: Boolean = true
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val titleItem = ArrayList<GradeItemBean>().apply { add(gradeItemList[0]) }
    private val contentItem = ArrayList<GradeItemBean>().apply {
        for (i in 1 until gradeItemList.size) {
            add(gradeItemList[i])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == GradeItem.Title.ordinal) {
            GradeTitle(
                LayoutInflater.from(context).inflate(
                    R.layout.item_grade_title,
                    parent,
                    false
                )
            )
        } else GradeContent(
            LayoutInflater.from(context).inflate(
                R.layout.item_grade_content,
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return gradeItemList[position].itemType.ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (gradeItemList[position].itemType == GradeItem.Title) {
            (holder as GradeTitle).apply {
                gradeItemList[position].let {
                    semesterTV.text = it.firstText
                    numOfLessonsTextView.text = "共${numOfLesson}门课程"
                    semesterTitleContainer.apply {
                        setOnClickListener {
                            if (onlyShowTitleNow) {
                                semesterTitleContainer.background = context.resources.getDrawable(R.drawable.bg_grade_title)
                                expandIV.setImageDrawable(context.resources.getDrawable(R.drawable.icon_arrow_up))
                                onlyShowTitleNow = false
                            } else {
                                semesterTitleContainer.background = context.resources.getDrawable(R.drawable.bg_grade_title_only_title)
                                expandIV.setImageDrawable(context.resources.getDrawable(R.drawable.icon_arrow_down))
                                onlyShowTitleNow = true
                            }
                            notifyDataSetChanged()
                        }
                    }
                }
            }
        } else if (gradeItemList[position].itemType == GradeItem.Content && !onlyShowTitleNow) {
            (holder as GradeContent).apply {
                gradeItemList[position].let {
                    lessonNameTV.text = it.firstText
                    gradeTV.text = it.secondText
                    if (position == gradeItemList.size - 1) {
                        semesterContentContainer.background = context.resources.getDrawable(R.drawable.bg_grade_last_content)
                        divLine.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (onlyShowTitleNow) 1 else gradeItemList.size
    }

    inner class GradeTitle(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val semesterTitleContainer: RelativeLayout = itemView.findViewById(R.id.semesterTitleContainer)
        var semesterTV: TextView = itemView.findViewById(R.id.semesterTV)
        val numOfLessonsTextView: TextView = itemView.findViewById(R.id.numOfLessons)
        val expandIV: ImageView = itemView.findViewById(R.id.expandIcon)
    }

    inner class GradeContent(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val semesterContentContainer: RelativeLayout = itemView.findViewById(R.id.semesterContentContainer)
        val lessonNameTV: TextView = itemView.findViewById(R.id.lessonNameTV)
        val gradeTV: TextView = itemView.findViewById(R.id.gradeTV)
        val divLine: View = itemView.findViewById(R.id.divLine)
    }

}