package com.stu.syllabuskt.syllabus.ext.add

/**
 * Create by yuan on 2020/12/14
 */
interface AddLessonContract {

    interface IView {

        fun chooseWeek()

        fun chooseDetail()

        fun showErrorMSG(msg: String)

        fun showSuccessMessage(msg: String)

    }

    interface IPresenter {

        fun addLesson(lessonName: String, classroom: String, weekSelected: String, detail: String)

    }

    interface IModel {

        fun addLesson(lessonName: String, classroom: String, weekSelected: String, detail: String)

    }

}