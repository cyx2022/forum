package com.cyx.msg.netty.data;

import lombok.Data;

@Data
public class ChatMsg {
    private Integer senderId;
    private Integer receiverId;
    private String msg;
    private Long msgId;
}
