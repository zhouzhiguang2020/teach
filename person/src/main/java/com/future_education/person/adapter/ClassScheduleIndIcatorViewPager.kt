package com.future_education.person.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.future_education.person.R
import com.future_education.person.fragment.DailyCoursePageFragment
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.constants.CommonConstants
import com.futureeducation.commonmodule.utill.ScreenUtils
import com.shizhefei.view.indicator.IndicatorViewPager

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/24
 * Time: 11:47
 * 课程表主要适配器
 */
class ClassScheduleIndIcatorViewPager : IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private var activity: CommonActivity? = null

    private var inflater: LayoutInflater? = null
    private var schooltimes = mutableListOf<String>()

    constructor(
        fragmentManager: FragmentManager?,
        activity: CommonActivity?
    ) : super(fragmentManager) {
        this.activity = activity
        inflater = LayoutInflater.from(activity)
        var choose_texts = activity!!.resources.getStringArray(R.array.schooltime_texts)
        schooltimes = choose_texts.toMutableList()

    }

    override fun getCount(): Int {
        return schooltimes.size
    }

    override fun getViewForTab(position: Int, convertView: View?, container: ViewGroup?): View {
        //顶部viewpage indicator
        var view = inflater!!.inflate(
            R.layout.class_schedule_viewpage_indicator_layout,
            null,
            false
        )
        var container = view.findViewById<FrameLayout>(R.id.container)
        val padding: Int = ScreenUtils.dip2px(activity, 17f)
        container.setPadding(padding, 0, padding, 0)
        var textview = view.findViewById<TextView>(R.id.text)
        textview.text = schooltimes.get(position)
        return view

    }

    override fun getFragmentForPage(position: Int): Fragment {
        var fragment = DailyCoursePageFragment()
        var args = Bundle()
        args.putInt(CommonConstants.FRAGMENT_POSITION, position)
        fragment.arguments = args
        return fragment
    }
}