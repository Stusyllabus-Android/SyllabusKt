package com.stu.syllabuskt.syllabus

import android.content.Context
import android.util.Log
import com.stu.syllabuskt.bean.Lesson
import com.stu.syllabuskt.bean.YiBanTimeTable
import java.util.*

/**
 * Create by yuan on 2020/12/8
 */
class SyllabusModel(mContext: Context) : ISyllabusContract.IModel {

    private val TAG = "SyllabusModel"

    override fun filterTables(
        tableBeanList: List<YiBanTimeTable.TableBean?>?,
        currentSemester: String?
    ): List<YiBanTimeTable.TableBean?> {
        val currentTables: MutableList<YiBanTimeTable.TableBean> = LinkedList<YiBanTimeTable.TableBean>()
        Log.d(TAG, "filterTables: all" + tableBeanList!!.size)
        for (i in tableBeanList.indices) {
            if (tableBeanList[i]!!.xnxqName.contains(
                    currentSemester!!.substring(
                        0,
                        9
                    )
                ) && tableBeanList[i]!!.xnxqName.contains(
                    currentSemester.substring(10)
                )
            ) {
                tableBeanList[i]?.let { currentTables.add(it) }
            }
        }
        Log.d(TAG, "filterTables: " + currentTables.size)
        return currentTables
    }

    /**
     * xnxq_name : 2017-2018学年秋季学期
     * kkb_key : 95421
     * kc_name : [ELC1]英语(ELC1)
     * js_name : 苏雪枫
     * ks_name : D座307
     * sj_name : 第1-16周，周二(12节)，周五(34节)
     * sj_name : 第1-16周，周四(单89节)，周五(34节) (单周的情况)
     */

    /**
     * name : [PED1060A]网球
     * id : 112857
     * teacher : 王家君
     * room : 网球场
     * duration : 3 -18
     * days : {"w0":"None","w1":"None","w2":"12","w3":"None","w4":"None","w5":"None","w6":"None"}
     * credit : 0.0
     */
    override fun convertTablesToLessons(currentTables: List<YiBanTimeTable.TableBean?>?): List<Lesson?>? {
        val lessonBeanList: MutableList<Lesson> = LinkedList<Lesson>()

        for (i in currentTables!!.indices) {
            val time: List<String> = currentTables[i]!!.getSjName().split("，")
            if (time.size == 1) continue  //网络课程不进行显示
            var duration: String? = null
            val daysBean = Lesson.DaysBean()
            for (j in time.indices) {
                if (j == 0) {
                    val indexOfBeginWeek = currentTables[i]!!.getSjName().indexOf("第")
                    val indexOfEndWeek = currentTables[i]!!.getSjName().indexOf("周")
                    duration =
                        time[0].substring(indexOfBeginWeek + 1, indexOfEndWeek - indexOfBeginWeek)
                } else {
                    var indexOfSectionBeginBound = time[j].indexOf("(")
                    val indexOfSectionEndBound = time[j].indexOf(")")
                    if (indexOfSectionBeginBound >= indexOfSectionEndBound) continue  //军事训练和理论课返回的数据不规范
                    var first: String? = null
                    if (time[j][indexOfSectionBeginBound + 1] == '单' || time[j][indexOfSectionBeginBound + 1] == '双') indexOfSectionBeginBound++
                    first = if (time[j][indexOfSectionBeginBound + 1] == '0') {
                        "10"
                    } else if (time[j][indexOfSectionBeginBound + 1] == 'A') {
                        "11"
                    } else if (time[j][indexOfSectionBeginBound + 1] == 'B') {
                        "12"
                    } else if (time[j][indexOfSectionBeginBound + 1] == 'C') {
                        "13"
                    } else time[j][indexOfSectionBeginBound + 1].toString() + ""
                    var last: String? = null
                    last = if (time[j][indexOfSectionEndBound - 2] == '0') {
                        "10"
                    } else if (time[j][indexOfSectionEndBound - 2] == 'A') {
                        "11"
                    } else if (time[j][indexOfSectionEndBound - 2] == 'B') {
                        "12"
                    } else if (time[j][indexOfSectionEndBound - 2] == 'C') {
                        "13"
                    } else time[j][indexOfSectionEndBound - 2].toString() + ""
                    Log.d(
                        TAG,
                        "convertTablesToLessons: " + time[j] + time[j][indexOfSectionBeginBound + 1] + " " + time[j][indexOfSectionEndBound - 2]
                    )
                    when (time[j].substring(0, 2)) {
                        "周日" -> daysBean.setW0("$first-$last")
                        "周一" -> daysBean.setW1("$first-$last")
                        "周二" -> daysBean.setW2("$first-$last")
                        "周三" -> daysBean.setW3("$first-$last")
                        "周四" -> daysBean.setW4("$first-$last")
                        "周五" -> daysBean.setW5("$first-$last")
                        "周六" -> daysBean.setW6("$first-$last")
                    }
                }
            }
            val indexOfKcName = currentTables[i]!!.getKcName().indexOf(']') //去掉课程名前方括号中的内容
            val showLessonBean = Lesson(
                currentTables[i]!!.kcName.substring(indexOfKcName + 1), java.lang.String.valueOf(
                    currentTables[i]!!.getKkbKey()
                ), currentTables[i]!!.getJsName(), currentTables[i]!!
                    .getKsName(), duration, daysBean, "0"
            )
            lessonBeanList.add(showLessonBean)
        }
        return lessonBeanList
    }
}