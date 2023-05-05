export default {
    //和后段枚举一一对应
    CONNECT: 1,    // "第一次(或重连)初始化连接"
    CHAT: 2,       // "聊天消息"),	
    SIGNED: 3,     // "消息签收"),
    KEEPALIVE: 4,  //"客户端保持心跳"),
    ERROR:5,       //消息发送与源异常token过期，不经过网关，
    TOKENILLEGAL:6,  //token异常；
    ChatMsg: function (senderId, receiverId, msg, msgId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.msg = msg;
        this.msgId = msgId;
    },
    DataContent: function (action, chatMsg, extend) {
        this.action = action;
        this.chatMsg = chatMsg;
        this.extend = extend;
    }

}