package com.stu.syllabuskt.login

import android.widget.Button
import android.widget.EditText
import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseActivity
import com.stu.syllabuskt.toMainViewAct
import com.stu.syllabuskt.utils.ToastUtil
import com.stu.syllabuskt.widget.LoadingDialog

class LoginActivity : BaseActivity(), LoginContract.view {

    lateinit var presenter: LoginPresenter
    lateinit var loadingDialog: LoadingDialog

    override fun getContentView(): Int {
        return R.layout.activity_login
    }

    override fun init() {
        presenter = LoginPresenter(this, this)
        loadingDialog = LoadingDialog(this, "正在登录中…")
        val accountET = findViewById<EditText>(R.id.accountEditText)
        val passwordET = findViewById<EditText>(R.id.passwordEditText)
        findViewById<Button>(R.id.loginButton).setOnClickListener {
            presenter.login(accountET.text.toString(), passwordET.text.toString())
        }
    }

    override fun toMainView() {
        runOnUiThread {
            loadingDialog.realDismiss()
            toMainViewAct()
        }
    }

    override fun showLoading() {
        runOnUiThread {
            loadingDialog.show()
        }
    }

    override fun showFailMsg(msg: String) {
        ToastUtil.showShort(this, msg)
        runOnUiThread {
            loadingDialog.realDismiss()
        }
    }
}
