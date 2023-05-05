package com.cyx.search.controller;

import com.cyx.common.entity.Content;
import com.cyx.common.resp.RespBean;
import com.cyx.search.service.ContentSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Api("content搜索相关")
@RestController
@RequestMapping("/search/content")
public class ContentSearchController {
    @Resource
    ContentSearchService contentSearchService;

    @ApiOperation("根据关键字对content进行搜索")
    @GetMapping("/word")
    public RespBean searchByWord(String word) throws IOException {
        List<Content> list = contentSearchService.searchByWord(word);
        return RespBean.success(list);
    }

    @ApiOperation("bar内根据关键字进行搜索")
    @GetMapping("/id/word")
    public RespBean searchByIdAndWord(String id,String word) throws IOException {
        List<Content> list = contentSearchService.searchByIdAndWord(id,word);
        return RespBean.success(list);
    }
}
