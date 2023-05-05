package com.cyx.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("subComment")
@ApiModel("二级评论实体")
public class SubComment {
    @TableId
    @ApiModelProperty("唯一主键")
    private Long id;
    @ApiModelProperty("被评论的一级评论id")
    private Long commentId;
    @ApiModelProperty("二级评论发布者")
    private Long fromMemberId;
    @ApiModelProperty("二级评论被评论者")
    private Long toMemberId;
    @ApiModelProperty("二级评论的内容")
    private String content;
    @ApiModelProperty("评论时间")
    private Date time;
    @ApiModelProperty("删除标记")
    private Integer flag;
}
