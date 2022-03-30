package com.future_education.assistant.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apkfuns.logutils.LogUtils
import com.dylanc.viewbinding.nonreflection.binding
import com.future_education.assistant.R
import com.future_education.assistant.adapter.StudentAssessmentAdapter
import com.future_education.assistant.databinding.FragmentStudentAssessmentLayoutBinding
import com.future_education.assistant.model.StudentAssessmentBean
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.extension.viewBinding
import com.futureeducation.commonmodule.fragment.LazyFragment


/**
 * 学生评价fragment
 */
class StudentAssessmentFragment : LazyFragment() {


    private var layout: View? = null

    private var recyclerview: RecyclerView? = null
    private val binding by binding(FragmentStudentAssessmentLayoutBinding::bind)
    private var adapter: StudentAssessmentAdapter? = null
    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        super.onCreateViewLazy(savedInstanceState)
        layout = inflater.inflate(
            R.layout.fragment_student_assessment_layout,
            null,
            false
        )
        setContentView(layout)
        initView()
    }

    private fun initView() {
        layout!!.let {
            recyclerview = it.findViewById(R.id.recyclerview)
        }
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerview!!.layoutManager = manager
        var datas = mutableListOf<StudentAssessmentBean>()
        datas.add(StudentAssessmentBean())
        datas.add(StudentAssessmentBean())
        datas.add(StudentAssessmentBean())
        datas.add(StudentAssessmentBean())
        datas.add(StudentAssessmentBean())
        adapter = StudentAssessmentAdapter(
            R.layout.item_student_assessment_layout, datas,
            activity as CommonActivity?
        )
        recyclerview!!.adapter = adapter
        adapter!!.setOnItemClickListener { adapter, view, position ->
            LogUtils.e("点击条目+" + position)
        }
    }


}