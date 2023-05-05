package com.cyx.main.controller;


import com.cyx.common.resp.RespBean;
import com.cyx.main.service.FocusMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/main/focus/member")
public class FocusMemberController {
    @Autowired
    FocusMemberService focusMemberService;
    @PostMapping("/add")
    public RespBean doFocus(@RequestBody Map<String,Long> map){
        boolean res = focusMemberService.doFocus(map);
        return RespBean.success(res);
    }

    @GetMapping("/check")
    public RespBean check(Long id){
        boolean res = focusMemberService.check(id);
        return RespBean.success(res);
    }

}
