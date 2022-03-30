package com.future_education.loginmodule.presenter

import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.model.TeacherBean


/**
 * Created by IPresenter.
 * User: Administrator
 * Date: 2019/11/7
 * Time: 11:46
 * 登录p层返回登录成功后的user对象
 */
interface IPresenter {
    //初始化操作
    fun InitPresenter(commonActivity: CommonActivity)

    fun doLogin(account: String, passwrod: String): TeacherBean?


}