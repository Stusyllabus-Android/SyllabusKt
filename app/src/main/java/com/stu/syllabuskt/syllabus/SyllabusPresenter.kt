package com.stu.syllabuskt.syllabus

import android.content.Context
import androidx.core.content.contentValuesOf
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.bean.Lesson

/**
 * Create by yuan on 2020/12/8
 */
class SyllabusPresenter(private val mContext: Context, val view: ISyllabusContract.IView) {

    private val model = SyllabusModel(mContext)

    fun init() {
        if (StuContext.getDBService().getSemester(mContext).isNullOrEmpty()) {
            view.showMSG("还未设置当前学年学期\n请前往个人主页设置当前学年学期~")
        } else {
            view.showSyllabus(model.convertTablesToLessons(model.filterTables(StuContext.getDBService().getSyllabus(mContext), StuContext.getDBService().getSemester(mContext))) as List<Lesson>)
        }
    }

}