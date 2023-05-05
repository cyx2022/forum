package com.cyx.search.feign;

import com.cyx.common.resp.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient("gateway-server")
public interface MainFeignService {

    @GetMapping(value = "/main/bar/feign",headers = {"relief=1"})
    RespBean getBar();

    @GetMapping(value = "/main/content/feign",headers = "relief=1")
    RespBean getContent();

    @GetMapping(value = "/main/bar/feignByIds",headers = "relief=1")
    RespBean fetchBarByIds(@RequestParam("ids") List<Long> ids);

    @GetMapping(value = "/main/content/feignByIds",headers = "relief=1")
    RespBean fetchContentByIds(@RequestParam("ids") List<Long> ids);
}
