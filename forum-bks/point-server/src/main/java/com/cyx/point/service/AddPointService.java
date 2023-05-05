package com.cyx.point.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.common.entity.Point;
import com.cyx.point.entity.AddPoint;

import java.util.Date;

public interface AddPointService extends IService<AddPoint> {
    public boolean add(Long commentId, Long subSommentId, Date publicTime);

    public boolean delete(Date publicTime);
}
