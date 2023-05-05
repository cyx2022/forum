package com.cyx.search.controller;

import com.cyx.common.resp.RespBean;
import com.cyx.search.constant.EsDataConstant;
import com.cyx.search.entity.EsData;
import com.cyx.search.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Api("概括搜索相关")
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchService searchService;

    @ApiOperation("根据关键字进行概括搜索")
    @GetMapping("/get/word")
    public RespBean getByword(String word) throws IOException {
        List<String> l1= new ArrayList<>();
        l1.add(EsDataConstant.TITLE);
        l1.add(EsDataConstant.TEXT);
        List<String> l2=new ArrayList<>();
        l2.add(word);
        l2.add(word);
        List<EsData> res = searchService.search(EsDataConstant.INDEX, l1, l2, null,null,EsDataConstant.SIZE, EsData.class);
        return RespBean.success(res);
    }
}
