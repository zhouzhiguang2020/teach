package com.future_education.assistant.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.apkfuns.logutils.LogUtils

import com.dylanc.viewbinding.nonreflection.binding
import com.future_education.assistant.R
import com.future_education.assistant.adapter.ClassroomAssessmentIcatorViewPager
import com.future_education.assistant.constant.AssistantConstant
import com.future_education.assistant.databinding.ActivityClassroomAssessmentLayoutBinding
import com.future_education.assistant.fragment.GroupAppraisalFragment
import com.future_education.assistant.fragment.StudentAssessmentFragment
import com.future_education.assistant.model.TeacherClassBean
import com.future_education.assistant.scrollbar.DrawableBar
import com.future_education.person.dialog.SelectClassCourseDialog
import com.future_education.person.listener.OnSelectClassCurseOptionListener
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.fragment.CommonFragment
import com.gyf.immersionbar.ktx.immersionBar
import com.shizhefei.view.indicator.Indicator
import com.shizhefei.view.indicator.IndicatorViewPager

import com.shizhefei.view.indicator.slidebar.ScrollBar
import com.shizhefei.view.indicator.transition.OnTransitionTextListener
import java.util.ArrayList

/*
 * @Author wljy
 * @Date 2021/11/25
 * @Des 课堂评价
 */
class ClassroomAssessmentActivity : CommonActivity(), View.OnClickListener {
    private val binding by binding(ActivityClassroomAssessmentLayoutBinding::inflate)
    private var indicator: Indicator? = null
    private var viewPager: ViewPager? = null
    private var indicatorViewPager: IndicatorViewPager? = null
    private var adapter: ClassroomAssessmentIcatorViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_classroom_assessment_layout)
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.white)
            statusBarDarkFont(true, 0.2f)
            navigationBarColor(R.color.white)
        }
        initview()
    }

    private fun initview() {
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
        fragments.add(StudentAssessmentFragment())
        fragments.add(GroupAppraisalFragment())
        adapter = ClassroomAssessmentIcatorViewPager(
            supportFragmentManager,
            this,
            fragments
        )
        indicatorViewPager!!.adapter = adapter
        //indicator.isSplitAuto()
        binding.topLayout.back.setOnClickListener(this)
        binding.topLayout.titleTxt.text = getString(R.string.classroom_assessment)
        binding.topLayout.rightText.visibility = View.VISIBLE
        binding.topLayout.rightText.text = getString(R.string.all_the_class)
        binding.topLayout.rightText.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.button_normal_style1
            )
        )
        binding.topLayout.rightText.setOnClickListener(this)
        binding.evaluationIndicators.setOnClickListener(this)
        binding.plementaryComments.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.back -> {
                finish()
            }
            R.id.right_text -> {
                //全部班级
                var classList = mutableListOf<TeacherClassBean>()
                var classBean = TeacherClassBean()
                classBean.class_name = "一年级一班"
                var classBean2 = TeacherClassBean()
                classBean2.class_name = "一年级二班"
                var classBean3 = TeacherClassBean()
                classBean3.class_name = "一年级三班"
                var classBean4 = TeacherClassBean()
                classBean4.class_name = "一年级四班"
                var classBean5 = TeacherClassBean()
                classBean5.class_name = "一年级五班"
                var classBean6 = TeacherClassBean()
                classBean6.class_name = "一年级六班"
                var classBean7 = TeacherClassBean()
                classBean7.class_name = "一年级七班"
                classList.add(classBean)
                classList.add(classBean2)
                classList.add(classBean3)
                classList.add(classBean4)
                classList.add(classBean5)
                classList.add(classBean6)
                classList.add(classBean7)
                var dialog = SelectClassCourseDialog()
                var args = Bundle()
                args.putParcelableArrayList(
                    AssistantConstant.SUBJECT_COURSE,
                    classList as ArrayList<out TeacherClassBean>
                )
                dialog.arguments = args
                dialog.show(supportFragmentManager, "dialog")
                dialog.setOnSelectCoursesOptionListener(object : OnSelectClassCurseOptionListener {
                    override fun OnSelectClassOptionsItem(selectbean: TeacherClassBean) {
                        LogUtils.e("选择的班级是：" + selectbean.toString())
                    }
                }

                )
            }
            R.id.evaluation_indicators -> {
                var intent = Intent(this, EvaluationIndicatorsActivity::class.java)
                startActivity(intent)
            }
            R.id.plementary_comments -> {
                //补评第一步选择学生
                var intent = Intent(this, SelectStudentActivity::class.java)
                startActivity(intent)
            }

        }
    }
}
