package com.cyx.main.service.impl.log;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.common.entity.log.OptLog;
import com.cyx.main.mapper.log.OptLogMapper;
import com.cyx.main.service.log.OptLogService;
import org.springframework.stereotype.Service;

@Service
public class OptLogServiceImpl extends ServiceImpl<OptLogMapper,OptLog> implements OptLogService{

}
