package com.cyx.msg.holder;

import lombok.Data;

@Data
public class MemberIdHolder {
    static ThreadLocal<Long> memberId=new ThreadLocal<>();
    public static void setMemberId(long userId){
        memberId.set(userId);
    }

    public static long getMemberId(){
        return memberId.get();
    }
}
