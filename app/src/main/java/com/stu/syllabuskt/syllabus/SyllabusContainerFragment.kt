package com.stu.syllabuskt.syllabus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager

import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseFragment

class SyllabusContainerFragment : BaseFragment() {

    private val TAG = "SyllabusContainerFragment"

    private var selectedWeek = 1

    private lateinit var titleTV: TextView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var syllabusContainer: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_syllabus_container, container, false).apply {
            syllabusContainer = findViewById(R.id.syllabusContainer)
            titleTV = findViewById<TextView>(R.id.titleBarTV).apply{ text = "第 $selectedWeek 周" }
            initEvent()
        }
    }

    private fun initEvent() {
        syllabusContainer.adapter = SyllabusPagerAdapter(childFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        syllabusContainer.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.i(TAG, "onPageScrolled() >>> $position")
            }

            override fun onPageSelected(position: Int) {
                Log.i(TAG, "onPageSelected() >>> $position")
                selectedWeek = position + 1
                titleTV.text = "第 ${position + 1} 周"
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.i(TAG, "onPageScrollStateChanged() >>> $state")
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = SyllabusContainerFragment()
    }
}
