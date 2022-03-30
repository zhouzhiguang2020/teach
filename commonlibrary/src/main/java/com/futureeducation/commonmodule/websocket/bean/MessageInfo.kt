package com.futureeducation.commonmodule.websocket.bean

import androidx.annotation.Keep
import com.futureeducation.commonmodule.utill.TimeUtil

@Keep
class MessageInfo : Comparable<MessageInfo> {

    var id: String? = null
    var key: String? = null
    var creatorId: String? = null

    //如课堂内的消息消息体内的“irsrId”字段就是上课堂id
    var irsrId: String? = null
    /**
     * 消息类型，默认为用户[MessageTargetType]
     */
    /**
     * 目标类型
     * @param targetType [MessageTargetType]
     */
    var targetType: Int = 0

    //目标id，默认为*，CourseGroup 或者个人[id]
    var targetId: String? = null

    var targetName: String? = null

    //格式类型，默认为文本{@link ContentFormatType}
    var formatType: Int = 0

    var content: String? = null

    //发件人 ids
    var fromId: String? = null

    //发件人 名字
    var fromName: String? = null

    //发送者头像
    var avatar: String? = null

    var tag: String? = null

    var sendTime: String? = null

    /**
     * 内容扩展
     * @return
     */
    var contentExpand: String? = null

    var functionKey: String? = null

    var targetUserIds: Array<String>? = null

    var fromPlatformType: String? = null


    var fromDeviceNo: String? = null
    var fromIP: String? = null

    //是否是存档消息
    var isArchive = false
    var fromConnectionId: String? = null
    //消息序列号
    var serialNo: Int? = 0

    //回复消息
    var replyContent: String? = null

    var isReply:Boolean? = false


    constructor()
    //聊天KEY
    /*public static final String CHAT_KEY = "chat";
    //创建群KEY
    public static final String CREATE_GROUP_KEY = "createGroup";
    //加入群KEY
    public static final String JOIN_GROUP_KEY = "joinGroup";
    //移出群KEY
    public static final String SHIFT_OUT_GROUP_KEY = "shiftOutGroup";
    //修改群名KEY
    public static final String MODIFY_GROUP_NAME_KEY = "modifyGroupName";
    //解散群KEY
    public static final String DISSOLVE_GROUP_KEY = "dissolveGroup";
    //设置群状态KEY
    public static final String SET_GROUP_STATUS_KEY = "setGroupStatus";*/


    enum class MessageKey private constructor(//设置群状态KEY

        private val value: String) {
        CHAT_KEY("chat"), //聊天KEY
        CREATE_GROUP_KEY("createGroup"), //创建群KEY
        JOIN_GROUP_KEY("joinGroup"), //加入群KEY
        SHIFT_OUT_GROUP_KEY("shiftOutGroup"), //移出群KEY
        MODIFY_GROUP_NAME_KEY("modifyGroupName"), //修改群名KEY
        DISSOLVE_GROUP_KEY("dissolveGroup"), //解散群KEY
        SET_GROUP_STATUS_KEY("setGroupStatus");

        fun value(): String {
            return this.value
        }
    }

    // 消息目标类型
    /*
    * //    //私聊 int SINGLE = 0;
    // 群聊 Group int GROUP = 1;
    //聊天室  int CHATROOM = 2;
    //频道
    int Channel = 3;
    * */
    enum class MessageTargetType private constructor(value: Int) {
        USER(1), // 用户
        GROUP(2), //群组
        CLASS(3),//班级
        COURSE(4),//课程
        COURSEGROUP(5); //课程小组

        // 班级
        private var value = 0

        init {
            this.value = value
        }

        fun value(): Int {
            return this.value
        }
    }

    enum class ContentFormatType private constructor(private val value: Int) {
        TEXT(1),
        PICTURE(2),
        VIDEO(3),
        AUDIO(4),
        OTHER_FILE(5),
        OTHER(255);

        fun value(): Int {
            return this.value
        }
    }

    // 消息平台类型
    enum class MessagePlatformType {
        // PC浏览器
        PC_BROWER,

        //移动浏览器
        MOBILE_BROWER,

        // PC桌面
        PC_DESKTOP,

        // IOS
        IOS,

        // 安卓
        ANDROID
    }

    override fun toString(): String {
        return "MessageInfo(id=$id, key=$key, creatorId=$creatorId, irsrId=$irsrId, targetType=$targetType, serialNo=$serialNo,isReply=$isReply, replyContent=$replyContent,  targetId=$targetId, targetName=$targetName, formatType=$formatType, content=$content, fromId=$fromId, fromName=$fromName, avatar=$avatar, tag=$tag, sendTime=$sendTime, contentExpand=$contentExpand, functionKey=$functionKey, targetUserIds=${targetUserIds?.contentToString()}, fromPlatformType=$fromPlatformType, fromDeviceNo=$fromDeviceNo, fromIP=$fromIP, isArchive=$isArchive, fromConnectionId=$fromConnectionId)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MessageInfo

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    // /**
    //        * compareTo()：大于0表示前一个数据比后一个数据大， 0表示相等，小于0表示前一个数据小于后一个数据

    //         */
    override fun compareTo(other: MessageInfo): Int {
        var time1 = this.sendTime
        var time2 = other.sendTime
        var serialNo1 = this.serialNo
        var serialNo2 = other.serialNo
        var sendtime = TimeUtil.timeStrToSecond(time1)
        var sendtime2 = TimeUtil.timeStrToSecond(time2)
        if (sendtime.compareTo(sendtime2) == 0) {

            return serialNo1!!.compareTo(serialNo2!!)
        } else {
            return sendtime.compareTo(sendtime2)
        }
        //return Long.co(sendtime,sendtime2);
    }


}
