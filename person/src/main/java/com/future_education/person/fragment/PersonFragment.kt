package com.future_education.homework.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.TextView
import androidx.core.content.ContextCompat
import cc.taylorzhang.singleclick.onSingleClick
import com.apkfuns.logutils.LogUtils
import com.billy.cc.core.component.CC
import com.future_education.person.R
import com.future_education.person.activity.*
import com.future_education.person.databinding.FragmentPersonLayoutBinding
import com.futureeducation.commonmodule.component.CompocomponentName
import com.futureeducation.commonmodule.constants.CommonConstants
import com.futureeducation.commonmodule.dialog.GraceSimpleDialog
import com.futureeducation.commonmodule.event.LoginOutEvent
import com.futureeducation.commonmodule.extension.viewBinding


import com.futureeducation.commonmodule.fragment.CommonFragment
import com.futureeducation.commonmodule.listener.OnCommonDialogListener
import com.futureeducation.commonmodule.utill.SharedPreferencesUtil
import com.threshold.rxbus2.RxBus

/*
 * @Author wljy
 * @Date 2021/11/19
 * @Des 我的主页
 */
class PersonFragment : CommonFragment(), View.OnClickListener {

    private val binding by viewBinding(FragmentPersonLayoutBinding::bind)
    private var setting_text: TextView? = null
    override fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreateView(savedInstanceState)
        setContentView(R.layout.fragment_person_layout)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initview()
        RxBus.getDefault().register(this)
    }

    private fun initview() {
        binding.topLayout.back.visibility = View.GONE
        binding.topLayout.titleTxt.setText(R.string.mine)
        binding.studentManagement.setOnClickListener(this)
        binding.settingConditionsLayout.contentLayout.findViewById<TextView>(R.id.contact_us)
            .setOnClickListener(this)
        binding.settingConditionsLayout.contentLayout.findViewById<TextView>(R.id.user_notice)
            .setOnClickListener(this)
        binding.settingConditionsLayout.contentLayout.findViewById<TextView>(R.id.privacy_policy)
            .setOnClickListener(this)
        binding.settingConditionsLayout.contentLayout.findViewById<TextView>(R.id.withdraw_account)
            .setOnClickListener(this)
        setting_text = binding.settingConditionsLayout.headerLayout.findViewById(R.id.setting_text)
        val drawable2 = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_right_arrows
        )
        drawable2!!.setBounds(0, 0, 42, 42);    //需要设置图片的大小才能显示
        val drawable1 = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_up_arrows
        )
        drawable1!!.setBounds(0, 0, 42, 42);
        binding.settingConditionsLayout.setLayoutAnimationListener(object :
            Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                var isopen = binding.settingConditionsLayout!!.isOpened
                if (isopen) {
                    setting_text!!.setCompoundDrawables(null, null, drawable1, null)
                } else {
                    setting_text!!.setCompoundDrawables(null, null, drawable2, null)
                }

            }

            override fun onAnimationRepeat(animation: Animation?) {
            }


        })
        binding.settingConditionsLayout.contentLayout.findViewById<TextView>(R.id.contact_us)
            .setOnClickListener(this)
        binding.classSchedule.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.contact_us -> {
                //联系我们
                LogUtils.e("联系我们")
                var intent = Intent(context, ContactUsActivity::class.java)
                startActivity(intent)
            }
            R.id.student_management -> {

                var intent = Intent(context, StudentManagementActivity::class.java)
                startActivity(intent)
            }
            R.id.user_notice -> {
                LogUtils.e("用户须知")
                var intent = Intent(context, UserAgreementActivity::class.java)
                startActivity(intent)
            }
            R.id.privacy_policy -> {
                LogUtils.e("隐私政策")
                var intent = Intent(context, PrivacyPolicyActivity::class.java)
                startActivity(intent)
            }
            R.id.check_updates -> {

            }
            R.id.class_schedule -> {
                //课程表
                var intent = Intent(context, ClassScheduleActivity::class.java)
                startActivity(intent)


            }
            R.id.withdraw_account -> {
                var dialog = GraceSimpleDialog()
                dialog.show(childFragmentManager, "dialog")
                dialog.setMassageText(R.string.log_out_msg)
                dialog.setOnCommonDialogListener(object : OnCommonDialogListener {
                    override fun OnCommonDialogCancel() {

                    }

                    override fun OnCommonDialogConfirm() {
                        //
                        LogUtils.e("T退出登录了==")
                        var cc = CC.obtainBuilder(CompocomponentName.LOGIN_COMPINENR)
                            .setActionName(CompocomponentName.LOGIN_OUT)
                            .build()
                        cc.callAsync()
                        var sp = SharedPreferencesUtil.getInstance(activity)
                        var account = sp.getString(CommonConstants.USER_ACCOUNT, "")
                        var passwrod = sp.getString(CommonConstants.USER_PASS, "")
                        var service_api = sp.getString(CommonConstants.SERVICEAPI, "")
                        var service_api_list = sp.getString(CommonConstants.SERVICEAPILIST, "")
                        //退出登录不赞成清理全部
                        //SharedPreferencesUtil.getInstance(context).clearpreference()
                        sp.saveString(CommonConstants.USER_ACCOUNT, account)
                        sp.saveString(CommonConstants.USER_PASS, passwrod)
                        sp.saveString(CommonConstants.USER_ID, "")
                        sp.saveString(CommonConstants.USER_TOKEN, "")
                        if (service_api != "") {
                            sp.saveString(CommonConstants.SERVICEAPI, service_api)
                            sp.saveString(CommonConstants.SERVICEAPILIST, service_api_list)
                        }
                        RxBus.getDefault().post(LoginOutEvent())
                    }

                })

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getDefault().unregister(this)
    }
}


