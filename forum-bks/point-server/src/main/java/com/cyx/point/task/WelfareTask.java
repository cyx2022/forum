package com.cyx.point.task;

import com.cyx.common.commonConstant.CommonRedisHeaderConstant;
import com.cyx.common.commonConstant.CommonRedisTimeOutConstant;
import com.cyx.common.commonConstant.CommonRedisValueConstant;
import com.cyx.point.constant.RedisHeaderConstant;
import com.cyx.point.service.impl.WelfareServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

//每日积分福利上架
@Component
public class WelfareTask {
    @Autowired
    RedisTemplate redisTemplate;
    @Scheduled(cron = "0 0 0 * * ?")
    public void act() {
        WelfareServiceImpl.POINT_WELFARE_FLAG=true;
        String key = CommonRedisHeaderConstant.getPointWelfareKey();
        String preKey = CommonRedisHeaderConstant.getPointWelfarePreviousKey();
        redisTemplate.delete(preKey);
        redisTemplate.delete(RedisHeaderConstant.POINT_WELFAE_POOL_HEAD);
        redisTemplate.opsForValue().set(key, CommonRedisValueConstant.POINT_WELFARE_COUNT, CommonRedisTimeOutConstant.POINT_WELFARE_DAILY_TIMEOUT, TimeUnit.SECONDS);
    }
}
