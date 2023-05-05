package com.cyx.main.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.common.entity.Content;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyx.main.dto.ContentDto;
import com.cyx.main.to.ContentFavorTo;
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
public interface ContentMapper extends BaseMapper<Content> {

    Page<ContentDto> getList(ContentFavorTo to, Page<Content> page);

    Page<ContentDto> getByLabelId(long label, String order, Page<Content> page);

    Page<ContentDto> recommend(Long barId, Page<Content> page);

    Page<ContentDto> getByOwner(Long id, Page<Content> page);
}
