package com.stu.syllabuskt.syllabus.ext.delete

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseActivity
import com.stu.syllabuskt.bean.Lesson
import com.stu.syllabuskt.bean.YiBanTimeTable

class DeleteLessonActivity : BaseActivity() {

    lateinit var deleteLessonModel: DeleteLessonModel
    lateinit var title: TextView
    lateinit var backIcon: ImageView
    lateinit var lessonRecyclerView: RecyclerView
    private var adapter: LessonAdapter? = null

    private var localCustomLesson: ArrayList<YiBanTimeTable.TableBean?>? = null

    override fun getContentView(): Int {
        return R.layout.activity_delete_lesson
    }

    override fun init() {
        super.init()
        deleteLessonModel = DeleteLessonModel(this)
        title = findViewById<TextView>(R.id.titleBarTV).apply { text = "删除课程" }
        backIcon = findViewById<ImageView>(R.id.backIcon).apply {
            visibility = View.VISIBLE
            setOnClickListener {
                finish()
            }
        }
        lessonRecyclerView = findViewById(R.id.lessonRecyclerView)
        localCustomLesson = deleteLessonModel.getCustomizedSyllabus()
        adapter = LessonAdapter(this, localCustomLesson).apply {
            setOnDeleteIconClickListener(object : LessonAdapter.OnDeleteIconClickListener {
                override fun onDeleteIconClickListener(position: Int) {
                    deleteLessonModel.deleteLesson(localCustomLesson?.get(position)?.kkbKey)
                    localCustomLesson?.removeAt(position)
                    notifyDataSetChanged()
                }
            })
        }
        lessonRecyclerView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }
    }
}