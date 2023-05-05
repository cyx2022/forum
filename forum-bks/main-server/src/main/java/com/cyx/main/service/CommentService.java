package com.cyx.main.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.common.entity.Comment;
import com.cyx.main.to.CommentAddTo;

/**
 * 
 *
 * @author cyx
 * @email 2906665963@qq.com
 * @date 2023-03-24 21:05:26
 */
public interface CommentService extends IService<Comment> {

    Page<Comment> getByContentId(long contentId, Page<Comment> page);

    Comment addComment(CommentAddTo to);
}

