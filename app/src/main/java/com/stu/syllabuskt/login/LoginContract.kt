package com.stu.syllabuskt.login

/**
 *yuan
 *2020/9/4
 **/
interface LoginContract {
    interface presenter {
        fun login(account: String, password: String)
    }

    interface view {
        fun toMainView()

        fun showLoading()

        fun showFailMsg(msg: String)
    }
}