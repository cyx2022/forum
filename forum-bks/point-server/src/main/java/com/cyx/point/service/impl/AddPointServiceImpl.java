package com.cyx.point.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.point.entity.AddPoint;
import com.cyx.point.mapper.AddPointMapper;
import com.cyx.point.service.AddPointService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AddPointServiceImpl extends ServiceImpl<AddPointMapper, AddPoint> implements AddPointService {
    public boolean add(Long commentId, Long subSommentId, Date publicTime) {
        AddPoint addPoint = new AddPoint(null, commentId, subSommentId, publicTime);
        boolean res = save(addPoint);
        return res;
    }

    @Override
    public boolean delete(Date publicTime) {
        Date date = new Date();
        QueryWrapper<AddPoint> wrapper = new QueryWrapper<>();
        wrapper.lt("public_time", date);
        boolean res = this.remove(wrapper);

        return res;
    }
}
