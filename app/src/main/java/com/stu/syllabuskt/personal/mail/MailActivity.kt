package com.stu.syllabuskt.personal.mail

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseActivity

class MailActivity : BaseActivity() {

    private val TAG = "MailActivity"

    private lateinit var mEmailWebView: WebView
    private lateinit var mEmailLoadingBar: ProgressBar

    override fun getContentView(): Int {
        return  R.layout.activity_mail
    }

    override fun init() {
        super.init()
        findViewById<TextView>(R.id.titleBarTV).text = "校内邮箱"
        findViewById<ImageView>(R.id.backIcon).let {
            it.visibility = View.VISIBLE
            it.setOnClickListener { finish() }
        }
        mEmailWebView = findViewById<WebView>(R.id.mailWebView).apply {
            settings.let {
                it.allowFileAccess = true
                it.setAppCacheEnabled(true)
                it.domStorageEnabled = true
                it.javaScriptEnabled = true
            }
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    mEmailLoadingBar.progress = newProgress
                    if (newProgress >= 100) mEmailLoadingBar.visibility = View.GONE
                    else mEmailLoadingBar.visibility = View.VISIBLE
                    super.onProgressChanged(view, newProgress)
                }
            }
            loadUrl("https://partner.outlook.cn/owa/?realm=stu.edu.cn&exsvurl=1&ll-cc=2052&modurl=0")
            setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
                Log.i(TAG, "url: $url")
                val uri = Uri.parse(url)
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        mEmailLoadingBar = findViewById(R.id.loadingBar)
    }

    override fun onBackPressed() {
        Log.i(TAG, "onBackPressed() >>> ${mEmailWebView.canGoBack()}")
        if (mEmailWebView.canGoBack()) mEmailWebView.goBack()
        else super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}