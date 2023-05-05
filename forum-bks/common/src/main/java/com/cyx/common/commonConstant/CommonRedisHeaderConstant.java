package com.cyx.common.commonConstant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonRedisHeaderConstant {
    //    每日积分的头
    public static String POINT_WELFARE_HEAD="point:welfare:";
    //    获取每日积分的key
    public static String getPointWelfareKey() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        String s = simpleDateFormat.format(date);
        return POINT_WELFARE_HEAD+s;
    }
    //获取每日积分前一天的key
    public static String getPointWelfarePreviousKey() {
        Date date = new Date();
        // 创建 Calendar 实例并设置日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 将日期减一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        // 格式化日期为字符串
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String s = simpleDateFormat.format(calendar.getTime());
        return POINT_WELFARE_HEAD+s;
    }
}
