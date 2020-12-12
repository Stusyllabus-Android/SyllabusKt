package com.stu.syllabuskt.syllabus

import com.stu.syllabuskt.bean.Lesson
import com.stu.syllabuskt.bean.YiBanTimeTable

/**
 * Create by yuan on 2020/12/8
 */
interface ISyllabusContract {

    interface IView {
        fun showMSG(msg: String)

        fun showSyllabus(lessonList: List<Lesson>)
    }

    interface IModel {

        fun filterTables(tableBeanList: List<YiBanTimeTable.TableBean?>?, currentSemester: String?): List<YiBanTimeTable.TableBean?>?

        fun convertTablesToLessons(currentTables: List<YiBanTimeTable.TableBean?>?): List<Lesson?>?
    }
}