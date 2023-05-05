package com.cyx.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.common.entity.Bar;
import com.cyx.common.entity.FocusBar;

import java.util.List;
import java.util.Map;

public interface FocusBarService extends IService<FocusBar> {
    List<Bar> getListByMemberId(long id);

    long doCount(long barId);

    boolean cancelFocus(Long barId);

    boolean doFocus(Map<String, Long> map);
}
