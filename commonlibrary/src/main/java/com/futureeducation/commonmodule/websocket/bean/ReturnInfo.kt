package com.futureeducation.commonmodule.websocket.bean

import androidx.annotation.Keep


@Keep
class ReturnInfo {

    //消息码，0：成功 -1：失败
    var code: Int = 0
    var key: String? = null
    var msg: String? = null
    var id: String? = null
    var functionKey: String? = null
    var fromConnectionId: String? = null
    var clientSerializationType: String? = null
    var fromId: String? = null
    var content: String? = null
    var replyContent: String? = null
    //描述
    var desc: String? = null
    var expand: String? = null

    var serialNo:Int = 0

    companion object {
        val SUCCESS_CODE = 0
        val DEFAULT_FAILURE_CODE = -1
    }

    override fun toString(): String {
        return "ReturnInfo(code=$code,serialNo=$serialNo key=$key,functionKey=$functionKey,fromConnectionId=$fromConnectionId,clientSerializationType=$clientSerializationType,fromId=$fromId,content=$content,replyContent=$replyContent, msg=$msg,id=$id, desc=$desc, expand=$expand)"
    }

}
