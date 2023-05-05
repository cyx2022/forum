package com.cyx.common.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("由评论新增的积分消息实体")
public class PointMsg {

    @ApiModelProperty("一级评论id")
    private Long commentId;

    @ApiModelProperty("二级评论id")
    private Long subCommentId;

    @ApiModelProperty("发布时间")
    private Date publicTime;

    @ApiModelProperty("发送者id")
    private Long memberId;

    @ApiModelProperty("添加的积分")
    private Long point;

}
