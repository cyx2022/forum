package com.cyx.main.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.common.entity.Bar;
import com.cyx.common.entity.Content;
import com.cyx.common.resp.RespBean;
import com.cyx.main.dto.ContentDto;
import com.cyx.main.service.ContentService;
import com.cyx.main.to.ContentAddTo;
import com.cyx.main.to.ContentFavorTo;
import com.cyx.main.to.ContentUpdateTo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("content相关")
@RestController
@RequestMapping("/main/content")
public class ContentController {
    @Autowired
    ContentService contentService;

    @ApiOperation("根据id进行获取")
    @GetMapping("/one")
    public RespBean one(long id){
        Content content=contentService.getOneById(id);
        return RespBean.success(content);
    }

    @ApiOperation("根据 memberId barId  进行获取/排序")
    @GetMapping("/list")
    public RespBean list(ContentFavorTo to, Page<Content> page){
        Page<ContentDto> list=contentService.getList(to,page);
        return RespBean.success(list);
    }

    @ApiOperation("根据与content的查询因子进行查询")
    @GetMapping("/count")
    public RespBean count(ContentFavorTo to){
        long res = contentService.getCount(to);
        return RespBean.success(res);
    }

    @ApiOperation("根据label即类型对content进行分页查询")
    @GetMapping("/label")
    public RespBean getByLabelId(long label,long order,Page<Content> page){
        Page<ContentDto> res = contentService.getByLabelId(label,order, page);
        return RespBean.success(res);
    }

    @ApiOperation("新增content")
    @PostMapping("/add")
    public RespBean addContent(ContentAddTo to){
        boolean res = contentService.addContent(to);
        return RespBean.success(res);
    }

    @ApiOperation("修改content")
    @PostMapping("/update")
    public RespBean updateContent(@RequestBody ContentUpdateTo to){
        boolean res = contentService.updateContent(to);
        return RespBean.success(res);
    }

    @ApiOperation("feign接口远程拉取所有content")
    @GetMapping("/feign")
    public RespBean feign() {
        List<Content> res=contentService.feignList();
        return RespBean.success(res);
    }

    @ApiOperation("feign工具content集合获取bar信息")
    @GetMapping("/feignByIds")
    public RespBean feignByIds(@RequestParam("ids") List<Long> ids){
        List<Content> res=contentService.feignByIds(ids);
        return RespBean.success(res);
    }

    @ApiOperation("获取吧主的收藏content(即推荐)")
    @GetMapping("/recommend")
    public RespBean recommend(Long barId,Page<Content> page){
        Page<ContentDto> res=contentService.recommend(barId,page);
        return RespBean.success(res);
    }

    @ApiOperation("查看bar主的内容")
    @GetMapping("/owner")
    public RespBean getByOwner(Long id,Page<Content> page){
        Page<ContentDto> res=contentService.getByOwner(id,page);
        return RespBean.success(res);
    }

    @ApiOperation("根据contentId进行删除")
    @DeleteMapping("/del")
    public RespBean delById(Long contentId){
        boolean res = contentService.delById(contentId);
        return RespBean.success(res);
    }



}
