package com.cyx.main.controller;

import com.cyx.common.resp.RespBean;
import com.cyx.main.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api("收藏相关")
@RestController
@RequestMapping("/main/store")
public class StoreController {
    @Autowired
    StoreService storeService;

    @ApiOperation("新增收藏")
    @PostMapping("/add")
    public RespBean add(@RequestBody Map<String,Long> map){
        boolean res = storeService.add(map);
        return RespBean.success(res);
    }

    @ApiOperation("取消收藏")
    @PostMapping("/cancel")
    public RespBean cancel(@RequestBody Map<String,Long> map) {
        boolean res = storeService.cancel(map);
        return RespBean.success(res);
    }

    @ApiOperation("查看收藏")
    @GetMapping("/check")
    public RespBean check(Long contentId){
        boolean res = storeService.check(contentId);
        return RespBean.success(res);
    }



}
