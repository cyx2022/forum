package com.cyx.main.controller;

import com.cyx.common.entity.Member;
import com.cyx.common.resp.RespBean;
import com.cyx.common.to.MemberFavorTo;
import com.cyx.common.to.MemberTo;
import com.cyx.main.service.MemberService;
import com.cyx.main.to.MemberUpdateTo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api("用户相关")
@RestController
@RequestMapping("/main/member")
public class MemberController {
    @Resource
    MemberService memberService;


//    TODO 服务调用  多个条件都为空会搜索出所有数据，所以要排除所有参数都为空的请求
    @ApiOperation("根据用户的查询因子进行单用户查询")
    @GetMapping("/get")
    public RespBean getMember(MemberFavorTo to,HttpServletRequest request){
        Member member=memberService.getMember(to,request);
        return RespBean.success(member);
    }


    @ApiOperation("新增用户")
    @PostMapping("/add")
    public RespBean add(@RequestBody MemberTo to){
        long res=memberService.add(to);
        return RespBean.success(res);
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/update")
    public RespBean update(@RequestBody MemberUpdateTo to){
        boolean res = memberService.toUpdate(to);
        return RespBean.success(res);
    }

}
