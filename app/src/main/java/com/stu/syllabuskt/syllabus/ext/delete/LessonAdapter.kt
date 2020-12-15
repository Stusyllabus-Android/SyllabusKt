package com.stu.syllabuskt.syllabus.ext.delete

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stu.syllabuskt.R
import com.stu.syllabuskt.bean.Lesson
import com.stu.syllabuskt.bean.YiBanTimeTable

/**
 * Create by yuan on 2020/12/14
 */
class LessonAdapter(val context: Context, private val lessonList: List<YiBanTimeTable.TableBean?>?) : RecyclerView.Adapter<LessonAdapter.ViewHolder>() {

    private var onDeleteIconClickListener: OnDeleteIconClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_lesson, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        lessonList?.get(position)?.let {
            holder.apply {
                lessonNameTV.text = it.kcName
                classRoomTV.text = it.ksName
                lessonTimeTV.text = it.sjName
                deleteIcon.setOnClickListener{
                    onDeleteIconClickListener?.onDeleteIconClickListener(position)
                }
            }
        }
    }

    @JvmName("setOnDeleteIconClickListener1")
    fun setOnDeleteIconClickListener(onDeleteIconClickListener: OnDeleteIconClickListener) {
        this.onDeleteIconClickListener = onDeleteIconClickListener
    }

    override fun getItemCount(): Int {
        return lessonList?.size ?: 0
    }

    inner class ViewHolder(private val itemView: View) :RecyclerView.ViewHolder(itemView) {
        val lessonNameTV: TextView = itemView.findViewById(R.id.lessonNameTV)
        val classRoomTV: TextView = itemView.findViewById(R.id.classroomTV)
        val lessonTimeTV: TextView = itemView.findViewById(R.id.lessonTimeTV)
        val deleteIcon: ImageView = itemView.findViewById(R.id.deleteLessonIcon)
    }

    interface OnDeleteIconClickListener {
        fun onDeleteIconClickListener(position: Int)
    }
}