package com.cyx.main.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.common.entity.Bar;
import com.cyx.common.resp.RespBean;
import com.cyx.main.annotation.OperatorLog;
import com.cyx.main.annotation.SensitiveFilter;
import com.cyx.main.dto.BarDto;
import com.cyx.main.service.BarService;
import com.cyx.main.service.ContentService;
import com.cyx.main.to.BarAddTo;
import com.cyx.main.to.BarFavorTo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("bar相关的请求")
@RestController
@RequestMapping("/main/bar")
public class BarController {
    @Autowired
    BarService barService;
    @Autowired
    ContentService contentService;

    @ApiOperation("集结所有关于bar的查询信息进行查询")
    @OperatorLog()
    @RequestMapping("/get")
    public RespBean getBar(BarFavorTo to, Page<BarDto> page) {
        Page<BarDto> list = barService.getList(to, page);
        return RespBean.success(list);
    }

    @ApiOperation("工具contentId进行bar的获取")
    @RequestMapping("/content")
    public RespBean getByContentId(Long contentId) {
        Long id = barService.getByContentId(contentId);
        return RespBean.success(id);
    }


    @ApiOperation("feign接口获取所有bar信息")
    @GetMapping("/feign")
    public RespBean feignList() {
        List<Bar> list = barService.feignList();
        return RespBean.success(list);
    }

    @ApiOperation("根据id获取")
    @RequestMapping("/one")
    public RespBean one(long id) {
        BarDto bar = barService.getBar(id);
        return RespBean.success(bar);
    }

    @ApiOperation("新增bar")
    @SensitiveFilter
    @PostMapping("/add")
    public RespBean add(@RequestBody BarAddTo to) {
        long id = barService.add(to);
        return RespBean.success(id);
    }

    @ApiOperation("更新bar信息")
    @PostMapping("/update")
    public RespBean update(Long id, Long labelId, String name, String tip, String img) {
        boolean res = barService.toUpdate(id, labelId, name, tip, img);
        return RespBean.success(res);
    }


    @ApiOperation("feign工具id集合获取bar信息")
    @GetMapping("/feignByIds")
    public RespBean feignByIds(@RequestParam("ids") List<Long> ids) {
        List<Bar> res = barService.feignByIds(ids);
        return RespBean.success(res);
    }

    @ApiOperation("删除bar")
    @PostMapping("/del")
    public RespBean delete(@RequestBody Map<String,Long> map){
        boolean res = barService.delete(map);
        return RespBean.success(res);
    }


}
