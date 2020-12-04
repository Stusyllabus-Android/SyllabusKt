package com.stu.syllabuskt.login

import android.content.Context

/**
 *yuan
 *2020/9/4
 **/
class LoginPresenter(val view: LoginContract.view, mContext: Context): LoginContract.presenter {

    var model: LoginModel = LoginModel(mContext)

    override fun login(account: String, password: String) {
        view.showLoading()
//        model.login(account, password, object : LoginModel.LoginListener {
//            override fun onProgress() {
//                view.showLoading()
//            }
//
//            override fun onSuccess() {
//                view.toMainView()
//            }
//
//            override fun onFailure(msg: String) {
//                view.showFailMsg(msg)
//            }
//        })
    }

}