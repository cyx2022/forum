package com.cyx.point.service.impl;

import com.cyx.common.commonConstant.CommonRedisHeaderConstant;
import com.cyx.common.commonConstant.CommonRedisTimeOutConstant;
import com.cyx.common.commonConstant.CommonRedisValueConstant;
import com.cyx.common.holder.MemberIdHolder;
import com.cyx.common.utils.UUIDUtil;
import com.cyx.point.constant.RedisHeaderConstant;
import com.cyx.point.service.PointService;
import com.cyx.point.service.WelfareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class WelfareServiceImpl implements WelfareService {
    public static boolean POINT_WELFARE_FLAG=false;
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("pointWelfare")
    private RedisScript<Boolean> pointWelfare;

    @Autowired
    PointService pointService;
    @Override
    public String getPagePointUrl() {
//        if(!POINT_WELFARE_FLAG) return "";
        long memberId = MemberIdHolder.getMemberId();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String s = simpleDateFormat.format(date);
        String uuid = UUIDUtil.uuid();
        String setKey= RedisHeaderConstant.POINT_WELFAE_POOL_HEAD;
        Long flag = redisTemplate.opsForSet().add(setKey, memberId);
        if(flag==0L)return "";
//        key为用户id+uuid+时间
        String key="welfare:"+memberId+":"+s+":"+uuid;
        redisTemplate.opsForValue().set(key,1, CommonRedisTimeOutConstant.POINT_WELFARE_URL_TIMEOUT, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(key,1, CommonRedisTimeOutConstant.POINT_WELFARE_URL_TIMEOUT, TimeUnit.SECONDS);
        return uuid;
    }

    @Override
    public long pagePointPass(String key) {

        long memberId = MemberIdHolder.getMemberId();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String s = simpleDateFormat.format(date);
        String on="welfare:"+memberId+":"+s+":"+key;
        String value=String.valueOf(redisTemplate.opsForValue().get(on));
        if(value==null){
//            如果key是昨天的，需要删除
            Date d = new Date();
            // 创建 Calendar 实例并设置日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            // 将日期减一天
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            // 格式化日期为字符串
            simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String ss = simpleDateFormat.format(calendar.getTime());
            redisTemplate.delete(memberId+":"+ss+":"+key);
            return -1;
        }
        String pool= CommonRedisHeaderConstant.getPointWelfareKey();

        List<String> list = new ArrayList<>();
        list.add(pool);
        //TODO 积分福利在redis的标记，可能不用做
        list.add("11");
        boolean res = (Boolean) redisTemplate.execute(pointWelfare,list, Collections.EMPTY_LIST);
//        不足，返回
        if(!res){
            return -1;
        }
        pointService.addPoint(MemberIdHolder.getMemberId(),CommonRedisValueConstant.POINT_WELFARE_PER);

        return CommonRedisValueConstant.POINT_WELFARE_PER;
    }
}

