package com.cyx.main.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.common.entity.Label;
import com.cyx.main.config.RedissonConfig;
import com.cyx.main.lock.RedisLockKey;
import com.cyx.main.mapper.LabelMapper;
import com.cyx.main.service.LabelService;
import com.cyx.main.utils.key.LabelCacheKey;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements LabelService {
    @Resource
    RedisTemplate redisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Override
    public List<Label> getList() {
        String key = LabelCacheKey.LABEL_KEY;
        List<Label> res;
        res = (List<Label>) redisTemplate.opsForValue().get(key);
        if (res!=null) return res;
//        使用分布式锁控制对label的请求
        RLock lock = redissonClient.getLock(RedisLockKey.LABEL_LOCK);
        lock.lock();
        res = (List<Label>) redisTemplate.opsForValue().get(key);
        if(res==null) {
            res = list();
            redisTemplate.opsForValue().set(key, res);
        }
        lock.unlock();
        return res;
    }
}
