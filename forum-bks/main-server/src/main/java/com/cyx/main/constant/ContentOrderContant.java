package com.cyx.main.constant;

import java.util.HashMap;
import java.util.Map;

//content的排序方式
public class ContentOrderContant {
    public static final Map<Long,String> orders;
    static {
        orders=new HashMap<>();
        orders.put(1L,"share_count,store_count,comment_count");
        orders.put(2L,"publish_time");
    }
}
