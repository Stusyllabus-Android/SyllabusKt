package com.stu.syllabuskt.syllabus.ext.add

import android.content.Context
import com.stu.syllabuskt.StuContext

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
        if (StuContext.getDBService().getSemester(context).isNullOrEmpty()) {
            view.showErrorMSG("请先到个人主页设置当前学年学期~")
        } else {
            model.addLesson(lessonName, classroom, weekSelected, detail)
            view.showSuccessMessage("添加成功")
        }
    }
}