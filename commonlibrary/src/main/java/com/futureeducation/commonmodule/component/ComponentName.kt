package com.futureeducation.commonmodule.component


//模块化常量类
public class CompocomponentName {
    /**
     *
     *@author Administrator
     *@date   2019/5/4 14:33
     *@describe 模块化常量类配置
     */
    companion object {
        //登录模块的常量类
        val LOGIN_COMPINENR = "LoginComponent"
        val LOGIN: String? = "login"

        //绑定帐号
        val Bind: String? = "bind"

        //token已经过期
        val LOGIN_OVERDE: String = "login_overd"

        //account has expired.
        var ACCOUNT_EXPIRED = "account_expired"

        //log out 退出登录
        val LOGIN_OUT: String = "login_out"

        //主的模块组件化类
        val MAIN_COMPINENT = "mainComponent"
        val MAIN = "main"

        //主模块的登录
        val MAIN_LOGIN = "main_login"

        //学生模块
        val STUDENT_COMPONENT = "StudentComponent"
        val STUDENT_MAIN = "student_main"

        //微课模块 ShortClassroomComponent
        val SHORT_CLASSROOM_COMPONENT = "ShortClassroomComponent"
        val SHORT_CLASSROOM_MAIN = "shortclassroommain"

        //计算草稿纸 calculationpaper
        val CALCULATION_PAPER_COMPONENT = "calculationpaperComponent"

        //计算纸主类
        val CALCULATION_PAPER_MAIN = "calculationpaperMain"

        //Im模块
        val IM_COMPINENR = "ImComponent"
        val IM: String? = "im"

        //第一次登陆初始化远程消息first
        val IM_FIRST_LOGIN = "im_first_log"

        //初始化im
        val IM_INIT = "im_init"

        //talk
        val IM_TALK = "im_talk"

        //classroom interaction 课堂互动聊天
        val IM_CLASSROOM_INTERACTION = "im_classroom_interaction"

        //关闭im Unlink
        val IM_UNLINK = "im_unlink"

        //作业模块 homework Fragment
        val HOMEWORK_COMPINENR = "homework"
        val HOMEWORK_FRAGMENT = "homeworkfragment"

        //学习情况模块 Learning condition
        val LEARNINGCONDITION_COMPINENR = "learningcondition"
        val LEARNINGCONDITION_FRAGMENT = "learningconditionfragment"

        //助手模块 assistant
        var ASSISTANT_COMPINENR = "assistant"
        var ASSISTANT_FRAGMENT = "assistantfragment"

        //我的模块person
        var PERSON_COMPINENR = "person"
        var PERSON_FRAGMENT = "personfragment"
        //打开用户须知
        var OPEN_USER_AGREEMENT="open_UserAgreement"
        //打开隐私政策 PrivacyPolicyActivity
        var OPEN_PRIVACY_POLICY="open_PrivacyPolicyActivity"
    }
}