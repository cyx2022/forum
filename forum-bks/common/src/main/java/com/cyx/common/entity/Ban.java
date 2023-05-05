package com.cyx.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ban")
@ApiModel("禁止实体")
public class Ban {
    @TableId
    @ApiModelProperty("唯一主键")
    private Integer id;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("发布时间")
    private Date time;

}
