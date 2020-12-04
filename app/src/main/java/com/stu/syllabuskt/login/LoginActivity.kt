package com.stu.syllabuskt.login

import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.stu.syllabuskt.R
import com.stu.syllabuskt.anim.LoadingAnimationUtil
import com.stu.syllabuskt.base.BaseActivity
import com.stu.syllabuskt.toMainViewAct
import com.stu.syllabuskt.utils.ToastUtil

class LoginActivity : BaseActivity(), LoginContract.view {

    lateinit var presenter: LoginPresenter
    lateinit var loadingView: LinearLayout

    override fun getContentView(): Int {
        return R.layout.activity_login
    }

    override fun init() {
        presenter = LoginPresenter(this, this)
        loadingView = findViewById(R.id.loadingView)
        val accountET = findViewById<EditText>(R.id.accountEditText)
        val passwordET = findViewById<EditText>(R.id.passwordEditText)
        findViewById<Button>(R.id.loginButton).setOnClickListener {
            presenter.login(accountET.text.toString(), passwordET.text.toString())
        }
    }

    override fun toMainView() {
        toMainViewAct()
    }

    override fun showLoading() {
        LoadingAnimationUtil.startLoading(this, loadingView)
    }

    override fun showFailMsg(msg: String) {
        ToastUtil.showShort(this, msg)
    }
}
