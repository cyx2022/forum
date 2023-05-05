package com.cyx.point.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.common.entity.Point;

/**
 * 
 *
 * @author cyx
 * @email 2906665963@qq.com
 * @date 2023-03-24 21:05:26
 */
public interface PointService extends IService<Point> {

    Point getByMid(long mid);

    boolean addPoint(long mid, long count);

    void delPoint(long mid, long count);
}

