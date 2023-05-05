package com.cyx.main.utils.key;

public class MemberCacheKey {
    public static String MEMBER_CACHE_HEAD="member:";
    public static String getMemberKey(long id){
        return MEMBER_CACHE_HEAD+id;
    }
}
