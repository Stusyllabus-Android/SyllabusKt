package com.stu.syllabuskt.syllabus.ext.delete

import android.content.Context
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.bean.Lesson
import com.stu.syllabuskt.bean.YiBanTimeTable

/**
 * Create by yuan on 2020/12/14
 */
class DeleteLessonModel(val context: Context){

    fun getCustomizedSyllabus(): ArrayList<YiBanTimeTable.TableBean?>? {
        return StuContext.getDBService().getCustomizedSyllabus(context, StuContext.getDBService().getUserAccount(context))
    }

    fun deleteLesson(
        kkbKey: Int?
    ) {
        kkbKey?.let {
            StuContext.getDBService().deleteInCustomizedSyllabus(context, kkbKey.toLong())
        }
    }
}