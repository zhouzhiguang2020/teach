package com.future_education.loginmodule.presenter

import android.text.TextUtils
import com.apkfuns.logutils.LogUtils
import com.future_education.teacher.loginmodule.R
import com.future_education.teacher.loginmodule.constant.LoginConstant
import com.future_education.teacher.loginmodule.contact.LoginContact
import com.future_education.teacher.loginmodule.location.LoginUrlLocation
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.application.CommonApplication
import com.futureeducation.commonmodule.constants.CommonConstants
import com.futureeducation.commonmodule.dialog.GraceDialog
import com.futureeducation.commonmodule.http.callback.SimpleCallback
import com.futureeducation.commonmodule.http.converter.RequestConverter
import com.futureeducation.commonmodule.manager.TeacherManager
import com.futureeducation.commonmodule.model.ResponseBean
import com.futureeducation.commonmodule.model.TeacherBean
import com.futureeducation.commonmodule.utill.SharedPreferencesUtil
import com.futureeducation.commonmodule.utill.ToastUtils
import com.yanzhenjie.kalle.Kalle
import com.yanzhenjie.kalle.simple.SimpleResponse
import com.yanzhenjie.kalle.simple.cache.CacheMode
import java.util.concurrent.TimeUnit


/**
 * Created by LoginPresenter.
 * User: ASUS
 * Date: 2019/11/19
 * Time: 16:07
 * 执行登录的p层
 *
 */
class LoginPresenter(var view: LoginContact.View) : LoginContact.Presenter, LoginContact {

    private var commonActivity: CommonActivity? = null

    init {
        view.setPresenter(this)

        this.commonActivity = view as CommonActivity
    }

    //执行登录
    private fun doLogin(account: String, passwrod: String) {
        var url = LoginUrlLocation.getLoginUrl()
        LogUtils.w("登录的地址：" + url)
        Kalle.post(url)
            .tag(this)
            .cacheMode(CacheMode.NETWORK_NO_THEN_READ_CACHE)
            .param("password", passwrod)
            .param("userName", account)
            .param("clientAppType", 4)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .converter(RequestConverter(commonActivity))
            .perform(object : SimpleCallback<ResponseBean<TeacherBean>>(commonActivity) {
                override fun onResponse(response: SimpleResponse<ResponseBean<TeacherBean>, String>?) {
                    var issucceed = response!!.isSucceed
                    if (issucceed) {
                        var resp = response.succeed()
                        var code = resp.statesCode
                        var token = resp.accessToken
                        when (code) {
                            CommonConstants.REQUES_SUCESS -> {
                                //登录成功
                                var bean = resp.data
                                var role = bean.userRole
                                if (TextUtils.equals(
                                        role,
                                        LoginConstant.GENERAL_TEACHER
                                    ) || TextUtils.equals(role, LoginConstant.CLASS_TEACHER)
                                ) {
                                    bean.token = token
                                    TeacherManager(commonActivity).setUser(bean)
                                    if (bean != null) {
                                        var config = Kalle.getConfig()
                                        var header = config.headers
                                        if (header.containsKey("Authorization")) {
                                            header.remove("Authorization")
                                        }

                                    }
                                    if (!TextUtils.isEmpty(token)) {
                                        var application =
                                            commonActivity!!.application as CommonApplication
                                        application.initialize(token)
                                    }
                                    view.shownLoginSucess(bean)
                                    var sp = SharedPreferencesUtil.getInstance(commonActivity)
                                    var teacherManager = TeacherManager(commonActivity)
                                    teacherManager.setUser(bean)
                                    sp.saveString(CommonConstants.USER_ACCOUNT, account)
                                    sp.saveString(CommonConstants.USER_PASS, passwrod)
                                } else {
                                    //登录的是其他角色
                                    var msg =
                                        String.format(
                                            commonActivity!!.getString(R.string.login_role_errors),
                                            role
                                        )
                                    var dialog = GraceDialog()
                                    dialog.setMassageText(msg)
                                    dialog.show(
                                        commonActivity!!.supportFragmentManager,
                                        "dialog"
                                    )
                                    view.logingFailure()
                                }
                            }
                            CommonConstants.REQUES_FAILURE -> {
                                //登录失败
                                var msg = resp.message
                                var dialog = GraceDialog()
                                LogUtils.e("看看msg：" + msg)
                                dialog.setMassageText(msg)
                                dialog.show(
                                    commonActivity!!.supportFragmentManager,
                                    "dialog"
                                )
                                view.logingFailure()
                            }
                            LoginConstant.DUE_ACCOUNT -> {
                                //账户已经过期

                                var dialog = GraceDialog()
                                var msg=commonActivity!!.getString(R.string.account_overdue)
                                dialog.setMassageText(msg)
                                dialog.show(commonActivity!!.supportFragmentManager, "dialog")
                                view.logingFailure()

                            }
                        }

                    } else {
                        var failure = response.failed()
                        ToastUtils.showCenterToast(commonActivity, R.string.login_failure)
                        view.logingFailure()
                    }
                }


            })


    }

    override fun ExecutiveLogging(keyword: String, account: String) {
        doLogin(account, keyword)

    }

//    suspend fun Test(): String? {
//        var url = LoginUrlLocation.getLoginUrl()
//        var result: String? =null
//        Kalle.post(url).tag(this).cacheMode(CacheMode.NETWORK_NO_THEN_READ_CACHE)
//            .param("password", "")
//            .converter(RequestConverter())
//            .perform(object : SimpleCallback<ResponseBean<TeacherBean>>(commonActivity) {
//                override fun onResponse(response: SimpleResponse<ResponseBean<TeacherBean>, String>?) {
//                    var issucceed = response!!.isSucceed
//                    if (issucceed) {
//                        result=response.toString()
//                    }
//                }
//            })
//
//
//}

    override fun start() {

        // 运行代码
//    GlobalScope.launch(Dispatchers.Main) {
//
//    }
    }
}