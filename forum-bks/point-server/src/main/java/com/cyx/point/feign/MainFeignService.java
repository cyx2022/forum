package com.cyx.point.feign;

import com.cyx.common.resp.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Component
@FeignClient("gateway-server")
public interface MainFeignService {

    @GetMapping(value = "/main/comment/get",headers = {"relief=1"})
    RespBean getComment(@RequestParam("id") Long id);

    @GetMapping(value = "/main/subComment/get",headers = {"relief=1"})
    RespBean getSubComment(@RequestParam("id") Long id);

}
