package com.futureeducation.commonmodule.websocket.listener

import com.futureeducation.commonmodule.websocket.bean.MessageInfo
import com.futureeducation.commonmodule.websocket.bean.ReturnInfo


interface SignalRListener {
    fun onReceiveMessage(messageInfo: MessageInfo)
    fun onReceiveReturnMessage(returnInfo: ReturnInfo)
}
