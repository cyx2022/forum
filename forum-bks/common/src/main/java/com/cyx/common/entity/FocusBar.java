package com.cyx.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName("focusBar")
@ApiModel("关注bar列表")
@AllArgsConstructor
@NoArgsConstructor

public class FocusBar {
    @TableId
    @ApiModelProperty("唯一主键")
    private Long id;
    @ApiModelProperty("关注者id")
    private long memberId;
    @ApiModelProperty("被关注的bar id")
    private long barId;
    @ApiModelProperty("关注时间")
    private Date focusTime;
}
