package com.stu.syllabuskt.oa

import com.stu.syllabuskt.bean.OABean

/**
 * Create by yuan on 2020/12/5
 */
interface OAContract {

    interface view {
        fun showLoading()
        fun showErrMsg(msg: String)
        fun setPagerAdapter(oaList: List<OABean>?)
    }

    interface presenter {
        fun loadOAList(pageIndex: Int)
    }
}