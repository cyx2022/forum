package com.cyx.main.controller;

import com.cyx.common.resp.RespBean;
import com.cyx.main.service.FavorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api("点赞相关")
@RestController
@RequestMapping("/main/favor")
public class FavorController {
    @Autowired
    FavorService favorService;

    @ApiOperation("新增点赞")
    @PostMapping("/add")
    public RespBean add(@RequestBody Map<String,Long> map){
        boolean res = favorService.add(map);
        return RespBean.success(res);
    }

    @ApiOperation("取消点赞")
    @PostMapping("/cancel")
    public RespBean cancel(@RequestBody Map<String,Long> map){
        boolean res = favorService.cancel(map);
        return RespBean.success(res);
    }

    @ApiOperation("查看点赞")
    @GetMapping("/check")
    public RespBean check(Long contentId){
        boolean res=favorService.check(contentId);
        return RespBean.success(res);
    }
}
