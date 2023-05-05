package com.cyx.main.constant;

public class RedisTimeOutConstant {
//    当个bar过期时间
    public static final Long BAR_SINGLE_TIMEOUT=60*60*12L;
//    多个bar过期时间
    public static final Long BAR_LIST_TIMEOUT=60*60*6L;

//    单个content过期时间
    public static final Long CONTENT_SINGLE_TIMEOUT=60*60l;
//    多个content过期时间
    public static final Long CONTENT_LIST_TIMEOUT=60*60*6L;

//    多个comment过期时间
    public static final Long COMMENT_LIST_TIMEOUT=60*60L;
//    单个bar的content 数量过期时间
    public static final Long CONTENT_COUNT_TIMEOUT=60*60*2L;

//    关注列表的过期时间
    public static final Long FOCUS_BAR_TIMEOUT=60*60*2L;

//    用户信息过期时间
    public static final Long MEMBER_TIMEOUT=60*60*24L;


}


