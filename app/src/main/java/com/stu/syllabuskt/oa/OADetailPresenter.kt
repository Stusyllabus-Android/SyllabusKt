package com.stu.syllabuskt.oa

import android.content.Context
import com.stu.syllabuskt.utils.ToastUtil

/**
 * Create by yxbao on 2020/12/13
 */
class OADetailPresenter(val mView: OADetailContract.view, mContext: Context) : OADetailContract.presenter {

    private val oaModel = OADetailModel(mContext)

    override fun loadOADetail(id: Int) {
        mView.showLoading()
        oaModel.getOADetail(id,object :OADetailModel.OADetailModelListener{
            override fun onProgress() {

            }

            override fun onSuccess(content: String?) {
                mView.setWebView(content)
            }

            override fun onFailure(msg: String) {
                mView.showErrMsg(msg)
            }
        })

    }
}