package com.stu.syllabuskt.personal.card

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.tabs.TabLayout
import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseActivity
import com.stu.syllabuskt.utils.ToastUtil
import com.stu.syllabuskt.widget.LoadingDialog

class CardActivity : BaseActivity(), ICardContract.IView {

    private val TAG = "CardActivity"

    lateinit var refreshCardLayout: SwipeRefreshLayout
    lateinit var balanceTV: TextView
    lateinit var tabLayout: TabLayout
    lateinit var flowRecordRecyclerView: RecyclerView
    lateinit var loadingDialog: LoadingDialog

    lateinit var presenter: ICardContract.IPresenter

    private var currentTabText = "近三日"

    override fun getContentView(): Int {
        return R.layout.activity_card
    }

    private val refreshListener = object : CardPresenter.RefreshListener {
        override fun onFailure() {
            refreshCardLayout.isRefreshing = false
        }

        override fun onSuccess() {
            refreshCardLayout.isRefreshing = false
        }
    }

    override fun init() {
        super.init()
        refreshCardLayout = findViewById(R.id.refreshCardLayout)
        findViewById<ImageView>(R.id.backIcon).setOnClickListener { finish() }
        balanceTV = findViewById(R.id.balanceTV)
        tabLayout = findViewById(R.id.tabLayout)
        tabLayout.apply {
            addTab(tabLayout.newTab().let {
                it.text = "近三日"
                isSelected = true
                it
            })
            addTab(tabLayout.newTab().apply { text = "近一周" })
            addTab(tabLayout.newTab().apply { text = "近一月" })
        }
        flowRecordRecyclerView = findViewById(R.id.flowRecordRecyclerView)
        loadingDialog = LoadingDialog(this, "正在加载中…")
        initEvent()
        presenter = CardPresenter(this, this)
        presenter.init(Type.ThreeDays, null)
    }

    private fun initEvent() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                Log.i(TAG, "onTabSelected() >>>" + p0?.text ?: "")
                currentTabText = p0?.text.toString() ?: "近三日"
                when (p0?.text) {
                    "近三日" -> presenter.init(Type.ThreeDays, null)
                    "近一周" -> presenter.init(Type.OneWeek, null)
                    "近一月" -> presenter.init(Type.OneMonth, null)
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                Log.i(TAG, "onTabUnselected() >>>" + p0?.text ?: "")
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
                Log.i(TAG, "onTabReselected() >>>" + p0?.text ?: "")
            }
        })

        refreshCardLayout.setOnRefreshListener {
            when (currentTabText) {
                "近三日" -> presenter.init(Type.ThreeDays, refreshListener)
                "近一周" -> presenter.init(Type.OneWeek, refreshListener)
                "近一月" -> presenter.init(Type.OneMonth, refreshListener)
            }
        }
    }

    override fun showLoading() {
        refreshCardLayout.isRefreshing = false
        loadingDialog.show()
    }

    override fun showData(accountAndExpenseData: AccountAndExpenseData) {
        refreshCardLayout.isRefreshing = false
        loadingDialog.realDismiss()
        balanceTV.text = (accountAndExpenseData.accountNo.balance / 100.0).toString()
        if (accountAndExpenseData.expenseRecordList != null) {
            val mAdapter = FlowItemAdapter(this, accountAndExpenseData.expenseRecordList)
            flowRecordRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@CardActivity)
                adapter = mAdapter
            }
        }
    }

    override fun showErrMsg(msg: String) {
        refreshCardLayout.isRefreshing = false
        loadingDialog.realDismiss()
        ToastUtil.showShort(this, "加载失败")
    }
}
