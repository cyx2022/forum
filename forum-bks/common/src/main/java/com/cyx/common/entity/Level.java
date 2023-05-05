package com.cyx.common.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("level")
public class Level {
    @TableId
    private long id;
    private String content;
    private String value;
    private int flag;
}
