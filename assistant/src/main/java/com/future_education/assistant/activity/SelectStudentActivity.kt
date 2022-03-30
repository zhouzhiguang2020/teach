package com.future_education.assistant.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apkfuns.logutils.LogUtils
import com.dylanc.viewbinding.nonreflection.binding
import com.future_education.assistant.R
import com.future_education.assistant.databinding.ActivityClassroomAssessmentLayoutBinding
import com.future_education.assistant.databinding.ActivitySelectStudentLayoutBinding
import com.futureeducation.commonmodule.activities.CommonActivity
import com.gyf.immersionbar.ktx.immersionBar

/*
 * @Author wljy
 * @Date 2021/11/27
 * @Des 课堂补评选择学生
 * h获取当前课程下的学生
 */
class SelectStudentActivity : CommonActivity(), View.OnClickListener {

    private var recyclerView: RecyclerView? =null
    private val binding by binding(ActivitySelectStudentLayoutBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_select_student_layout)
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
        binding.topLayout.titleTxt.text = getString(R.string.select_student)
        binding.topLayout.rightText.apply {
            this.visibility = View.VISIBLE
            this.setTextColor(
                ContextCompat.getColor(
                    this@SelectStudentActivity,
                    R.color.button_normal_style1
                )
            )
            this.setText(R.string.check_all)
            this.setOnClickListener(this@SelectStudentActivity)
        }
        binding.confirm.setOnClickListener(this)
        recyclerView=binding.recyclerview
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager=manager
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.back -> {
                finish()
            }
            R.id.right_text -> {
                LogUtils.w("全选点击-")
            }
            R.id.confirm->{

            }
        }
    }
}