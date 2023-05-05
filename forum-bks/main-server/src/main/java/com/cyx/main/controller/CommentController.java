package com.cyx.main.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.common.entity.Comment;
import com.cyx.common.resp.RespBean;
import com.cyx.main.service.CommentService;
import com.cyx.main.to.CommentAddTo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("评论相关")
@RestController
@RequestMapping("/main/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @ApiOperation("工具contentId进行分页查询")
    @GetMapping("/content")
    public RespBean getByContentId(long contentId, Page<Comment> page){
        Page<Comment> res = commentService.getByContentId(contentId, page);
        return RespBean.success(res);
    }

    @ApiOperation("新增comment")
    @PostMapping("/add")
    public RespBean addComment(@RequestBody CommentAddTo to){
        Comment res = commentService.addComment(to);
        return RespBean.success(res);
    }

    @ApiOperation("根据commentId获取comment信息")
    @GetMapping("/get")
    public RespBean getById(Long id){
        Comment res = commentService.getById(id);
        return RespBean.success(res);
    }

//    @ApiOperation("根据commentId")

}
