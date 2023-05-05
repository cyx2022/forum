package com.cyx.main.constant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.common.entity.Comment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RedisHeaderConstant {
    public static String FOCUS_BAR_CACHE_HEAD="focus:bar:";
    public static String FOCUS_MEMBER_CACHE_HEAD="focus:bar:";
    public static String MEMBER_CACHE_HEAD="member:";
    public static String COMMENT_CACHE_HEAD="comment:";

//    每日积分结束的标记
    public static String POINT_WELFARE_DOWN_FLAG="point:welfare:down:flag";
    public static String getFocusBarKey(long id){
        return FOCUS_BAR_CACHE_HEAD+id;
    }

    public static String getFocusMemberKey(long id){
        return FOCUS_MEMBER_CACHE_HEAD+id;
    }

    public static String getMemberKey(long id){
        return MEMBER_CACHE_HEAD+id;
    }
    public static String getCommentKey(long id, Page<Comment> page){
        String key=COMMENT_CACHE_HEAD+id+":"+page.getCurrent()+":"+page.getSize();
        return key;
    }


}
