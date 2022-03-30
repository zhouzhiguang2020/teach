package com.future_education.homework.component

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.CCUtil
import com.billy.cc.core.component.IComponent
import com.billy.cc.core.component.remote.RemoteCC
import com.future_education.homework.fragment.PersonFragment
import com.future_education.person.activity.PrivacyPolicyActivity
import com.future_education.person.activity.UserAgreementActivity
import com.futureeducation.commonmodule.component.CompocomponentName

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/19
 * Time: 15:05
 * 老师作业组件
 */
class PersonComponent : IComponent {
    private var context: Context? = null

    override fun getName(): String {
        return CompocomponentName.PERSON_COMPINENR
    }

    /**
     * 组件被调用时的入口
     * 要确保每个逻辑分支都会调用到CC.sendCCResult，
     * 包括try-catch,if-else,switch-case-default,startActivity
     *
     * @param cc 组件调用对象，可从此对象中获取相关信息
     * @return true:将异步调用CC.sendCCResult(...),用于异步实现相关功能，例如：文件加载、网络请求等
     * false:会同步调用CC.sendCCResult(...),即在onCall方法return之前调用，否则将被视为不合法的实现
     */
    override fun onCall(cc: CC?): Boolean {
        context = cc!!.context
        val actionName = cc.actionName
        when (actionName) {

            CompocomponentName.PERSON_FRAGMENT -> {
                //调用的是获取主fragment
                var fragment = PersonFragment()
                CC.sendCCResult(cc.callId, CCResult.successWithNoKey(fragment))
                return false
            }
            CompocomponentName.OPEN_USER_AGREEMENT -> {
                openUserAgreementctivity(cc)
                return false

            }
            CompocomponentName.OPEN_PRIVACY_POLICY -> {
                openPrivacyPolicytctivity(cc)
                return false

            }

        }
        return false
    }

    //打开隐私政策
    private fun openPrivacyPolicytctivity(cc: CC) {
        val intent = CCUtil.createNavigateIntent(cc, PrivacyPolicyActivity::class.java)
        val remoteCC = RemoteCC(cc)
        intent.putExtra(CCUtil.EXTRA_KEY_REMOTE_CC, remoteCC)
        intent.putExtra(CCUtil.EXTRA_KEY_CALL_ID, cc.callId)
        if (context !is Activity) {
            //调用方没有设置context或app间组件跳转，context为application
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        context!!.startActivity(intent)
        CC.sendCCResult(cc.callId, CCResult.success())
    }

    //打开用户须知
    private fun openUserAgreementctivity(cc: CC) {
        val intent = CCUtil.createNavigateIntent(cc, UserAgreementActivity::class.java)
        val remoteCC = RemoteCC(cc)
        intent.putExtra(CCUtil.EXTRA_KEY_REMOTE_CC, remoteCC)
        intent.putExtra(CCUtil.EXTRA_KEY_CALL_ID, cc.callId)
        if (context !is Activity) {
            //调用方没有设置context或app间组件跳转，context为application
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        context!!.startActivity(intent)
        CC.sendCCResult(cc.callId, CCResult.success())
    }
}