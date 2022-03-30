package com.futureeducation.commonmodule.websocket

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Handler
import android.os.UserManager
import android.util.Log
import androidx.annotation.Keep
import androidx.core.app.NotificationCompat
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import com.apkfuns.logutils.LogUtils
import com.futureeducation.commonmodule.R
import com.futureeducation.commonmodule.activities.CommonActivity
import com.futureeducation.commonmodule.application.CommonApplication
import com.futureeducation.commonmodule.manager.TeacherManager


import com.futureeducation.commonmodule.utill.SharedPreferencesUtil
import com.futureeducation.commonmodule.utill.TimeUtil
import com.futureeducation.commonmodule.websocket.bean.MessageInfo
import com.futureeducation.commonmodule.websocket.bean.ReturnInfo
import com.futureeducation.commonmodule.websocket.listener.NoReadMsgListener
import com.futureeducation.commonmodule.websocket.listener.SignalRListener
import com.microsoft.signalr.Action1
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import io.reactivex.Single
import java.lang.Exception
import java.util.*

/**
 *
 * @ProjectName:
 * @Package:
 * @ClassName:
 * @Description:
 * @Author:         作者名
 * @CreateDate:     2020/6/30 11:31
 * @UpdateUser:     更新者：
 * @UpdateDate:     2020/6/30 11:31
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
@Keep
class SignalRUtils {
    private var hubConnection: HubConnection? = null
    private var isLogger = true
    private var isClone = false
    public var smessageInfo: MessageInfo? = null
    private var signalRListenerTalk: SignalRListener? = null
    public var noReadMsgListenerImpl = NoReadMsgListenerImpl()
    private var noReadMsgListener: NoReadMsgListener? = null
    private var msgList = ArrayList<MessageInfo>()
    private var isWaitMsg = false //是否正在处理等待消息
    var mNotificationManager = CommonApplication.getInstance()!!
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;

    //适配8.0
    val CHANNEL_ID = "teacherTalk"
    var mBuilder: NotificationCompat.Builder? = null

    /**
     * * 在调用init()之后使用
     *
     * @return
     */
    val isConnected: Boolean
        get() = if (hubConnection != null) {
            hubConnection!!.connectionState == HubConnectionState.CONNECTED
        } else {
            false
        }

    fun initTalkListener(signalRListener: SignalRListener?) {
        signalRListenerTalk = signalRListener
    }

    //消息监听
    class NoReadMsgListenerImpl : NoReadMsgListener {

        override fun onReadCount(noReadCount: Int, index: Int, complete: Boolean) {
            LogUtils.e("$noReadCount-----------------------$index")

            if (SignalRUtils.instance!!.noReadMsgListener != null) {
                LogUtils.e("$noReadCount-----------------------$index")
                SignalRUtils.instance!!.noReadMsgListener!!.onReadCount(
                    noReadCount,
                    index,
                    complete
                )
            }
        }
    }

    fun getNoReadMsgCount(listener: NoReadMsgListener?) {
        noReadMsgListener = listener
    }

    fun initNotifi(context: Context, cls: CommonActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "聊天信息",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(context, cls::class.java)
        val pi: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Talk")
            .setContentText("聊天内容")
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pi)//跳转
            .setAutoCancel(true)//点击取消
            .setDefaults(NotificationCompat.DEFAULT_SOUND)//声音
            .setDefaults(NotificationCompat.DEFAULT_VIBRATE)//震动
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
    }

    /**
     * @param url             服务器地址
     * @param token
     * @param signalRListener
     */
    fun init(url: String, token: String, signalRListener: SignalRListener?) {
        if (isConnected) {
            //已经初始化了


        } else {
            LogUtils.e("初始化url地址：" + url)
            hubConnection = HubConnectionBuilder.create(url)
                .withAccessTokenProvider(Single.defer { Single.just(token) })
                .withHandshakeResponseTimeout(3 * 60000)
                .build()

        }
        isClone = false

        //在客户端上定义中心可调用的方法。 在生成之后但在开始连接之前定义方法。
        //ReceiveMessage返回的是对象，Android处理不了，不加这个会有异常log
        hubConnection!!.on("ReceiveMessage", { message ->
            if (isLogger) {
                LogUtils.e(TAG, "ReceiveMessage:$message")
            }
        }, MessageInfo::class.java)

        hubConnection!!.on(
            "ReceiveMessageJsonString",
            { message ->
                if (isLogger) {
                    LogUtils.e(TAG, "ReceiveMessageJsonString:$message")
                }
                LogUtils.d("接收消息----------------$message")
                var json = JSON.parseObject(message, MessageInfo::class.java)

                //LogUtils.e("远端接收接收message情况" + signalRListenerTalk == null)
                if (json.functionKey == "talk") {
                    sendConfirmMessageJsonString(message)
                    if (signalRListenerTalk != null) {
                        if (!CommonApplication.LoadingUnreadMsg) {
                            signalRListenerTalk!!.onReceiveMessage(json)
                            if (CommonApplication.isNotification) {
                                if (mBuilder != null) {
                                    mBuilder!!.setContentText(json.fromName + ":" + json.content);
                                    if (json.formatType == 2) {
                                        mBuilder!!.setContentText(json.fromName + ":[图片]");
                                    } else if (json.formatType == 3) {
                                        mBuilder!!.setContentText(json.fromName + ":[视频]");
                                    } else if (json.formatType == 4) {
                                        mBuilder!!.setContentText(json.fromName + ":[语音]");
                                    }
                                    mBuilder!!.setContentTitle("" + json.targetName)
                                    mNotificationManager.notify(1, mBuilder!!.build())
                                }
                            }
                        } else {
                            LogUtils.e("------------------正在获取离线消息--------------------")
                            msgList.add(json)
                            if (!isWaitMsg) {
                                waitMsg()
                            }
                        }
                    }
                }
                signalRListener?.onReceiveMessage(json)

            },
            String::class.java
        )

        //异常时返回的消息
        hubConnection!!.on("ReceiveReturnMessageJsonString", { returnInfo ->
            if (isLogger) {
                LogUtils.e(TAG, "ReceiveReturnMessageJsonString:$returnInfo")
            }
            signalRListener?.onReceiveReturnMessage(
                JSON.parseObject(
                    returnInfo,
                    ReturnInfo::class.java
                )
            )

            LogUtils.e("服务器返回：" + signalRListenerTalk == null)
            if (signalRListenerTalk != null) {
                signalRListenerTalk?.onReceiveReturnMessage(
                    JSON.parseObject(
                        returnInfo,
                        ReturnInfo::class.java
                    )
                )
            }
        }, String::class.java)

        hubConnection!!.on("ReceiveHeartbeat", { returnInfo ->
            //接收服务心跳
            LogUtils.d("接收心跳ReceiveHeartbeat:$returnInfo")
        }, String::class.java)


        //This is a blocking call 阻塞试
        try {
            //开始连接并等待连接成功
            hubConnection!!.start().blockingAwait()
            LogUtils.e(hubConnection!!.connectionState.toString() + "")
        } catch (e: Exception) {
            if (e.message != null) {
                if (e.message.toString().indexOf("401") != -1) {
                    CommonApplication.getInstance()!!.LoginOverdue(2)
                }
            }
        }

        hubConnection!!.onClosed { exception ->
            try {
                if (exception != null) {
                    LogUtils.e(TAG, "invoke: " + exception.message)
                }
                if (!isClone) {
                    //断开自动重连
                    hubConnection!!.start()
                }
            } catch (e: Exception) {

            }

        }
    }

    /**
     * 处理等待消息
     */
    fun waitMsg() {
        isWaitMsg = true
        Handler().postDelayed(Runnable {
            if (!CommonApplication.LoadingUnreadMsg) {
                for (item in msgList) {
                    LogUtils.e("处理等待消息===$item")
                    if (signalRListenerTalk != null) {
                        signalRListenerTalk!!.onReceiveMessage(item)
                    }
                }
                msgList.clear()
            } else {
                waitMsg()
            }
        }, 1000)
    }

    /**
     * 是否打印log
     *
     * @param isLogger
     */
    fun isLogger(isLogger: Boolean) {
        this.isLogger = isLogger
    }

    /**
     * 在调用init()之后使用
     * @param keepAliveIntervalInMilliseconds The interval (specified in milliseconds) at which the connection should send keep alive messages.
     * @param serverTimeoutInMilliseconds     The server timeout duration (specified in milliseconds)
     */
    fun setConnection(keepAliveIntervalInMilliseconds: Long, serverTimeoutInMilliseconds: Long) {
        if (hubConnection != null) {
            hubConnection!!.keepAliveInterval = keepAliveIntervalInMilliseconds
            hubConnection!!.serverTimeout = serverTimeoutInMilliseconds
        }
    }

    fun send(message: String) {
        LogUtils.e("发送的数据：" + message)
        try {
            if (hubConnection != null) {
                if (hubConnection!!.connectionState == HubConnectionState.CONNECTED) {
                    hubConnection!!.send("SendMessageJsonString", message)
                    LogUtils.e("发送了哦")
                } else {
                    hubConnection!!.start()
                }
            }
        } catch (e: Exception) {
            LogUtils.e("im服务器报错了：" + e.message)
        }

    }

    fun sendHeartbeat(message: String) {
        LogUtils.d("发送心跳")
        try {
            if (hubConnection != null) {
                if (hubConnection!!.connectionState == HubConnectionState.CONNECTED) {
                    hubConnection!!.send("SendHeartbeat", message)
                } else {
                    hubConnection!!.start()
                }
            }
        } catch (e: Exception) {
            LogUtils.e("im服务器报错了：" + e.message)
        }

    }

    /**
     * 收到消息回馈
     */
    fun sendConfirmMessageJsonString(message: String) {
        LogUtils.e("收到消息回馈$message")
        if (hubConnection != null) {
            if (hubConnection!!.connectionState == HubConnectionState.CONNECTED) {
                hubConnection!!.send("ConfirmMessageJsonString", message)
            } else {
                hubConnection!!.start()
            }
        }
    }

    /**
     * * 在调用init()之后使用
     */
    fun restart() {
        if (hubConnection != null) {
            hubConnection!!.start()
        }
    }

    /**
     * * 在调用init()之后使用
     */
    fun close() {
        if (hubConnection != null) {
            isClone = true
            hubConnection!!.stop()
        }
    }

    companion object {
        private val TAG = "SignalRUtils"
        private var signalRUtils: SignalRUtils? = null
        val instance: SignalRUtils?
            get() {
                if (signalRUtils == null) {
                    synchronized(SignalRUtils::class.java) {
                        if (signalRUtils == null) {
                            signalRUtils = SignalRUtils()
                        }
                    }
                }
                return signalRUtils
            }
    }

    //学生答题
    fun submitListString(list: MutableList<String>, context: Context) {
        var fromId: String? = SharedPreferencesUtil.getInstance(context).getString("FROM_ID", "")
        var recordId: String =
            SharedPreferencesUtil.getInstance(context).getString("CLASS_RECORD_ID", "")
        val groupMessageInfo = MessageInfo()
        groupMessageInfo.id = UUID.randomUUID().toString()
        groupMessageInfo.key = MessageInfo.MessageKey.CHAT_KEY.value()
        groupMessageInfo.formatType = MessageInfo.ContentFormatType.OTHER.value()
        groupMessageInfo.functionKey = "studentansrecord"
        groupMessageInfo.fromId = ""
        groupMessageInfo.targetType = MessageInfo.MessageTargetType.GROUP.value()
        groupMessageInfo.targetUserIds = arrayOf<String>(fromId!!)
        groupMessageInfo.content = JSON.toJSONString(list)
        groupMessageInfo.targetId = recordId
        send(JSON.toJSONString(groupMessageInfo))
        Log.e("lzh", "submit" + JSON.toJSONString(groupMessageInfo))
    }


    fun submitDetails(functionKey: String, content: String, context: Context) {
        val strInfo: String? =
            SharedPreferencesUtil.getInstance(context).getString("ROLL_CALL_DATA", "")
        Log.e("lzh", strInfo + "submitDetails-->")
        val str: List<String>? = strInfo?.split(",")
        val targetId: String = str?.get(0).toString()
        val fromId: String = str?.get(1).toString()
        Log.e("lzh", targetId + "targetId")
        val groupMessageInfo = MessageInfo()
        groupMessageInfo.id = UUID.randomUUID().toString()
        groupMessageInfo.key = MessageInfo.MessageKey.CHAT_KEY.value()
        groupMessageInfo.formatType = MessageInfo.ContentFormatType.TEXT.value()
        groupMessageInfo.functionKey = functionKey
        groupMessageInfo.fromId = ""
        groupMessageInfo.targetType = MessageInfo.MessageTargetType.GROUP.value()
        groupMessageInfo.targetUserIds = arrayOf<String>(fromId)
        groupMessageInfo.content = content
        groupMessageInfo.targetId = targetId
        instance?.send(JSON.toJSONString(groupMessageInfo))
        Log.e("lzh", "submit" + JSON.toJSONString(groupMessageInfo))

    }

    fun submitContent(messageInfo: MessageInfo, dialog: DialogInterface) {
        val groupMessageInfo = MessageInfo()
        groupMessageInfo.id = UUID.randomUUID().toString()
        groupMessageInfo.key = MessageInfo.MessageKey.CHAT_KEY.value()
        groupMessageInfo.formatType = MessageInfo.ContentFormatType.TEXT.value()
        groupMessageInfo.targetUserIds = arrayOf<String>(messageInfo.fromId.toString())
        groupMessageInfo.functionKey = "rollcall"
        groupMessageInfo.targetType = MessageInfo.MessageTargetType.GROUP.value()
        groupMessageInfo.targetId = messageInfo.targetId
        groupMessageInfo.content = messageInfo.content
        send(JSON.toJSONString(groupMessageInfo))
        dialog.dismiss()
        Log.e("lzh", "submit" + JSON.toJSONString(groupMessageInfo))
    }

    fun submitContent() {
        if (smessageInfo != null) {
            val groupMessageInfo = MessageInfo()
            groupMessageInfo.id = UUID.randomUUID().toString()
            groupMessageInfo.key = MessageInfo.MessageKey.CHAT_KEY.value()
            groupMessageInfo.formatType = MessageInfo.ContentFormatType.TEXT.value()
            groupMessageInfo.targetUserIds = arrayOf<String>(smessageInfo!!.fromId.toString())
            groupMessageInfo.functionKey = "rollcall"
            groupMessageInfo.targetType = MessageInfo.MessageTargetType.GROUP.value()
            groupMessageInfo.targetId = smessageInfo!!.targetId
            groupMessageInfo.content = smessageInfo!!.content
            send(JSON.toJSONString(groupMessageInfo))
            Log.e("lzh", "submit" + JSON.toJSONString(groupMessageInfo))
        }
    }

    /**
     * fromId:发送id
     * targetId:接收id
     * content:课程id
     */
    fun submitContent2(fromId: String, targetId: String, content: String, teacherName: String) {
        val groupMessageInfo = MessageInfo()
        groupMessageInfo.id = UUID.randomUUID().toString()
        groupMessageInfo.key = MessageInfo.MessageKey.CHAT_KEY.value()
        groupMessageInfo.formatType = MessageInfo.ContentFormatType.TEXT.value()
        groupMessageInfo.functionKey = "studentrollcall"
        groupMessageInfo.targetType = MessageInfo.MessageTargetType.USER.value()
        groupMessageInfo.targetId = targetId
        groupMessageInfo.fromId = fromId
        groupMessageInfo.content = content
        groupMessageInfo.fromName = TeacherManager.getUser().userName
        groupMessageInfo.targetName = teacherName
        groupMessageInfo.sendTime = TimeUtil.getNowDatetime()
        send(JSON.toJSONString(groupMessageInfo))
        Log.e("lzh", "submit" + JSON.toJSONString(groupMessageInfo))
    }

    fun sendTalkMessage(messageinfo: MessageInfo) {
        if (messageinfo != null) {
            send(JSON.toJSONString(messageinfo, SerializerFeature.WriteMapNullValue))
        }

    }

}
