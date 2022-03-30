package com.future_education.loginmodule.presenter

import android.text.TextUtils
import com.apkfuns.logutils.LogUtils
import com.future_education.teacher.loginmodule.R
import com.future_education.teacher.loginmodule.constant.LoginConstant
import com.future_education.teacher.loginmodule.contact.BindContact
import com.future_education.teacher.loginmodule.location.LoginUrlLocation
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.application.CommonApplication
import com.futureeducation.commonmodule.constants.CommonConstants
import com.futureeducation.commonmodule.dialog.GraceDialog
import com.futureeducation.commonmodule.http.callback.SimpleCallback
import com.futureeducation.commonmodule.http.converter.RequestConverter
import com.futureeducation.commonmodule.location.BaseurlLocation
import com.futureeducation.commonmodule.manager.TeacherManager
import com.futureeducation.commonmodule.model.ResponseBean
import com.futureeducation.commonmodule.model.TeacherBean
import com.futureeducation.commonmodule.model.WXTokenBean
import com.futureeducation.commonmodule.model.WXTokenUserBean
import com.futureeducation.commonmodule.utill.SharedPreferencesUtil
import com.futureeducation.commonmodule.utill.ToastUtils
import com.yanzhenjie.kalle.Kalle
import com.yanzhenjie.kalle.simple.SimpleResponse
import com.yanzhenjie.kalle.simple.cache.CacheMode
import java.util.concurrent.TimeUnit


/**
 * Time: 16:07
 * 执行绑定的p层
 *
 */
class BindPresenter(var view: BindContact.View) : BindContact.Presenter, BindContact {

    private var commonActivity: CommonActivity? = null

    init {
        view.setPresenter(this)
        this.commonActivity = view as CommonActivity
    }

    //微信登录帐号绑定
    override fun putBind(account: String, passwrod: String, unionid: String) {
        var url = LoginUrlLocation.getBindUrl()
        LogUtils.d("微信登录绑定地址：" + url)
        Kalle.post(url)
            .tag(this) //
            .cacheMode(CacheMode.NETWORK_NO_THEN_READ_CACHE)
            .param("userName", account)
            .param("password", passwrod)
            .param("wechatUniqueid", unionid)
            .param("clientAppType", 4)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .converter(RequestConverter())
            .perform(object : SimpleCallback<ResponseBean<WXTokenBean>>(commonActivity) {
                override fun onResponse(response: SimpleResponse<ResponseBean<WXTokenBean>, String>?) {
                    var issucceed = response!!.isSucceed
                    if (issucceed) {
                        var resp = response.succeed()
                        var code = resp.statesCode
                        if (code == CommonConstants.REQUES_SUCESS) {
                            //绑定成功
                            if (resp.data != null) {
                                var config = Kalle.getConfig()
                                var header = config.headers
                                if (header.containsKey("Authorization")) {
                                    header.remove("Authorization")
                                }
                                var application = commonActivity!!.application as CommonApplication
                                application.initialize(resp.data.userToken)
                                gettokenuser(resp.data.userToken)
                            }
                        } else {
                            //绑定失败
                            //ToastUtils.showCenterToast(commonActivity, resp.message)
                            LogUtils.e("看看错误信息：" + resp.message)
                            //view.BindFailure(resp.message)
                            var bean = TeacherBean()
                            view.getUserSucess(bean)
                        }

                    } else {
                        view.RebindingAccount()
                    }
                }
            })
    }


    fun gettokenuser(token: String) {
        var url = BaseurlLocation.getBASEURL() + "/Api/WeChatAuth/GetUserInfoByToken"
        LogUtils.d("获取用户信息：" + url)
        Kalle.post(url)
            .tag(this)
            .cacheMode(CacheMode.NETWORK_NO_THEN_READ_CACHE)
            .param("token", token)
            .converter(RequestConverter())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .perform(object : SimpleCallback<ResponseBean<WXTokenUserBean>>(commonActivity) {
                override fun onResponse(response: SimpleResponse<ResponseBean<WXTokenUserBean>, String>?) {
                    var issucceed = response!!.isSucceed
                    if (issucceed) {
                        var resp = response.succeed()
                        var code = resp.statesCode
                        when (code) {
                            CommonConstants.REQUES_SUCESS -> {
                                if (resp.data != null) {
                                    getUser(resp.data.per_id, token)
                                }

                            }
                            CommonConstants.REQUES_FAILURE -> {
                                ToastUtils.showCenterToast(commonActivity, resp.message)
                            }
                            LoginConstant.DUE_ACCOUNT -> {
                                //登录账号已经过期
                                var dialog = GraceDialog()
                                var msg = commonActivity!!.getString(R.string.account_overdue)
                                dialog.setMassageText(msg)
                                dialog.show(commonActivity!!.supportFragmentManager, "dialog")

                            }
                        }
                    }
                }
            })
    }


    /**
     * 获取用户信息
     */
    fun getUser(userId: String, token: String) {
        var url = BaseurlLocation.getBASEURL() + "/Api/Login/GetUserInfo"
        LogUtils.d("微信登录绑定地址：" + url)
        Kalle.post(url)
            .tag(this)
            .cacheMode(CacheMode.NETWORK_NO_THEN_READ_CACHE)
            .addHeader("Authorization", "Bearer " + token)
            .param("userId", userId)
            .converter(RequestConverter())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .perform(object : SimpleCallback<ResponseBean<TeacherBean>>(commonActivity) {
                override fun onResponse(response: SimpleResponse<ResponseBean<TeacherBean>, String>?) {
                    var issucceed = response!!.isSucceed
                    if (issucceed) {
                        var resp = response.succeed()
                        var code = resp.statesCode
                        when (code) {
                            CommonConstants.REQUES_SUCESS -> {
                                //登录成功
                                var bean = resp.data
                                var role = bean.userRole
                                if (TextUtils.equals(role, "GeneralTeacher") || TextUtils.equals(
                                        role,
                                        "ClassTeacher"
                                    )
                                ) {
                                    bean.token = token
                                    TeacherManager(commonActivity).setUser(bean)
                                    if (bean != null) {
                                        var config = Kalle.getConfig()
                                        var header = config.headers
                                        if (header.containsKey("Authorization")) {
                                            header.remove("Authorization")
                                        }
                                        var application =
                                            commonActivity!!.application as CommonApplication
                                        application.initialize(token)
                                    }
                                    view.getUserSucess(bean)
                                    var sp = SharedPreferencesUtil.getInstance(commonActivity)
                                    var teacherManager = TeacherManager(commonActivity)
                                    teacherManager.setUser(bean)
                                } else {
                                    //登录的是其他角色
                                    var msg = String.format(
                                        commonActivity!!.getString(R.string.login_role_errors),
                                        role
                                    )
                                    var dialog = GraceDialog()
                                    dialog.setMassageText("抱歉，当前仅限教师登录!")
                                    dialog.show(commonActivity!!.supportFragmentManager, "dialog")
                                    view.getUserFailure()
                                }
                            }
                            CommonConstants.REQUES_FAILURE -> {
                                //登录失败
                                var msg = resp.message
                                var dialog = GraceDialog()
                                LogUtils.e("看看msg：" + msg)
                                dialog.setMassageText(msg)
                                dialog.show(commonActivity!!.supportFragmentManager, "dialog")
                                view.getUserFailure()
                            }
                        }

                    } else {
                        view.getUserFailure()
                    }
                }
            })
    }


    override fun start() {

    }
}