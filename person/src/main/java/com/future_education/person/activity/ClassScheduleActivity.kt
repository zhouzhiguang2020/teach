package com.future_education.person.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.apkfuns.logutils.LogUtils
import com.dylanc.viewbinding.nonreflection.binding
import com.future_education.person.R
import com.future_education.person.adapter.ClassScheduleIndIcatorViewPager
import com.future_education.person.databinding.ActivityClassScheduleLayoutBinding
import com.future_education.person.transition.ClassScheduleTransition
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.utill.TimeUtil
import com.gyf.immersionbar.ktx.immersionBar
import com.shizhefei.view.indicator.Indicator
import com.shizhefei.view.indicator.IndicatorViewPager
import com.shizhefei.view.indicator.slidebar.SpringBar

class ClassScheduleActivity : CommonActivity(), View.OnClickListener {

    private val binging by binding(ActivityClassScheduleLayoutBinding::inflate)
    private var indicator: Indicator? = null
    private var viewPager: ViewPager? = null
    private var indicatorViewPager: IndicatorViewPager? = null
    private var adapter: ClassScheduleIndIcatorViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_class_schedule_layout)
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.white)
            statusBarDarkFont(true, 0.2f)
            navigationBarColor(R.color.white)
        }
        initview()
    }

    private fun initview() {
        binging.topLayout.back.setOnClickListener(this)
        binging.topLayout.titleTxt.text = getString(R.string.class_schedule)
        indicator = binging.indicator
        viewPager = binging.viewpage
        var onTransitionListener = ClassScheduleTransition()
        var unSelectColorId = ContextCompat.getColor(this, R.color.white)
        var selectColorId = ContextCompat.getColor(this, R.color.black)
        onTransitionListener.setColor(selectColorId, unSelectColorId)
        indicator!!.setOnTransitionListener(onTransitionListener)
        var scrollBar =
            SpringBar(
                this, ContextCompat.getColor(this, R.color.white),
                0.7f,
                0.5f
            )

        indicator!!.setScrollBar(scrollBar)
        indicatorViewPager = IndicatorViewPager(indicator, viewPager)
        adapter = ClassScheduleIndIcatorViewPager(
            supportFragmentManager,
            this
        )
        indicatorViewPager!!.adapter = adapter
        var mWay = TimeUtil.StringData()
        LogUtils.e("看看今天周几" + mWay)
        if (TextUtils.equals(mWay, "6") || TextUtils.equals(mWay, "7")) {
            indicatorViewPager!!.setCurrentItem(0, false)
        } else {
            //默认显示今天周几的课程 主页这里重0 开始的
            indicatorViewPager!!.setCurrentItem(mWay.toInt() - 1, false)
        }

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.back -> {
                finish()
            }

        }
    }
}