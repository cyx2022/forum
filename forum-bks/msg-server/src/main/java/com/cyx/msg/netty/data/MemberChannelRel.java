package com.cyx.msg.netty.data;

import io.netty.channel.Channel;

import java.util.HashMap;

public class MemberChannelRel {
    private static HashMap<Long, Channel> manage=new HashMap<>();
    public static void put(long senderId,Channel channel){
        manage.put(senderId,channel);
    }

    public static Channel get(long memberId){
        return manage.get(memberId);
    }

}
