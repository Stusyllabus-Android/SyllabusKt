package com.stu.syllabuskt.syllabus.ext.add

import android.content.Context
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.bean.YiBanTimeTable
import com.stu.syllabuskt.db.SyllabusSourceType

/**
 * Create by yuan on 2020/12/14
 */
class AddLessonModel(val context: Context) : AddLessonContract.IModel {

    /**
     * xnxq_name : 2017-2018学年秋季学期
     * kkb_key : 95421
     * kc_name : [ELC1]英语(ELC1)
     * js_name : 苏雪枫
     * ks_name : D座307
     * sj_name : 第1-16周，周二(12节)，周五(34节)
     */
    override fun addLesson(
        lessonName: String,
        classroom: String,
        weekSelected: String,
        detail: String
    ) {
        val tableBean = YiBanTimeTable.TableBean(StuContext.getDBService().getSemester(context), System.currentTimeMillis().toInt(), lessonName, "", classroom, "第${weekSelected}周，${detail.split(" ")[0]}(${generateNodeStr(detail.split(" ")[1])}节)")
        StuContext.getDBService().writeSyllabus(context, StuContext.getDBService().getUserAccount(context), tableBean, SyllabusSourceType.Customized)
    }

    private fun generateNodeStr(detail: String):String {
        val node = detail.split("-")
        if (node[0] == node[1]) return node[0]
        else return detail
    }
}