package com.cyx.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.common.entity.FocusMember;

import java.util.Map;

public interface FocusMemberService extends IService<FocusMember> {
    boolean doFocus(Map<String,Long> map);

    boolean check(Long id);
}
