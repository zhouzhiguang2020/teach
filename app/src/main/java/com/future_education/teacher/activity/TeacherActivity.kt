package com.future_education.teacher.activity


import android.os.Bundle
import android.view.Gravity
import com.apkfuns.logutils.LogUtils
import com.billy.cc.core.component.CC
import com.dylanc.viewbinding.nonreflection.binding
import com.future_education.teacher.R

import com.future_education.teacher.databinding.ActivityMainBinding

import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.component.CompocomponentName
import com.futureeducation.commonmodule.event.LoginOutEvent
import com.futureeducation.commonmodule.fragment.CommonFragment
import com.futureeducation.commonmodule.view.MainNavigateTabBar
import com.gyf.immersionbar.ktx.immersionBar
import com.threshold.rxbus2.RxBus
import com.threshold.rxbus2.annotation.RxSubscribe
import com.threshold.rxbus2.util.EventThread

class TeacherActivity : CommonActivity() {

    private val binding by binding(ActivityMainBinding::inflate)
    private var containerId: Int = 0
    private var homeworkframgent: CommonFragment? = null
    private var learningconditionframgent: CommonFragment? = null
    private var assistantFragment: CommonFragment? = null
    private var personfragment: CommonFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.white)
            statusBarDarkFont(true, 0.2f)
            navigationBarColor(R.color.white)
        }
        containerId = binding.container.id
        initview()
        RxBus.getDefault().register(this)
    }

    private fun initview() {
        var homeworkcc = CC.obtainBuilder(CompocomponentName.HOMEWORK_COMPINENR)
            .setActionName(CompocomponentName.HOMEWORK_FRAGMENT)
            .build()
        var homeworkresult = homeworkcc.call()
        var fragment = homeworkresult.getDataItemWithNoKey<CommonFragment>()
        if (fragment != null) {
            homeworkframgent = fragment
            binding.navigate.addTab(
                homeworkframgent,
                MainNavigateTabBar.TabParam(
                    R.drawable.ic_homework,
                    R.drawable.ic_homework_select,
                    R.string.homework
                )
            )
        }
        var learningcondition_cc = CC.obtainBuilder(CompocomponentName.LEARNINGCONDITION_COMPINENR)
            .setActionName(CompocomponentName.LEARNINGCONDITION_FRAGMENT)
            .build()
        var learningcondition_res = learningcondition_cc.call()
        var fragment1 = learningcondition_res.getDataItemWithNoKey<CommonFragment>()
        if (fragment1 != null) {
            learningconditionframgent = fragment1
            binding.navigate.addTab(
                learningconditionframgent,
                MainNavigateTabBar.TabParam(
                    R.drawable.ic_learningcondition,
                    R.drawable.ic_learningcondition_select,
                    R.string.learningcondition
                )
            )
        }

        var assistant_cc = CC.obtainBuilder(CompocomponentName.ASSISTANT_COMPINENR)
            .setActionName(CompocomponentName.ASSISTANT_FRAGMENT)
            .build()
        var assistant_res = assistant_cc.call()
        var fragment2 = assistant_res.getDataItemWithNoKey<CommonFragment>()
        if (fragment2 != null) {
            assistantFragment = fragment2
            binding.navigate.addTab(
                assistantFragment,
                MainNavigateTabBar.TabParam(
                    R.drawable.ic_assistant,
                    R.drawable.ic_assistant_select,
                    R.string.assistant
                )
            )
        }

        var person_cc = CC.obtainBuilder(CompocomponentName.PERSON_COMPINENR)
            .setActionName(CompocomponentName.PERSON_FRAGMENT)
            .build()
        var person_res = person_cc.call()
        var fragment3 = person_res.getDataItemWithNoKey<CommonFragment>()
        if (fragment3 != null) {
            personfragment = fragment3
            binding.navigate.addTab(
                personfragment,
                MainNavigateTabBar.TabParam(
                    R.drawable.ic_person,
                    R.drawable.ic_person_select,
                    R.string.person
                )
            )
        }
        binding.navigate.gravity = Gravity.BOTTOM
        binding.navigate.currentSelectedTab = 2
    }

    //退出事件
    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun event(event: LoginOutEvent) {
        LogUtils.e("退出登录事件--")
        finish()

    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().unregister(this)
    }
}