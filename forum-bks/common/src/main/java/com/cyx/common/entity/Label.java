package com.cyx.common.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("label")
@ApiModel("标签实体")
public class Label {
    @TableId
    @ApiModelProperty("唯一主键")
    private long id;
    @ApiModelProperty("标签名")
    private String name;
    @ApiModelProperty("标签的bar数")
    private long count;
    @ApiModelProperty("删除标记")
    private int flag;
}
