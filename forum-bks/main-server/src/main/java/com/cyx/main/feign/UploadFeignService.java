package com.cyx.main.feign;

import com.cyx.common.resp.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("gateway-server")
public interface UploadFeignService {
    @GetMapping(value = "/upload/img/check",headers = {"relief=1"})
    RespBean exist(@RequestParam("bucketName")String bucketName,@RequestParam("path") String path);

}
