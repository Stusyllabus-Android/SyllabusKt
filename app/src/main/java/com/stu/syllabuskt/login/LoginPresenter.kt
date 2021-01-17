package com.stu.syllabuskt.login

import android.content.Context
import com.stu.syllabuskt.Target
import com.stu.syllabuskt.YBBusinessModel

/**
 *yuan
 *2020/9/4
 **/
class LoginPresenter(val view: LoginContract.view, mContext: Context): LoginContract.presenter {

    var model: YBBusinessModel = YBBusinessModel(mContext, Target.Login)

    override fun login(account: String, password: String) {
        model.login(account, password, object : YBBusinessModel.YBBusinessListener {
            override fun onProgress() {
                view.showLoading()
            }

            override fun onSuccess() {
                view.toMainView()
            }

            override fun onFailure(msg: String) {
                view.showFailMsg(msg)
            }
        })
    }

}