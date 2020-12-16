package com.stu.syllabuskt.syllabus

import android.content.Context
import androidx.core.content.contentValuesOf
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.bean.Lesson

/**
 * Create by yuan on 2020/12/8
 */
class SyllabusPresenter(private val mContext: Context, val view: ISyllabusContract.IView, val ioListener: IOListener) {

    private val model = SyllabusModel(mContext)

    fun init() {
        val semesterSet = StuContext.getSharePreferences(mContext).getString(SyllabusContainerFragment.CurrentSemesterKey, "Non-existent")
        if (semesterSet.isNullOrEmpty() || semesterSet == "Non-existent") {
            view.showMSG("还未设置当前学年学期\n请前往个人主页设置当前学年学期~")
        } else {
            Thread {
                ioListener.onFinish(model.convertTablesToLessons(model.filterTables(StuContext.getDBService().getSyllabus(mContext), semesterSet)) as List<Lesson>)
            }.run()
        }
    }

    interface IOListener {
        fun onFinish(lessonList: List<Lesson>)
    }

}