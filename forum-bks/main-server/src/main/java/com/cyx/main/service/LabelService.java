package com.cyx.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.common.entity.Label;

import java.util.List;

public interface LabelService extends IService<Label> {
    List<Label> getList();

}
