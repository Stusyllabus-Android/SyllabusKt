package com.stu.syllabuskt.personal.grade

/**
 * Create by yuan on 2020/12/15
 */
interface IGradeContract {
    interface IView {
        fun setGradeDateAndShow()
    }
    interface IPresenter {
        fun getGrade()
    }
}