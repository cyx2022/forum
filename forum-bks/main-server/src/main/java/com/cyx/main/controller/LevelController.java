package com.cyx.main.controller;

import com.cyx.common.entity.Level;
import com.cyx.common.resp.RespBean;
import com.cyx.main.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main/level")
public class LevelController {
    @Autowired
    LevelService levelService;
    @RequestMapping("/id")
    public RespBean getLevel(long id){
        Level level = levelService.getById(id);
        return RespBean.success(level);
    }
}
