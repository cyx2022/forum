package com.cyx.main.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.cyx.main.mapper.StatusMapper;
import com.cyx.common.entity.Status;
import com.cyx.main.service.StatusService;


@Service("statusService")
public class StatusServiceImpl extends ServiceImpl<StatusMapper, Status> implements StatusService {


}