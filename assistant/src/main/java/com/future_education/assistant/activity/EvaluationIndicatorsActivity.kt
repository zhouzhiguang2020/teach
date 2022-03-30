package com.future_education.assistant.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.apkfuns.logutils.LogUtils
import com.dylanc.viewbinding.nonreflection.binding
import com.future_education.assistant.R
import com.future_education.assistant.adapter.ClassroomAssessmentIcatorViewPager
import com.future_education.assistant.adapter.EvaluationIndicatorsViewPager
import com.future_education.assistant.databinding.ActivityClassroomAssessmentLayoutBinding
import com.future_education.assistant.databinding.ActivityEvaluationIndicatorsLayoutBinding
import com.future_education.assistant.fragment.AllEvaluationIndicatorsFragment
import com.future_education.assistant.fragment.GroupAppraisalFragment
import com.future_education.assistant.fragment.StudentAssessmentFragment
import com.future_education.assistant.scrollbar.DrawableBar
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.fragment.CommonFragment
import com.gyf.immersionbar.ktx.immersionBar
import com.shizhefei.view.indicator.Indicator
import com.shizhefei.view.indicator.IndicatorViewPager
import com.shizhefei.view.indicator.slidebar.ScrollBar
import com.shizhefei.view.indicator.transition.OnTransitionTextListener

/*
 * @Author wljy
 * @Date 2021/11/26
 * @Des 自定义评价指标
 */
class EvaluationIndicatorsActivity : CommonActivity(), View.OnClickListener {
    private val binding by binding(ActivityEvaluationIndicatorsLayoutBinding::inflate)

    private var indicator: Indicator? = null
    private var viewPager: ViewPager? = null
    private var indicatorViewPager: IndicatorViewPager? = null
    private var adapter: EvaluationIndicatorsViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_evaluation_indicators_layout)
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.white)
            statusBarDarkFont(true, 0.2f)
            navigationBarColor(R.color.white)
        }
        initview()
    }

    private fun initview() {
        binding.topLayout.back.setOnClickListener(this)
        binding.topLayout.titleTxt.text = getString(R.string.evaluation_indicators)
        binding.topLayout.rightText.visibility = View.VISIBLE
        binding.topLayout.rightText.text = getString(R.string.new_evaluation)
        binding.topLayout.rightText.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.button_normal_style1
            )
        )
        indicator = binding.indicator
        viewPager = binding.viewpage
        var unSelectTextColor = ContextCompat.getColor(this, R.color.textGrayDeep_8)
        var SelectTextColor = ContextCompat.getColor(this, R.color.white)
        // 设置滚动监听
        indicator!!.setOnTransitionListener(
            OnTransitionTextListener().setColor(
                SelectTextColor,
                unSelectTextColor
            )
        )
        var drawableBar = DrawableBar(
            this,
            R.drawable.classroom_assessment_indicator_select_background_shape,
            ScrollBar.Gravity.CENTENT_BACKGROUND
        )

        indicator!!.setScrollBar(drawableBar)

        indicatorViewPager = IndicatorViewPager(indicator, viewPager)
        var fragments = mutableListOf<CommonFragment>()
        fragments.add(AllEvaluationIndicatorsFragment())
        fragments.add(AllEvaluationIndicatorsFragment())
        fragments.add(AllEvaluationIndicatorsFragment())
        adapter = EvaluationIndicatorsViewPager(
            supportFragmentManager,
            this,
            fragments
        )
        indicatorViewPager!!.adapter = adapter
        binding.topLayout.rightText.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.back -> {
                finish()
            }
            R.id.right_text -> {
                LogUtils.e("新增评价-")
                var intent = Intent(this, NewEvaluationActivity::class.java)
                jumpActivity(intent)
            }
        }
    }
}