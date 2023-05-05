package com.cyx.point.constant;

import com.cyx.common.utils.UUIDUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RedisHeaderConstant {
    public static String POINT_MAX_HEAD="point:max:";
//    public static String POINT_WELFARE_HEAD="welfare:";
    public static String   POINT_WELFAE_POOL_HEAD="welfare_pool_head";

    public static String getPointMaxKey(long id){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String s = simpleDateFormat.format(date);
        return POINT_MAX_HEAD+id+":"+s;
    }

    public static String getPointMaxPrevious(long id){
        Date date = new Date();
        // 创建 Calendar 实例并设置日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 将日期减一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        // 格式化日期为字符串
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String s = simpleDateFormat.format(calendar.getTime());
        return POINT_MAX_HEAD+id+":"+s;
    }

//    public static String getWelfareHead(Long memberId){
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//        String s = simpleDateFormat.format(date);
//        String uuid = UUIDUtil.uuid();
////        key为用户id+uuid+时间
//        String key=memberId+":"+s+":"+uuid;
//        return key;
//    }
}
