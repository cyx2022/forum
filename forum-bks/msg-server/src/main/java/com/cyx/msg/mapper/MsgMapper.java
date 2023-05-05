package com.cyx.msg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyx.msg.entity.Msg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MsgMapper extends BaseMapper<Msg> {
    List<Msg> getLatestHead(long receiverId);

    void signBatchMsg(List<String> list);
}
