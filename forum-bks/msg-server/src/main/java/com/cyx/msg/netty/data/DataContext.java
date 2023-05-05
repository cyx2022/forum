package com.cyx.msg.netty.data;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DataContext {
    private Integer action;
    private ChatMsg chatMsg;
    private String extend;
}
