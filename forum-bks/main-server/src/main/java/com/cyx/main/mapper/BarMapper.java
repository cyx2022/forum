package com.cyx.main.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.common.entity.Bar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyx.main.dto.BarDto;
import com.cyx.main.to.BarFavorTo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author cyx
 * @email 2906665963@qq.com
 * @date 2023-03-24 21:05:26
 */
@Mapper
public interface BarMapper extends BaseMapper<Bar> {

    BarDto getBar(long id);

    Page<BarDto> getList(Page<BarDto> page, BarFavorTo to);

    List<Bar> getListByMemberId(long id);

    Long getByContentId(Long contentId);

}
