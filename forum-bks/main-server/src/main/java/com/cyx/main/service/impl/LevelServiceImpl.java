package com.cyx.main.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.common.entity.Level;
import com.cyx.main.mapper.LevelMapper;
import com.cyx.main.service.LevelService;
import org.springframework.stereotype.Service;

@Service
public class LevelServiceImpl extends ServiceImpl<LevelMapper, Level> implements LevelService {
}
