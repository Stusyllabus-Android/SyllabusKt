package com.stu.syllabuskt.personal.grade

import android.content.Context
import com.stu.syllabuskt.StuContext

/**
 * Create by yuan on 2020/12/15
 */
class GradePresenter(val mContext: Context, val mView: IGradeContract.IView): IGradeContract.IPresenter {

    private val mGradeModel: GradeModel = GradeModel(mContext)

    override fun getGrade(refreshListener: RefreshListener?) {
        mGradeModel.getGradeDataFromNet(
            StuContext.getDBService().getUserAccount(mContext),
            StuContext.getDBService().getUserPassword(mContext),
            object : GradeModel.GetGradeListener {
                override fun onFailure(msg: String) {
                    refreshListener?.onFailure()
                }

                override fun onSuccess(gradeArr: ArrayList<Grade>) {
                    mView.setGradeDateAndShow(gradeArr)
                    refreshListener?.onSuccess()
                }
            })
    }

    interface RefreshListener {
        fun onFailure()
        fun onSuccess()
    }
}