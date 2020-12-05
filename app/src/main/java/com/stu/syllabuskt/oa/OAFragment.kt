package com.stu.syllabuskt.oa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseFragment
import com.stu.syllabuskt.bean.OABean
import com.stu.syllabuskt.utils.ToastUtil
import com.stu.syllabuskt.widget.LoadingDialog

class OAFragment : BaseFragment(), OAContract.view {

    private val TAG = "OAFragment"

    lateinit var loadingDialog: LoadingDialog
    lateinit var oaRefreshLayout: SwipeRefreshLayout
    lateinit var oaListContainer: ViewPager
    lateinit var oaSearchFAB: FloatingActionButton

    private val oaPresenter = OAPresenter(this, context!!)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadingDialog = LoadingDialog(context!!, null)
        return inflater.inflate(R.layout.fragment_oa, container, false).apply {
            oaRefreshLayout = findViewById(R.id.oaRefreshLayout)
            oaListContainer = findViewById(R.id.oaListContainer)
            oaSearchFAB = findViewById(R.id.oaSearchFAB)
            onInitEvent()
            oaPresenter.loadOAList(1)
        }
    }

    private fun onInitEvent() {
        oaRefreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                oaPresenter.loadOAList(1)
            }
        })
        oaListContainer.apply {
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                    Log.i(TAG, "onPageScrollStateChanged() >>> state is $state")
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    Log.i(TAG, "onPageScrolled() >>> position is $position, positionOffset is $positionOffset, positionOffsetPixels is $positionOffsetPixels")
                }

                override fun onPageSelected(position: Int) {
                    Log.i(TAG, "onPageSelected() >>> position is $position")
                    oaPresenter.loadOAList(position)
                }
            })
        }
        oaSearchFAB.setOnClickListener{}
    }

    override fun showLoading() {
        runOnUiThread { loadingDialog.show() }
    }

    override fun showErrMsg(msg: String) {
        ToastUtil.showShort(context!!, msg)
    }

    override fun setPagerAdapter(oaList: List<OABean>?) {
        runOnUiThread {
            loadingDialog.realDismiss()
            oaListContainer.adapter = OAPagerAdapter(context!!, oaList)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = OAFragment()
    }
}
