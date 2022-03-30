package com.future_education.assistant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.future_education.assistant.R
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.fragment.CommonFragment
import com.futureeducation.commonmodule.utill.DimenUtils
import com.shizhefei.view.indicator.IndicatorViewPager

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/26
 * Time: 14:29
 * 课堂评价viwepage适配器
 */
class ClassroomAssessmentIcatorViewPager : IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private var activity: CommonActivity? = null

    private var inflater: LayoutInflater? = null
    private var fragments = mutableListOf<CommonFragment>()
    private var names = mutableListOf<String>()

    constructor(
        fragmentManager: FragmentManager?,
        activity: CommonActivity?,
        fragments: List<CommonFragment>
    ) : super(fragmentManager) {
        this.activity = activity
        this.fragments = fragments as MutableList<CommonFragment>
        inflater = LayoutInflater.from(activity)
        names.add(activity!!.getString(R.string.student))
        names.add(activity!!.getString(R.string.group))

    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getViewForTab(position: Int, convertView: View?, container: ViewGroup?): View {
        var layout: View? = null
        if (layout == null) {
            layout = inflater!!.inflate(
                R.layout.classroom_assessment_indicator_text_layout,
                container,
                false
            )

        }
        val textView = layout as TextView
        textView.setText(names.get(position % names.size))
        val padding: Int = DimenUtils.dp2pxInt(10.toFloat())
        textView.setPadding(padding, 0, padding, 0)
        return layout!!

    }

    override fun getFragmentForPage(position: Int): Fragment {
        return fragments.get(position)
    }
}