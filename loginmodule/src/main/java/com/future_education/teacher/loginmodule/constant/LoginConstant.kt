package com.future_education.teacher.loginmodule.constant

import com.tencent.mm.opensdk.openapi.IWXAPI


/**
 * Created by LoginConstant.
 * User: ASUS
 * Date: 2019/12/24
 * Time: 9:10
 */
class LoginConstant {
    companion object {
        /***********    微信appid      ****************/
        const val APP_ID = "wxd1900f92868900bb"


        //ogin is invalid
        val LOGIN_INVALID = "login_overd"

        //log out
        val LOGIN_TYPE = "login_type"
        val LOGIN_OUT_TYPE = 1//退出登录类型

        //Login date 登录过期类型
//        val LOGIN_DATE = "login_date"
        val LOGIN_DATE_TYPE = 2//登录过期类型

        //学生角色 Student role
        val STUDENT_ROLE = "Student"

        //GeneralTeacher	一般教师(科目老师)
        //ClassTeacher	班主任
        var GENERAL_TEACHER = "GeneralTeacher"
        var CLASS_TEACHER = "ClassTeacher"

        //全局Due account 账户已经过期
        var DUE_ACCOUNT = 1013
        //InputFilter.LengthFilter 聊天最长文字数量 account
        var ACCOUNT_INPUT_LENGTH = 30
    }

}