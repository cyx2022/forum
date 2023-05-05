package com.cyx.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.common.entity.Bar;
import com.cyx.common.entity.FocusBar;
import com.cyx.main.constant.RedisHeaderConstant;
import com.cyx.main.constant.RedisTimeOutConstant;
import com.cyx.common.holder.MemberIdHolder;
import com.cyx.main.mapper.BarMapper;
import com.cyx.main.mapper.FocusBarMapper;
import com.cyx.main.service.BarService;
import com.cyx.main.service.FocusBarService;
import com.cyx.main.utils.key.FocusBarCacheKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class FocusBarServiceImpl extends ServiceImpl<FocusBarMapper, FocusBar> implements FocusBarService {

    @Resource
    BarMapper barMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    BarService barService;

    @Override
    public List<Bar>  getListByMemberId(long id) {
        String key= RedisHeaderConstant.getFocusBarKey(id);
//        查询缓存
        ZSetOperations operations = redisTemplate.opsForZSet();
        Set<Bar> set = operations.reverseRange(key, 0, -1);
        if(set.size()!=0){
            List<Bar> list=new ArrayList<>();
            set.forEach(e->{
                list.add(e);
            });
            return list;
        }

        List<Bar> list = barMapper.getListByMemberId(id);
//        设置缓存，按照人数排序
        for(int i=0;i<list.size();i++){
            Bar e = list.get(i);
            operations.add(key,e,Double.valueOf(e.getNumber()));
        }
//        设置过期时间
        redisTemplate.boundValueOps(key).expire(RedisTimeOutConstant.FOCUS_BAR_TIMEOUT,TimeUnit.SECONDS);
        return list;
    }

    @Override
    public long doCount(long barId) {
        String key = FocusBarCacheKey.getFocusBarKey(barId);
        Object res = redisTemplate.opsForValue().get(key);
        if (res != null) return ((Integer)res).longValue();
        QueryWrapper<FocusBar> wrapper = new QueryWrapper<>();
        wrapper.eq("bar_id", barId);
        res = count(wrapper);
        redisTemplate.opsForValue().set(key,res, RedisTimeOutConstant.CONTENT_COUNT_TIMEOUT, TimeUnit.SECONDS);
        return (Long)res;
    }

    @Override
    public boolean cancelFocus(Long barId) {
        long memberId = MemberIdHolder.getMemberId();
        QueryWrapper<FocusBar> wrapper=new QueryWrapper<>();
        wrapper.eq("member_id",memberId).eq("bar_id",barId);
        boolean res = this.remove(wrapper);
//        对zset的元素更新困难，直接删除
        if(res){
            long memberIdHolder=MemberIdHolder.getMemberId();
            String key=RedisHeaderConstant.getFocusBarKey(memberIdHolder);
            redisTemplate.delete(key);
        }
        return res;
    }

    @Override
    public boolean doFocus(Map<String, Long> map) {
        long memberId = MemberIdHolder.getMemberId();
        FocusBar focusBar = new FocusBar(null,memberId,map.get("barId"),new Date());
        boolean res = save(focusBar);
        if(res){
            long memberIdHolder=MemberIdHolder.getMemberId();
            String key=RedisHeaderConstant.getFocusBarKey(memberIdHolder);
            Boolean hasKey = redisTemplate.hasKey(key);
//            如果该关注列表已经被删除，无序更新关注列表，因为数据已经不完整
            if(hasKey){
                Bar bar = barService.getById(map.get("barId"));
                redisTemplate.opsForZSet().add(key,bar,bar.getNumber());
            }
        }
        return res;
    }
}
