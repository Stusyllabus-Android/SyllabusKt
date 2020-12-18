package com.stu.syllabuskt.personal.card

import android.content.Context
import android.util.Log
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.login.Target
import com.stu.syllabuskt.login.YBBusinessModel

/**
 * Create by yuan on 2020/12/18
 */
class CardPresenter(val mContext: Context, val mView: ICardContract.IView) : ICardContract.IPresenter {

    private val TAG = "CardPresenter"

    private val yiBanBusinessModel = YBBusinessModel(mContext, Target.Card)

    override fun init(type: Type, refreshListener: RefreshListener?) {
        yiBanBusinessModel.login(
            StuContext.getDBService().getUserAccount(mContext),
            StuContext.getDBService().getUserPassword(mContext),
            null,
            object : YBBusinessModel.CardBusinessListener {
                override fun onSuccess(accountAndExpenseData: AccountAndExpenseData) {
                    Log.i(TAG, accountAndExpenseData.toString())
                    mView.showData(accountAndExpenseData)
                    refreshListener?.onSuccess()
                }

                override fun onFailure(msg: String) {
                    mView.showErrMsg(msg)
                    refreshListener?.onFailure()
                }

                override fun onProgress() {
                    mView.showLoading()
                }
            },
            type
        )
    }

    interface RefreshListener {
        fun onFailure()
        fun onSuccess()
    }
}