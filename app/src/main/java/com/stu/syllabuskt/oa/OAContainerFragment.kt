package com.stu.syllabuskt.oa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager

import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseFragment
import com.stu.syllabuskt.widget.LoadingDialog

class OAContainerFragment : BaseFragment() {

    private val TAG = "OAMainFragment"

    private var currentPageIndex: Int = 1

    lateinit var loadingDialog: LoadingDialog
    lateinit var oaListViewPager: ViewPager
//    lateinit var oaSearchFAB: FloatingActionButton

    lateinit var titleTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadingDialog = LoadingDialog(context!!, null)
        return inflater.inflate(R.layout.fragment_oa, container, false).apply {
            titleTextView = findViewById<TextView>(R.id.titleBarTV).apply { text = "第 $currentPageIndex 页" }
            oaListViewPager = findViewById<ViewPager>(R.id.oaListVP).apply { offscreenPageLimit = 2 }
//            oaSearchFAB = findViewById(R.id.oaSearchFAB)
            onInitView()
            onInitEvent()
        }
    }

    private fun onInitView() {
        oaListViewPager.adapter = OAPagerAdapter(childFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
    }

    private fun onInitEvent() {
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
                    currentPageIndex = position + 1
                    titleTextView.text = "第 $currentPageIndex 页"
                }
            })
        }
//        oaSearchFAB.setOnClickListener{}
    }

    companion object {
        @JvmStatic
        fun newInstance() = OAContainerFragment()
    }
}
