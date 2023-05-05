package com.cyx.search.controller;

import com.cyx.common.entity.Bar;
import com.cyx.common.resp.RespBean;
import com.cyx.search.service.BarSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Api("bar搜索相关")
@RestController
@RequestMapping("/search/bar")
public class BarSearchController {
    @Resource
    BarSearchService barSearchService;

    @ApiOperation("根据关键字对bar进行搜索")
    @GetMapping("/word")
    public RespBean searchByWord(String word) throws IOException {
        List<Bar> list=barSearchService.searchByWord(word);
        return RespBean.success(list);
    }



}
