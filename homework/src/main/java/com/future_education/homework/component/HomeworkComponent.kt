package com.future_education.homework.component

import android.content.Context
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.future_education.homework.fragment.HomeworkFragment
import com.futureeducation.commonmodule.component.CompocomponentName

/**
 * Created by .
 * User: ASUS
 * Date: 2021/11/19
 * Time: 15:05
 * 老师作业组件
 */
class HomeworkComponent : IComponent {
    private var context: Context? = null

    override fun getName(): String {
        return CompocomponentName.HOMEWORK_COMPINENR
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

            CompocomponentName.HOMEWORK_FRAGMENT -> {
                //调用的是获取主fragment
                var fragment = HomeworkFragment()
                CC.sendCCResult(cc.callId, CCResult.successWithNoKey(fragment))
                return false
            }
        }
        return false
    }
}