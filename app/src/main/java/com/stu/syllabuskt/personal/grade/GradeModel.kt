package com.stu.syllabuskt.personal.grade

import android.content.Context
import android.os.Parcelable
import android.util.Log
import com.stu.syllabuskt.api.OfficialAccountApi
import com.stu.syllabuskt.api.RetrofitProvider
import kotlinx.android.parcel.Parcelize
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Response

/**
 * Create by yuan on 2020/12/15
 */
class GradeModel(private val mContext: Context) {

    private val TAG = "GradeModel"

    private val officialAccountApi: OfficialAccountApi = RetrofitProvider.getOfficialWCRetrofit(mContext).create(OfficialAccountApi::class.java)

    // TODO: 2020/12/15 成绩缓存 
    fun getGradeDataFromCache() {
        
    }

    fun getGradeDataFromNet(account: String, password: String, listener: GetGradeListener) {
        officialAccountApi.login(account, password, "登录", "", "")
            .enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.i(TAG, response.body().toString())
                    officialAccountApi.getGrade().enqueue(object : retrofit2.Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            Log.i(TAG, response.body().toString())
                            /**
                             * <p>学期:2019-2020学年春季学期课程:古代戏曲经典专题研究成绩:86</p>
                             */
                            val gradeArray = arrayListOf<Grade>()
                            Jsoup.parseBodyFragment(response.body())
                                .getElementsByTag("p").forEach { element ->
                                if (element.text().split(":").size == 4) {
                                    gradeArray.add(generateGrade(element.text()))
                                }
                            }
                            listener.onSuccess(gradeArray)
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            listener.onFailure(t.message ?: "")
                        }
                    })
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    listener.onFailure(t.message ?: "")
                }
            })
    }

    private fun generateGrade(content: String): Grade {
        val rawGradeInfo = content.split(":")
        Log.i(TAG, "academicYear: ${rawGradeInfo[1].substring(0, 9)}")
        Log.i(TAG, "semester: ${rawGradeInfo[1].substring(11, 15)}")
        Log.i(TAG, "lessonName: ${rawGradeInfo[2].substring(0, rawGradeInfo[2].indexOf("成"))}")
        Log.i(TAG, "grade: ${rawGradeInfo[3].toInt()}")
        return Grade(rawGradeInfo[1].substring(0, 9) + rawGradeInfo[1].substring(11, 15), rawGradeInfo[2].substring(0, rawGradeInfo[2].indexOf("成")), rawGradeInfo[3].toInt())
    }

    interface GetGradeListener {
        fun onFailure(msg: String)
        fun onSuccess(gradeArr: ArrayList<Grade>)
    }

}

@Parcelize
data class Grade(val semester: String, val lessonName: String, val grade: Int) : Parcelable {
    override fun toString(): String {
        return "semester: $semester, lessonName: $lessonName, grade: $grade"
    }
}