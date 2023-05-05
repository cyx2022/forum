package com.cyx.msg.netty.enums;

public enum MsgActionEnum {
    CONNECT(1,"连接初始化"),
    CHAT(2,"聊天信息"),
    SIGNED(3,"消息签收"),
    KEEPALIVE(4,"客户端保持心跳"),
//    没有经过网关
    ERROR(5,"发送源异常"),
//    token过期
    TOKENILLEGAL(6,"token非法");
public final Integer type;
public final String content;
MsgActionEnum(Integer type, String content){
    this.type=type;
    this.content=content;
}
public Integer getType(){
    return type;
}
}
