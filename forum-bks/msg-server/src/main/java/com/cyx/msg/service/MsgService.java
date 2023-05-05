package com.cyx.msg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.msg.entity.Msg;

import java.util.List;

public interface MsgService extends IService<Msg> {
    long getSystemMsgCount();

    long getPersonMsgCountAll();

    List<Msg> getSystemMsgUnreadList();

    List<Msg> getPersonMsgList(long senderId);

    long getPersonMsgCountPer(long senderId);

    List<Msg> getLatestHead();

    List<Msg> getSystemMsgLatestList();

    void signBatchMsg(List<String> list);

}
