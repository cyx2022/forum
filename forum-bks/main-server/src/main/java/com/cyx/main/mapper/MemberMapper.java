package com.cyx.main.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyx.common.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}
