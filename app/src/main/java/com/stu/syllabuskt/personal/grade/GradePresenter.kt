package com.stu.syllabuskt.personal.grade

import android.content.Context
import com.stu.syllabuskt.StuContext

/**
 * Create by yuan on 2020/12/15
 */
class GradePresenter(val mContext: Context, val mView: IGradeContract.IView): IGradeContract.IPresenter {

    private val mGradeModel: GradeModel = GradeModel(mContext)

    override fun getGrade() {
        mGradeModel.getGradeDataFromNet(
            StuContext.getDBService().getUserAccount(mContext),
            StuContext.getDBService().getUserPassword(mContext),
            object : GradeModel.GetGradeListener {
                override fun onFailure(msg: String) {

                }

                override fun onSuccess() {
                    mView.setGradeDateAndShow()
                }
            })
    }
}