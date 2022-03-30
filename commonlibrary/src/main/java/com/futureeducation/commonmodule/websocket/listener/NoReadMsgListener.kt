package com.futureeducation.commonmodule.websocket.listener

import com.futureeducation.commonmodule.websocket.bean.MessageInfo

/**
 * 未读消息接口
 */
interface NoReadMsgListener {
    fun onReadCount(noReadCount: Int,index:Int,complete:Boolean)
}
