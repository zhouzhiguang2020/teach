package com.future_education.person.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dylanc.viewbinding.nonreflection.binding

import com.future_education.person.R
import com.future_education.person.adapter.DailyCoursePageAdapter
import com.future_education.person.databinding.FragmentDailyCoursePageLayoutBinding
import com.future_education.person.databinding.FragmentPersonLayoutBinding
import com.future_education.person.model.DailyClasseBean
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.constants.CommonConstants
import com.futureeducation.commonmodule.extension.viewBinding

import com.futureeducation.commonmodule.fragment.LazyFragment


/*
 * @Author wljy
 * @Date 2021/11/24
 * @Des 老师每天课程
 */
class DailyCoursePageFragment : LazyFragment() {

    private var layout: View? = null
    private var position: Int = -1
    private var recyclerview: RecyclerView? = null
    private var adapter: DailyCoursePageAdapter? = null

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        super.onCreateViewLazy(savedInstanceState)
        layout = inflater.inflate(
            R.layout.fragment_daily_course_page_layout,
            null,
            false
        )
        setContentView(layout)
        initView()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun initView() {
        position = arguments!!.getInt(CommonConstants.FRAGMENT_POSITION, -1)
        recyclerview = layout!!.findViewById(R.id.recyclerview)
        var manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerview!!.layoutManager = manager
        var choose_texts = activity!!.resources.getStringArray(R.array.teacher_class_time)
        var classPeriods = choose_texts.toMutableList()
        var datas = mutableListOf<DailyClasseBean>()
        classPeriods.forEachIndexed { index, s ->
            var dailyClasseBean = DailyClasseBean()
            dailyClasseBean.grade = "二年级二班"
            dailyClasseBean.subject = "语文"
            dailyClasseBean.sortingclass = (index + 1).toString()
            if (index == 4) {
                dailyClasseBean.isIslunchbreak = true
            } else {
                dailyClasseBean.isIslunchbreak = false
            }
            if (index == 6) {
                //说明没课
                dailyClasseBean.grade = ""
                dailyClasseBean.subject = ""
            }
            dailyClasseBean.classtimeperiod = s
            datas.add(dailyClasseBean)
        }

        adapter = DailyCoursePageAdapter(
            R.layout.item_daily_course_page_layout, datas,
            activity as CommonActivity?
        )
        recyclerview!!.adapter = adapter
    }

}