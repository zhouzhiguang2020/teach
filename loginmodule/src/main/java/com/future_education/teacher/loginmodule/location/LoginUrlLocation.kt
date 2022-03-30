package com.future_education.teacher.loginmodule.location

import com.futureeducation.commonmodule.location.BaseurlLocation

/**
 * Created by LoginUrlLocation.
 * User: ASUS
 * Date: 2019/12/25
 * Time: 19:57
 */
class LoginUrlLocation : BaseurlLocation() {
    companion object {
        fun getLoginUrl(): String {
            var builder = StringBuffer()
            builder.append(getBASEURL())
            builder.append("/Api/Login")
            return builder.toString()
        }

        /**
         * 微信登录绑定
         */
        fun getBindUrl(): String {
            var builder = StringBuffer()
            builder.append(getBASEURL())
            builder.append("/Api/WeChatAuth/WeChatLoginForAndroid")
            return builder.toString()
        }

        /**
         * 获取用户信息
         */
        fun GetUserInfoByTokenUrl(): String {
            var builder = StringBuffer()
            builder.append(getBASEURL())
            builder.append("/Api/WeChatAuth/GetUserInfoByToken")
            return builder.toString()
        }
    }
}