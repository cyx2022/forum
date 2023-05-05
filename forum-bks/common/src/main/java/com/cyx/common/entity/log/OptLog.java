package com.cyx.common.entity.log;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("optLog")
public class OptLog {
    @TableId
    private Long id;
    long memberId;
    String ip;
    String module;
    String operator;
    String url;
    Date time;
    long cost;
    String args;

}
