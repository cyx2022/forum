package com.cyx.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.common.entity.Store;
import com.cyx.common.holder.MemberIdHolder;
import com.cyx.main.mapper.StoreMapper;
import org.springframework.stereotype.Service;


import com.cyx.main.service.StoreService;

import java.util.Date;
import java.util.Map;
import java.util.logging.MemoryHandler;


@Service("storeService")
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {


    @Override
    public boolean add(Map<String,Long> map) {
        Store store=new Store();
        store.setContentId(map.get("contentId"));
        store.setMemberId(MemberIdHolder.getMemberId());
        store.setTime(new Date());
        boolean res = save(store);
        return res;
    }

    @Override
    public boolean cancel(Map<String,Long> map) {
        QueryWrapper<Store> wrapper=new QueryWrapper<>();
        wrapper.eq("member_id",MemberIdHolder.getMemberId());
        wrapper.eq("content_id",map.get("contentId"));
        boolean res = remove(wrapper);
        return res;
    }

    @Override
    public boolean check(Long contentId) {
        QueryWrapper<Store> wrapper=new QueryWrapper<>();
        wrapper.eq("member_id",MemberIdHolder.getMemberId());
        wrapper.eq("content_id",contentId);
        Store one = getOne(wrapper);
        return one!=null?true:false;
    }
}