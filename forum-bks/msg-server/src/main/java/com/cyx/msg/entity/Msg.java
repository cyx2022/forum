package com.cyx.msg.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("msg")
@ApiModel("消息实体")
public class Msg {
    @ApiModelProperty("唯一主键")
    private Long id;

    @ApiModelProperty("发送者id")
    private Integer senderId;

    @ApiModelProperty("接收者id")
    private Integer receiverId;

    @ApiModelProperty("发送时间")
    private Date time;

    @ApiModelProperty("签收标志")
    private Integer sign;

    @ApiModelProperty("删除标志")
    private Integer flag;

    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("内容")
    private String content;
}
