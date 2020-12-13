package com.stu.syllabuskt.oa

import android.content.Context

/**
 * Create by yxbao on 2020/12/13
 */
class OADetailPresenter(val mView: OADetailContract.view, mContext: Context) : OADetailContract.presenter {

    private val oaModel = OADetailModel(mContext)

    override fun loadOADetail(id: Int) {
        oaModel.getOADetail(id,object :OADetailModel.OADetailModelListener{
            override fun onProgress() {
                mView.showLoading()
            }

            override fun onSuccess(content: String?) {
                mView.setWebView(content)
            }

            override fun onFailure(msg: String) {
                mView.showErrMsg("网络出错，请稍后重试")
            }
        })

    }
}