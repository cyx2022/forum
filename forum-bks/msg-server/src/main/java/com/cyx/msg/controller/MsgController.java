package com.cyx.msg.controller;

import com.cyx.common.resp.RespBean;
import com.cyx.msg.entity.Msg;
import com.cyx.msg.service.MsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api("信息服务相关")
@RestController
@RequestMapping("/msg")
public class MsgController {
    @Resource
    MsgService msgService;

    @ApiOperation("获取系统消息的总数")
    @GetMapping("/system/count/all")
    public RespBean getSystemMsgCount(){
        long count = msgService.getSystemMsgCount();
        return RespBean.success(count);
    }

    @ApiOperation("获取最近一个月系统消息的列表")
    @GetMapping("/system/list/read")
    public RespBean getSystemMsgUnreadList(){
        List<Msg> list = msgService.getSystemMsgUnreadList();
        return RespBean.success(list);
    }

    @ApiOperation("获取系统最近消息的列表")
    @GetMapping("/system/list/latest")
    public RespBean getSystemMsgLatestList(){
        List<Msg> list = msgService.getSystemMsgLatestList();
        return RespBean.success(list);
    }

    @ApiOperation("获取个人消息的总数")
    @GetMapping("/person/count/all")
    public RespBean getPersonMsgCountAll(){
        long count=msgService.getPersonMsgCountAll();
        return RespBean.success(count);
    }

    @ApiOperation("获取个人消息的总数,针对某个用户")
    @GetMapping("/person/count/one")
    public RespBean getPersonMsgCountPer(long senderId){
        long count=msgService.getPersonMsgCountPer(senderId);
        return RespBean.success(count);
    }

//
    @ApiOperation("获取个人消息的列表，针对某个用户")
    @GetMapping("/person/list/one")
    public RespBean getPersonMsgList(long senderId){
        List<Msg> list=msgService.getPersonMsgList(senderId);
        return RespBean.success(list);
    }

    @ApiOperation("获取每个用户的第一条未签收的消息")
    @GetMapping("/person/latest/head")
    public RespBean getLatestHead(){
        List<Msg> list=msgService.getLatestHead();
        return RespBean.success(list);
    }

}
