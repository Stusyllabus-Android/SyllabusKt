package com.stu.syllabuskt.syllabus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stu.syllabuskt.R

private const val POSITION_PARAM = "position_param"

class SyllabusFragment : Fragment() {
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