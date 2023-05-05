package com.cyx.main.service.impl;

import com.cyx.common.entity.Barstatus;
import com.cyx.main.mapper.BarStatusMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.cyx.main.service.BarStatusService;


@Service("barstatusService")
public class BarStatusServiceImpl extends ServiceImpl<BarStatusMapper, Barstatus> implements BarStatusService {


}