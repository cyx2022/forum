package com.cyx.main.utils.key;

public class FocusBarCacheKey {
    public static String FOCUS_BAR_CACHE_HEAD="focus:bar:";
    public static String getFocusBarKey(long id){
        return FOCUS_BAR_CACHE_HEAD+id;
    }

}
