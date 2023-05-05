package com.cyx.main.controller;


import com.cyx.common.entity.Label;
import com.cyx.common.resp.RespBean;
import com.cyx.main.service.LabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("label即类型相关")
@RestController
@RequestMapping("/main/label")
public class LabelController {
    @Autowired
    LabelService labelService;

    @ApiOperation("查询所有类型信息")
    @GetMapping("/all")
    public RespBean getAll(){
        List<Label> list =labelService.getList();
        return RespBean.success(list);
    }


}
