package com.cyx.main.controller;

import com.cyx.common.entity.Ban;
import com.cyx.common.resp.RespBean;
import com.cyx.main.service.BanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/main/ban")
public class BanController {

    @Autowired
    BanService banService;
    @GetMapping("/list")
    public RespBean list(){
        List<Ban> res = banService.list();
        return RespBean.success(res);
    }
}
