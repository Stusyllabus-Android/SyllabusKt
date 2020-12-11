package com.stu.syllabuskt.syllabus

import android.content.Context
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.bean.Lesson

/**
 * Create by yuan on 2020/12/8
 */
class SyllabusPresenter(private val mContext: Context, val view: ISyllabusContract.IView) {

    private val model = SyllabusModel(mContext)

    fun init() {
        view.showSyllabus(model.convertTablesToLessons(model.filterTables(StuContext.getDBService().getTimeTable(mContext), StuContext.getDBService().getSemester(mContext))) as List<Lesson>)
    }

}