package com.future_education.assistant.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apkfuns.logutils.LogUtils
import com.future_education.assistant.R
import com.future_education.assistant.activity.EditEvaluationActivity
import com.future_education.assistant.adapter.EvaluationIndicatorsAdapter
import com.future_education.assistant.model.EvaluationIndicatorItemBean
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.constants.CommonConstants
import com.futureeducation.commonmodule.fragment.LazyFragment


/**
 * A simple [Fragment] subclass.
 * Use the [AllEvaluationIndicatorsFragment.newInstance] factory method to
 * create an instance of this fragment.
 * 所有 评论指标
 */
class AllEvaluationIndicatorsFragment : LazyFragment() {


    private var layout: View? = null

    private var recyclerview: RecyclerView? = null
    private var adapter: EvaluationIndicatorsAdapter? = null
    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        super.onCreateViewLazy(savedInstanceState)
        layout = inflater.inflate(
            R.layout.fragment_all_evaluation_indicators_layout,
            null,
            false
        )
        setContentView(layout)
        initView()
    }

    private fun initView() {
        recyclerview = layout!!.findViewById(R.id.recyclerview)
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerview!!.layoutManager = manager
        var datas = mutableListOf<EvaluationIndicatorItemBean>()
        datas.add(EvaluationIndicatorItemBean())
        datas.add(EvaluationIndicatorItemBean())
        datas.add(EvaluationIndicatorItemBean())
        datas.add(EvaluationIndicatorItemBean())
        datas.add(EvaluationIndicatorItemBean())
        adapter = EvaluationIndicatorsAdapter(
            R.layout.item_evaluation_indicators_layout, datas,
            activity as CommonActivity?
        )
        recyclerview!!.adapter = adapter
        adapter!!.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.modification -> {
                    //修改编辑标签
                    LogUtils.e("修改标签：" + position)
                    var intent = Intent(context, EditEvaluationActivity::class.java)
                    var bean=adapter.getItem(position) as EvaluationIndicatorItemBean
                    intent.putExtra(CommonConstants.USER_NAME,bean)
                    startActivity(intent)

                }
                R.id.remove -> {
                    LogUtils.e("移除标签：" + position)
                }
            }
        }
    }


}