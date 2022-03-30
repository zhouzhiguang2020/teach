package com.future_education.loginmodule.presenter

import android.text.TextUtils
import com.apkfuns.logutils.LogUtils
import com.future_education.teacher.R
import com.future_education.teacher.constant.MainConstant
import com.future_education.teacher.contact.WXEntryContact
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.application.CommonApplication
import com.futureeducation.commonmodule.constants.CommonConstants
import com.futureeducation.commonmodule.http.callback.SimpleCallback
import com.futureeducation.commonmodule.http.converter.RequestConverter
import com.futureeducation.commonmodule.location.BaseurlLocation
import com.futureeducation.commonmodule.manager.TeacherManager
import com.futureeducation.commonmodule.model.*
import com.futureeducation.commonmodule.utill.SharedPreferencesUtil
import com.futureeducation.commonmodule.utill.ToastUtils
import com.yanzhenjie.kalle.Kalle
import com.yanzhenjie.kalle.simple.SimpleResponse
import com.yanzhenjie.kalle.simple.cache.CacheMode


/**
 * 微信
 *
 */
class WXEntryPresenter(var view: WXEntryContact.View) : WXEntryContact.Presenter, WXEntryContact {

    private var commonActivity: CommonActivity? = null

    init {
        view.setPresenter(this)
        this.commonActivity = view as CommonActivity
    }


    override fun putWX(code: String) {
        var url = "https://api.weixin.qq.com/sns/oauth2/access_token"
        LogUtils.e("登录的地址：" + url)
        Kalle.get(url)
            .tag(this)
            .cacheMode(CacheMode.NETWORK_NO_THEN_READ_CACHE)
            .param("appid", CommonConstants.APP_ID)
            .param("secret", CommonConstants.AppSecret)
            .param("code", code)
            .param("grant_type", "authorization_code")
            .converter(RequestConverter())
            .perform(object : SimpleCallback<WXEntryBean>(commonActivity) {
                override fun onResponse(response: SimpleResponse<WXEntryBean, String>?) {
                    var issucceed = response!!.isSucceed
                    if (issucceed) {
                        var resp = response.succeed()
                        var unionid = resp.unionid
                        isBind(unionid)
                    }
                }
            })
    }

    /**
     * 获取token
     */
    fun isBind(unionid: String) {
        var url = BaseurlLocation.getBASEURL() + "/Api/WeChatAuth/AuthorizeRedirectForAndroid"
        LogUtils.e("微信登录的地址：" + url)
        LogUtils.e("微信登录：openid" + unionid)
        Kalle.post(url)
            .tag(this)
            .cacheMode(CacheMode.NETWORK_NO_THEN_READ_CACHE)
            .param("wechatUniqueid", unionid)
            .converter(RequestConverter())
            .perform(object : SimpleCallback<ResponseBean<WXTokenBean>>(commonActivity) {
                override fun onResponse(response: SimpleResponse<ResponseBean<WXTokenBean>, String>?) {
                    var issucceed = response!!.isSucceed
                    if (issucceed) {
                        var resp = response.succeed()
                        var code = resp.statesCode
                        when (code) {
                            CommonConstants.REQUES_SUCESS -> {
                                if (resp.data != null) {
                                    var config = Kalle.getConfig()
                                    var header = config.headers
                                    if (header.containsKey("Authorization")) {
                                        header.remove("Authorization")
                                    }
                                    var application = commonActivity!!.application as CommonApplication
                                    application.initialize(resp.data.userToken)
                                    //已经绑定了
                                    gettokenuser(resp.data.userToken)
                                }
                            }
                            CommonConstants.REQUES_FAILURE -> {
                                //还未绑定
                                view.BindFailure(unionid)
                            }
                        }
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
                            MainConstant.DUE_ACCOUNT->{
                                //登录账户已经过期了
                                view.AccountExpired()

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
        LogUtils.e("获取用户信息：" + url)
        LogUtils.e("获取用户信息：" + userId)
        LogUtils.e("获取用户信息：" + token)
        Kalle.post(url)
            .tag(this)
            .cacheMode(CacheMode.NETWORK_NO_THEN_READ_CACHE)
            .addHeader("Authorization", "Bearer " + token)
            .param("userId", userId)
            .converter(RequestConverter())
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
                                if (TextUtils.equals(role, "GeneralTeacher") || TextUtils.equals(role, "ClassTeacher")) {
                                    bean.token = token
                                    TeacherManager(commonActivity).setUser(bean)
                                    if (bean != null) {
                                        var config = Kalle.getConfig()
                                        var header = config.headers
                                        if (header.containsKey("Authorization")) {
                                            header.remove("Authorization")
                                        }
                                        var application = commonActivity!!.application as CommonApplication
                                        application.initialize(token)
                                    }
                                    view.getUserSucess(bean)
                                    var sp = SharedPreferencesUtil.getInstance(commonActivity)
                                    var teacherManager = TeacherManager(commonActivity)
                                    teacherManager.setUser(bean)
                                } else {
                                    //登录的是其他角色
                                    var msg = String.format(commonActivity!!.getString(R.string.login_role_errors), role)
                                    ToastUtils.showCenterToast(commonActivity, "抱歉，当前仅限教师登录!")
//                                    var dialog = GraceDialog()
//                                    dialog.setMassageText(msg)
//                                    dialog.show(commonActivity!!.supportFragmentManager, "dialog")
                                    view.getUserFailure()
                                }
                            }
                            CommonConstants.REQUES_FAILURE -> {
                                //登录失败
                                var msg = resp.message
//                                var dialog = GraceDialog()
//                                LogUtils.e("看看msg：" + msg)
//                                dialog.setMassageText(msg)
//                                dialog.show(commonActivity!!.supportFragmentManager, "dialog")
                                ToastUtils.showCenterToast(commonActivity, msg)
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