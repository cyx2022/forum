package com.cyx.main.utils.key;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.main.dto.BarDto;
import com.cyx.main.to.BarFavorTo;

//设置bar缓存的key
public class BarCacheKey {
    public static String getListKey(BarFavorTo to, Page<BarDto> page){
        String key="BarListCache:";
        if(to.getLabelId()!=null){
            key+=to.getLabelId();
        }else{
           key+=" ";
        }
        key+=":";

        if(to.getBuilderId()!=null){
            key+=to.getBuilderId();
        }else{
            key+=" ";
        }

        key+=":";

        if(to.getOrder()!=null){
            key+=to.getOrder();
        }else{
            key+=" ";
        }

        key+=":";

        key+=page.getCurrent()+":";
        key+=page.getSize();

        return key;
    }

    public static String getSingleKey(long id){
        String key="BarSingleCache:";
        key+=id;

        return key;
    }
}
