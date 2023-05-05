package com.cyx.point.controller;

import com.cyx.common.resp.RespBean;
import com.cyx.common.resp.RespBeanEnum;
import com.cyx.point.service.WelfareService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@ApiOperation("积分赠送相关")
@RestController
@RequestMapping("/point/welfare")
public class WelfareController {
    @Resource
    WelfareService welfareService;


    @ApiOperation("获取动态url")
    @GetMapping("/url")
    public RespBean getPagePointUrl(){
        String url = welfareService.getPagePointUrl();
        if(url.length()==0){
            return RespBean.error(RespBeanEnum.WELFARE_OVER);
        }
        return RespBean.success(url);
    }

    @ApiOperation("根据动态url抢积分")
    @GetMapping("/point/pass/{key}")
    public RespBean pagePointPass(@PathVariable("key") String key){
        long res = welfareService.pagePointPass(key);
        if(res==-1){
            return RespBean.success(RespBeanEnum.WELFARE_OVER);
        }
        return RespBean.success("获取积分成功，请注意系统消息查收");

    }
}
