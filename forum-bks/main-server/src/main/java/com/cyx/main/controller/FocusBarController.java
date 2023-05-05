package com.cyx.main.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyx.common.entity.Bar;
import com.cyx.common.entity.FocusBar;
import com.cyx.common.resp.RespBean;
import com.cyx.common.holder.MemberIdHolder;
import com.cyx.main.service.FocusBarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api("bar的关注相关")
@RestController
@RequestMapping("/main/focus/bar")
public class FocusBarController {

    @Resource
    FocusBarService focusBarService;

    @ApiOperation("根据关注者列表返回bar的列表")
    @GetMapping("/member")
    public RespBean getByMemberId(long id){
        List<Bar> list=focusBarService.getListByMemberId(id);
        return RespBean.success(list);
    }

    @ApiOperation("新增关注")
    @PostMapping("/barid")
    public RespBean doFocus(@RequestBody Map<String,Long> map){
        boolean res = focusBarService.doFocus(map);
        return RespBean.success(res);
    }

    @ApiOperation("取消关注")
    @DeleteMapping("/barid")
    public RespBean cancelFocus(Long barId){
        boolean res=focusBarService.cancelFocus(barId);
        return RespBean.success(res);
    }

    @ApiOperation("查询是否关注")
    @GetMapping("/barid")
    public RespBean checkFocus(long barId){
        long memberId = MemberIdHolder.getMemberId();
        QueryWrapper<FocusBar> wrapper=new QueryWrapper<>();
        wrapper.eq("member_id",memberId).eq("bar_id",barId);
        FocusBar res = focusBarService.getOne(wrapper);
        if(res==null){
            return RespBean.success(0);
        }else{
            return RespBean.success(1);
        }

    }

    @ApiOperation("根据barId统计关注者数量")
    @GetMapping("/count")
    public RespBean count(long barId){
        long res = focusBarService.doCount(barId);
        return RespBean.success(res);
    }


}
