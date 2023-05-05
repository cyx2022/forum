package com.cyx.main.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.common.entity.Ban;
import com.cyx.main.mapper.BanMapper;
import com.cyx.main.service.BanService;
import org.springframework.stereotype.Service;

@Service
public class BanServiceImpl extends ServiceImpl<BanMapper, Ban> implements BanService {
}
