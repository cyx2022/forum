package com.cyx.auth.constant;

public class MemberContant {
    //    token前缀
    public static final String TOKEN="token:";

    //    refresh token前缀
    public static final String REFRESH_TOKEN="refresh_token:";
//    token过期时间----5分钟
    public static final int TOKEN_EXPIRE=60*60*30*30;
    //    refresh_token过期时间---7天
    public static final int REFRESH_TOKEN_EXPIRE=60*60*24*7*30;



}
