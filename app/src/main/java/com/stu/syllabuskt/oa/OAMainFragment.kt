package com.stu.syllabuskt.oa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseFragment
import com.stu.syllabuskt.bean.OABean
import com.stu.syllabuskt.utils.ToastUtil
import com.stu.syllabuskt.widget.LoadingDialog

class OAMainFragment : BaseFragment() {

    private val TAG = "OAFragment"

    lateinit var loadingDialog: LoadingDialog
    lateinit var oaRefreshLayout: SwipeRefreshLayout
    lateinit var oaListViewPager: ViewPager
    lateinit var oaSearchFAB: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadingDialog = LoadingDialog(context!!, null)
        return inflater.inflate(R.layout.fragment_oa, container, false).apply {
            oaRefreshLayout = findViewById(R.id.oaRefreshLayout)
            oaListViewPager = findViewById(R.id.oaListVP)
            oaSearchFAB = findViewById(R.id.oaSearchFAB)
            onInitView()
            onInitEvent()
        }
    }

    private fun onInitView() {
        oaListViewPager.adapter = OAMainPagerAdapter(fragmentManager!!, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
    }

    private fun onInitEvent() {
        oaRefreshLayout.setOnRefreshListener {
            OAListFragment.newInstance(1)
        }
        oaListViewPager.apply {
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
                    oaRefreshLayout.isEnabled = position == 1
                }
            })
        }
        oaSearchFAB.setOnClickListener{}
    }

    companion object {
        @JvmStatic
        fun newInstance() = OAMainFragment()
    }
}
