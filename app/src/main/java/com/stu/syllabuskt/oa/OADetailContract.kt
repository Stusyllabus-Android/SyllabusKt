package com.stu.syllabuskt.oa

import com.stu.syllabuskt.bean.OABean

/**
 * Create by yxbao on 2020/12/13
 */
interface OADetailContract {
    interface view {
        fun showLoading()
        fun showErrMsg(msg: String)
        fun setWebView(content: String?)
    }

    interface presenter {
        fun loadOADetail(id: Int)
    }
}