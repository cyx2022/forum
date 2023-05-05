package com.cyx.main.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.common.entity.Inspect;
import com.cyx.common.resp.RespBean;
import com.cyx.main.service.InspectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/main/inspect")
public class InspectController {
    @Resource
    InspectService inspectService;
    @PostMapping("/add")
    public RespBean add(@RequestBody Inspect inspect){
        boolean res = inspectService.add(inspect);
        return RespBean.success(res);
    }

    @GetMapping("/list")
    public RespBean listAll(Long barId, Page<Inspect> page){
        Page<Inspect> res=inspectService.listAll(barId,page);
        return RespBean.success(res);
    }

    @GetMapping("/content/one")
    public RespBean content(Long contentId,Long type){
        Inspect res=inspectService.getByContentId(type,contentId);
        return RespBean.success(res);
    }

    @PostMapping("/content/accept")
    public RespBean contentAccept(@RequestBody Map<String,Long> map){
        boolean res = inspectService.contentAccept(map);
        return RespBean.success(res);
    }

    @PostMapping("/content/refuse")
    public RespBean contentRefuse(@RequestBody Map<String,Long> map){
        boolean res = inspectService.contentRefuse(map);
        return RespBean.success(res);
    }

    @PostMapping("/comment/accept")
    public RespBean commentAccept(@RequestBody Map<String,Long> map){
        boolean res = inspectService.commentAccept(map);
        return RespBean.success(res);
    }

    @PostMapping("/comment/refuse")
    public RespBean commentRefuse(@RequestBody Map<String,Long> map){
        boolean res = inspectService.commentRefuse(map);
        return RespBean.success(res);
    }


}
