package com.cyx.point.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cyx.common.entity.Point;
import com.cyx.point.mapper.PointMapper;
import com.cyx.point.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service("pointsService")
public class PointServiceImpl extends ServiceImpl<PointMapper, Point> implements PointService {

    @Autowired
    PointService pointService;

    @Override
    public Point getByMid(long mid) {
        QueryWrapper<Point> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",mid);
        Point point = getOne(wrapper);
        return point;
    }

    @Override
    public boolean addPoint(long mid, long count) {
        UpdateWrapper<Point> wrapper=new UpdateWrapper<>();
        wrapper.eq("member_id",mid);
        wrapper.setSql("hold=hold+"+count);
        boolean res = pointService.update(wrapper);
        return res;
    }

    @Override
    public void delPoint(long mid, long count) {
        UpdateWrapper<Point> wrapper=new UpdateWrapper<>();
        wrapper.eq("member_id",mid);
        wrapper.setSql("'hold'='hold'-"+count);
    }
}