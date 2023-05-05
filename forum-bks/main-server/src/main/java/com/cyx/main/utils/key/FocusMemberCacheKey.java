package com.cyx.main.utils.key;

public class FocusMemberCacheKey {
    public static String FOCUS_MEMBER_CACHE_HEAD="focus:member:";

    public static String getFocusMemberKey(long id){
        return FOCUS_MEMBER_CACHE_HEAD+id;
    }

}
