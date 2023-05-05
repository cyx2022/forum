package com.cyx.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyx.common.entity.Favor;
import com.cyx.common.holder.MemberIdHolder;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.main.mapper.FavorMapper;
import com.cyx.main.service.FavorService;

import java.util.Date;
import java.util.Map;


@Service("favorService")
public class FavorServiceImpl extends ServiceImpl<FavorMapper, Favor> implements FavorService {


    @Override
    public boolean add(Map<String,Long> map) {
        Favor favor=new Favor();
        favor.setContentId(map.get("contentId"));
        favor.setMemberId(MemberIdHolder.getMemberId());
        favor.setTime(new Date());
        boolean res = save(favor);
        return res;
    }

    @Override
    public boolean cancel(Map<String,Long> map) {
        QueryWrapper<Favor> wrapper=new QueryWrapper<>();
        wrapper.eq("content_id",map.get("contentId"));
        wrapper.eq("member_id",MemberIdHolder.getMemberId());
        boolean res = remove(wrapper);
        return res;
    }

    @Override
    public boolean check(Long contentId) {
        QueryWrapper<Favor> wrapper=new QueryWrapper<>();
        wrapper.eq("content_id",contentId);
        wrapper.eq("member_id",MemberIdHolder.getMemberId());
        Favor one = getOne(wrapper);
        return one!=null?true:false;
    }
}