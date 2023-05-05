package com.cyx.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.common.entity.FocusMember;
import com.cyx.common.holder.MemberIdHolder;
import com.cyx.main.mapper.FocusMemberMapper;
import com.cyx.main.service.FocusMemberService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Service
public class FocusMemberServiceImpl extends ServiceImpl<FocusMemberMapper, FocusMember> implements FocusMemberService {
    @Override
    public boolean doFocus(Map<String,Long> map) {
        Long id=map.get("id");
        FocusMember focusMember=new FocusMember();
        focusMember.setFromId(MemberIdHolder.getMemberId());
        focusMember.setToId(id);
        focusMember.setFocusTime(new Date());
        boolean res = save(focusMember);
        return res;
    }

    @Override
    public boolean check(Long id) {
        QueryWrapper<FocusMember> wrapper=new QueryWrapper<>();
        wrapper.eq("from_id",MemberIdHolder.getMemberId());
        wrapper.eq("to_id",id);
        FocusMember one = getOne(wrapper);
        return one==null;
    }
}
