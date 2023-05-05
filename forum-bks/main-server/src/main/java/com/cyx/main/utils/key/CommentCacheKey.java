package com.cyx.main.utils.key;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.common.entity.Comment;

public class CommentCacheKey {
    public static String COMMENT_CACHE_HEAD="comment:";

    public static String getListKey(long contentId, Page<Comment> page){
        String key=COMMENT_CACHE_HEAD;
        key+=contentId;
        key+=page.getCurrent();
        key+=page.getSize();
        return key;
    }
}
