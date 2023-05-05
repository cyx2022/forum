package com.cyx.main.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.common.entity.SubComment;
import com.cyx.common.resp.RespBean;
import com.cyx.main.service.SubCommentService;
import com.cyx.main.to.SubCommentAddTo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("二级论相关")
@RestController
@RequestMapping("/main/subComment")
public class SubCommentController {
    @Autowired
    SubCommentService subCommentService;

    @ApiOperation("根据一级评论对二级评论进行分页查询")
    @GetMapping("/comment")
    public RespBean get(long commentId, Page<SubComment> page){
        Page<SubComment> res = subCommentService.getByCommentId(commentId, page);
        return RespBean.success(res);
    }

    @ApiOperation("新增二级评论")
    @PostMapping("/add")
    public RespBean addSubComment(@RequestBody SubCommentAddTo to){
        SubComment res = subCommentService.addSubComment(to);
        return RespBean.success(res);
    }

    @ApiOperation("根据二级评论id获取二级评论")
    @GetMapping("/get")
    public RespBean getById(Long id){
        SubComment res = subCommentService.getById(id);
        return RespBean.success(res);
    }
}
