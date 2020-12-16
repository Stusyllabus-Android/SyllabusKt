package com.stu.syllabuskt.personal.grade

import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseActivity

class GradeActivity() : BaseActivity(), IGradeContract.IView {

    private lateinit var presenter: IGradeContract.IPresenter

    override fun getContentView(): Int {
        return R.layout.activity_grade
    }

    override fun init() {
        super.init()
        presenter = GradePresenter(this, this)
        presenter.getGrade()
    }

    override fun setGradeDateAndShow() {

    }
}
