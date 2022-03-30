package com.future_education.assistant.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apkfuns.logutils.LogUtils
import com.future_education.assistant.R
import com.future_education.assistant.adapter.GroupAppraisalAdapter
import com.future_education.assistant.adapter.StudentAssessmentAdapter
import com.future_education.assistant.model.GroupAppraisalBean
import com.future_education.assistant.model.StudentAssessmentBean
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.fragment.LazyFragment


/**

 * 小组评价
 */
class GroupAppraisalFragment : LazyFragment() {


    private var layout: View? = null

    private var recyclerview: RecyclerView? = null
    private var adapter: GroupAppraisalAdapter? =null
    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        super.onCreateViewLazy(savedInstanceState)
        layout = inflater.inflate(
            R.layout.fragment_group_appraisal_layout,
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
        var datas = mutableListOf<GroupAppraisalBean>()
        datas.add(GroupAppraisalBean())
        datas.add(GroupAppraisalBean())
        datas.add(GroupAppraisalBean())
        datas.add(GroupAppraisalBean())
        datas.add(GroupAppraisalBean())
        adapter = GroupAppraisalAdapter(
            R.layout.item_group_appraisal_layout, datas,
            activity as CommonActivity?
        )
        recyclerview!!.adapter = adapter
        adapter!!.setOnItemClickListener { adapter, view, position ->
            LogUtils.e("点击条目+" + position)
        }
    }




}