package com.stu.syllabuskt.personal.grade

/**
 * Create by yuan on 2020/12/15
 */
interface IGradeContract {
    interface IView {
        fun setGradeDateAndShow(gradeArr: ArrayList<Grade>)
    }
    interface IPresenter {
        fun getGrade(refreshListener: GradePresenter.RefreshListener?)
    }
}