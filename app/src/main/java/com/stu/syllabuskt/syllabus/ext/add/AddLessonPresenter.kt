package com.stu.syllabuskt.syllabus.ext.add

import android.content.Context

/**
 * Create by yuan on 2020/12/14
 */
class AddLessonPresenter(val context: Context, val view: AddLessonContract.IView) : AddLessonContract.IPresenter {

    private val model = AddLessonModel(context)

    override fun addLesson(
        lessonName: String,
        classroom: String,
        weekSelected: String,
        detail: String
    ) {
        model.addLesson(lessonName, classroom, weekSelected, detail)
        view.showSuccessMessage("添加成功")
    }
}