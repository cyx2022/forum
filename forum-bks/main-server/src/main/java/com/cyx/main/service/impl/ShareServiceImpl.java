package com.cyx.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyx.common.entity.Share;
import com.cyx.common.holder.MemberIdHolder;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.cyx.main.mapper.ShareMapper;
import com.cyx.main.service.ShareService;

import java.util.Date;
import java.util.Map;


@Service("shareService")
public class ShareServiceImpl extends ServiceImpl<ShareMapper, Share> implements ShareService {


    @Override
    public boolean add(Map<String,Long> map) {
        QueryWrapper<Share> wrapper=new QueryWrapper<>();
        wrapper.eq("member_id", MemberIdHolder.getMemberId());
        wrapper.eq("content_id",map.get("contentId"));
        Share one = getOne(wrapper);
        if(one!=null){
            one.setCount(one.getCount()+1);
            one.setLastTime(new Date());
            boolean res = update(one, wrapper);
            return res;
        }else{
            Share share=new Share();
            share.setLastTime(new Date());
            share.setCount(1L);
            share.setContentId(map.get("contentId"));
            share.setMemberId(MemberIdHolder.getMemberId());
            boolean res = save(share);
            return res;
        }

    }
}