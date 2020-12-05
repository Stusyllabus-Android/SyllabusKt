package com.stu.syllabuskt.oa

import android.content.Context
import com.stu.syllabuskt.bean.OABean

/**
 * Create by yuan on 2020/12/5
 */
class OAPresenter(val mView: OAContract.view, mContext: Context) : OAContract.presenter {

    private val oaModel = OAModel(mContext)

    override fun loadOAList(pageIndex: Int) {
        oaModel.getOAList(object : OAModel.OAModelListener {
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