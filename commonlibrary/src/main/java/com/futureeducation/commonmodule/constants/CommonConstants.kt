package com.futureeducation.commonmodule.constants

/**
 * Created by CommonConstants.
 * User: ASUS
 * Date: 2019/11/12
 * Time: 13:39
 *
 * 全局常量类
 */
class CommonConstants {

    companion object {

        /***********    微信appid      ****************/
        const val APP_ID = "wxd1900f92868900bb"
        const val AppSecret = "900a0bae02092fef7111e39ae457eb7d"
        /***********    微信appid      ****************/

        var USER_ID: String = "user_id"
        var USER_TOKEN: String = "user_token"
        var USER_ACCOUNT: String = "user_account"
        var USER_PASS: String = "user_pass"
        var USER_NAME: String = "user_name"
        var PER_PIC: String = "per_pic"
        var UNIT_SESSION_UID: String = "unit_session_uid"
        var SESSION_WEEK: String = "session_week"
        var SERVICEAPI: String = "service_api"
        var SERVICEAPILIST: String = "service_api_list"
        var LOGIN_INVALID: String = "login_invalid"

        //uni_id 学校ID
        var USER_UNOID = "uni_id"
        val REQUEST_CODE_SETTING: Int = 100;

        //second
        val REQUEST_CODE_SECOND = 200

        //requesucess
        val REQUES_SUCESS = 1001

        //Failure
        val REQUES_FAILURE = 1002

        //全局调试标志
        var ISDUG = true//调试的全局状态
        val PREFERENCE_NAME = "teacher"//账户信息
        var FLAG_READ = "flag_read"//是否阅读隐私政策
        var FIRST_LOGIN = "first_Login"//第一次登录

        //视频播放地址video display
        val VIDEO_DISPLAY_URL = "video_display_url"

        //视频video测试地址
        var VIDEO_TEST_URL =
            "http://gzwk.oss-cn-shenzhen.aliyuncs.com/weike/13385503031/20191029/2CF4CE0C-D92F-74C9-CBC9-172B4938006D.mp4"
        var LOGINFAILURE = 500 //登录失败5次需要等会才能登录(去除登录失败过多提示)
        var LOGINFAILURETIME = 3 * 60 //需要停3分钟
        var LOGINFAUILURESP = "login_failure_sp"

        //连接跟新跳转连接
        val LINK_UPDATE = "linkupdate"

        //计算纸生成地址 calculation paper
        val CALCULATION_PAPER_PATH = "calculation_paper_image_path"
        var CALCULATION_PAPER_COMPILE = "calculation_paper_compile"

        //targer 传过去fragment唯一标识
        var CALCULATION_PAPER_TARGER = "calculation_paper_targer"

        //按钮是不是隐藏
        val CALCULATION_ISSHOW = "calculationisshow"

        //计算纸结果页面
        val CALCULATION_PAPER_LOCATION_PATH = "calculation_paper_location_path"

        //计算纸的fragment
        val CALCULATION_PAPER_FRAGMENT = "calculation_paper_fragment"

        //talk 类型
        val CHAT_TALK = "talk"
        //fragment_position
        val FRAGMENT_POSITION = "fragment_position"

    }
}