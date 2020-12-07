package com.stu.syllabuskt.oa

import android.content.Context
import com.stu.syllabuskt.bean.OABean

/**
 * Create by yuan on 2020/12/5
 */
class OAListPresenter(val mView: OAListContract.view, mContext: Context) : OAListContract.presenter {

    private val oaModel = OAListModel(mContext)

    override fun loadOAList(pageIndex: Int) {
        mView.showLoading()
        oaModel.getOAList(pageIndex, object : OAListModel.OAModelListener {
            override fun onProgress() {

            }

            override fun onSuccess(oaList: List<OABean>?) {
                mView.setPagerAdapter(oaList)
            }

            override fun onFailure(msg: String) {

            }
        })
    }
}