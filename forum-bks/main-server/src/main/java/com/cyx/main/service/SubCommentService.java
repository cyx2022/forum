package com.cyx.main.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.common.entity.SubComment;
import com.cyx.main.to.SubCommentAddTo;

public interface SubCommentService extends IService<SubComment> {
    Page<SubComment> getByCommentId(long commentId, Page<SubComment> page);

    SubComment addSubComment(SubCommentAddTo to);
}
