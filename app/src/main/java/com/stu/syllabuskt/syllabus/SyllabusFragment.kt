package com.stu.syllabuskt.syllabus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stu.syllabuskt.R
import com.stu.syllabuskt.base.BaseFragment
import com.stu.syllabuskt.bean.Lesson

private const val POSITION_PARAM = "position_param"

class SyllabusFragment : BaseFragment(), ISyllabusContract.IView {
    private var selectedWeek: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedWeek = it.getInt(POSITION_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_syllabus, container, false)
    }

    override fun showMSG(msg: String) {

    }

    override fun showSyllabus(lessonList: List<Lesson>) {

    }

    companion object {
        @JvmStatic
        fun newInstance(selectedWeek: Int) =
            SyllabusFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION_PARAM, selectedWeek)
                }
            }
    }
}