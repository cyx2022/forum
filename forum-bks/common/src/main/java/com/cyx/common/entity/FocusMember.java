package com.cyx.common.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("focusMember")
@ApiModel("关注member列表")
public class FocusMember {
    @TableId
    @ApiModelProperty("唯一主键")
    private long id;
    @ApiModelProperty("关注者")
    private long fromId;
    @ApiModelProperty("被关注者")
    private long toId;
    @ApiModelProperty("关注时间")
    private Date focusTime;
}
