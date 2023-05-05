package com.cyx.main.utils.key;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.common.entity.Content;
import com.cyx.main.to.ContentFavorTo;

public class ContentCacheKey {
    public static String getListToKey(ContentFavorTo to, Page<Content> page) {
        String key = "ContentListToCache:";
        if (to.getBarId() != null) {
            key += to.getBarId();
        } else {
            key += " ";
        }
        key += ":";
        if (to.getMemberId() != null) {
            key += to.getMemberId();
        } else {
            key += " ";
        }

        key += ":";
        if (to.getOrder() != null) {
            key += to.getOrder();
        } else {
            key += " ";
        }
        key += ":";


        key += page.getCurrent() + ":";
        key += page.getSize() + ":";
        return key;

    }

    public static String getListLabelKey(long label, long order, Page<Content> page) {
        String key = "ContentListLabelCache:";
        key += label + ":";
        key += order + ":";
        key += page.getCurrent() + ":";
        key += page.getSize();
        return key;
    }


    public static String getSingleKey(long id) {
        String key = "ContentSingleCache:";
        key += id;
        return key;
    }
}
