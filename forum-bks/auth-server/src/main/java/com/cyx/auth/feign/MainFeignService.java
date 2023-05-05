package com.cyx.auth.feign;

import com.cyx.auth.to.MemberTo;
import com.cyx.common.entity.Member;
import com.cyx.common.resp.RespBean;
import com.cyx.common.to.MemberFavorTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@Component
@FeignClient("gateway-server")
public interface MainFeignService {

    @GetMapping(value = "/main/member/get",headers = {"relief=1"})
    RespBean getMember(@SpringQueryMap MemberFavorTo to);


    @PostMapping(value = "/main/member/add",headers = {"relief=1"})
    RespBean add(MemberTo memberTo);
}
