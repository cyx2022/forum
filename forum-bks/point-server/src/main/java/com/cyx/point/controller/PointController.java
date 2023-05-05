package com.cyx.point.controller;

import com.cyx.common.entity.Point;
import com.cyx.common.resp.RespBean;
import com.cyx.point.service.PointService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiOperation("积分相关")
@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    PointService pointService;

    @ApiOperation("根据用户id获取积分")
    @GetMapping("/get/mid")
    public RespBean getPoint(long mid){
        Point point=pointService.getByMid(mid);
        return RespBean.success(point);
    }

    @ApiOperation("根据用户id进行增加")
    @PutMapping("/update/add")
    public RespBean addPoint(long mid,long count){
        boolean res = pointService.addPoint(mid, count);
        return RespBean.success(res?1:0);
    }

//    @PutMapping("/update/del")
//    public RespBean delPoint(long mid,long count){
//        pointService.delPoint(mid,count);
//    }

}
