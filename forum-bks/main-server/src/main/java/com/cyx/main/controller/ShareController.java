package com.cyx.main.controller;

import com.cyx.common.resp.RespBean;
import com.cyx.main.service.ShareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api("分享相关")
@RestController
@RequestMapping("/main/share")
public class ShareController {
    @Autowired
    ShareService shareService;

    @ApiOperation("新增分享")
    @PostMapping("/add")
    public RespBean add(@RequestBody Map<String,Long> map){
        boolean res = shareService.add(map);
        return RespBean.success(res);
    }


}
