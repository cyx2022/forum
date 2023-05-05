package com.cyx.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.common.entity.Inspect;
import com.cyx.common.holder.MemberIdHolder;
import com.cyx.main.mapper.InspectMapper;
import com.cyx.main.service.InspectService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class InspectServiceImpl extends ServiceImpl<InspectMapper, Inspect> implements InspectService {
    @Override
    public boolean add(Inspect inspect) {
        inspect.setBans(StringUtils.join(inspect.getBanArr(), ','));
        inspect.setMemberId(MemberIdHolder.getMemberId());
        inspect.setTime(new Date());
        inspect.setResult(0);
        inspect.setFlag(0);
        boolean res = save(inspect);
        return res;
    }

    @Override
    public Page<Inspect> listAll(Long barId, Page<Inspect> page) {
        QueryWrapper<Inspect> wrapper = new QueryWrapper<>();
        wrapper.eq("bar_id", barId);
        wrapper.eq("result",0);
        Page<Inspect> res = page(page, wrapper);
        return res;
    }

    @Override
    public Inspect getByContentId(Long type,Long contentId) {
        QueryWrapper<Inspect> wrapper = new QueryWrapper<>();
        wrapper.eq("item_id", contentId);
        wrapper.eq("type",type);
        wrapper.orderByDesc("time");
        wrapper.eq("result",0);
        Page<Inspect> page = new Page<>(1, 1);
        Page<Inspect> p = page(page, wrapper);
        if (p != null && p.getRecords() != null&&page.getRecords().size()>0)
            return p.getRecords().get(0);
        else
            return null;
    }

    @Override
    public boolean contentAccept(Map<String, Long> map) {
        Long contentId=map.get("contentId");

        QueryWrapper<Inspect> wrapper=new QueryWrapper<>();
        wrapper.eq("item_id",contentId);
        wrapper.eq("type",1);
        List<Inspect> list = list(wrapper);
        list.forEach(e->{
            e.setResult(1);
        });
        boolean res = updateBatchById(list);
        return res;
    }

    @Override
    public boolean contentRefuse(Map<String, Long> map) {
        Long contentId=map.get("contentId");

        QueryWrapper<Inspect> wrapper=new QueryWrapper<>();
        wrapper.eq("item_id",contentId);
        wrapper.eq("type",1);
        List<Inspect> list = list(wrapper);
        list.forEach(e->{
            e.setResult(-1);
        });
        boolean res = updateBatchById(list);
        return res;
    }

    @Override
    public boolean commentAccept(Map<String, Long> map) {
        Long contentId=map.get("commentId");

        QueryWrapper<Inspect> wrapper=new QueryWrapper<>();
        wrapper.eq("item_id",contentId);
        wrapper.eq("type",2);
        List<Inspect> list = list(wrapper);
        list.forEach(e->{
            e.setResult(1);
        });
        boolean res = updateBatchById(list);
        return res;
    }

    @Override
    public boolean commentRefuse(Map<String, Long> map) {
        Long contentId=map.get("commentId");

        QueryWrapper<Inspect> wrapper=new QueryWrapper<>();
        wrapper.eq("item_id",contentId);
        wrapper.eq("type",2);
        List<Inspect> list = list(wrapper);
        list.forEach(e->{
            e.setResult(-1);
        });
        boolean res = updateBatchById(list);
        return res;
    }

}
