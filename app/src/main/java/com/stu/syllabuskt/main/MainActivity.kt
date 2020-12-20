package com.stu.syllabuskt.main

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stu.syllabuskt.CheckUpdateModel
import com.stu.syllabuskt.R
import com.stu.syllabuskt.Result
import com.stu.syllabuskt.StuContext
import com.stu.syllabuskt.base.BaseActivity
import com.stu.syllabuskt.oa.OAContainerFragment
import com.stu.syllabuskt.personal.PersonalFragment
import com.stu.syllabuskt.syllabus.SyllabusContainerFragment
import com.stu.syllabuskt.utils.ActionTrigger
import com.stu.syllabuskt.utils.ToastUtil
import com.stu.syllabuskt.widget.StuMainViewPager


class MainActivity : BaseActivity() {

    private val TAG = "MainActivity"

    lateinit var mainViewPager: StuMainViewPager

    val trigger = ActionTrigger(250)

    override fun init() {
        initBottomNavigationView()
        mainViewPager = findViewById(R.id.mainViewPager)
        val fragmentList = arrayListOf<Fragment>().apply {
            add(SyllabusContainerFragment.newInstance())
            add(OAContainerFragment.newInstance())
            add(PersonalFragment.newInstance())
        }
        mainViewPager.adapter = MainPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragmentList)
        mainViewPager.offscreenPageLimit = 2
        CheckUpdateModel().canUpdateWithVersionName(object : CheckUpdateModel.UpdateListener {
            override fun canUpdate(result: Result) {
                if (result.canUpdate && StuContext.getSharePreferences(this@MainActivity).getString(SyllabusContainerFragment.CurrentSemesterKey,"Non-existent") != "Non-existent") {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("版本更新提醒")
                        .setMessage(result.versionInfo?.description ?: "")
                        .setPositiveButton("更新"
                        ) { dialog, which ->
                            val uri: Uri = Uri.parse("http://d.firim.top/stupie")
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            startActivity(intent)
                        }
                        .setNegativeButton(
                            "忽略"
                        ) { dialog, which ->
                            ToastUtil.showLong(
                                this@MainActivity,
                                "后续可去设置->关于我们手动升级"
                            )
                        }
                        .create().show()
                }
            }
        })
    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    private fun initBottomNavigationView() {
        findViewById<BottomNavigationView>(R.id.navigation).setOnNavigationItemSelectedListener(
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                    if (!trigger.canTrigger()) return false
                    onTabItemSelected(p0.itemId)
                    return true
                }
            })
    }

    private fun onTabItemSelected(id: Int) {
        when(id){
            R.id.syllabus -> 0
            R.id.oa -> 1
            R.id.personal -> 2
            else -> 0
        }.let {
            mainViewPager.currentItem = it
        }
    }
}
