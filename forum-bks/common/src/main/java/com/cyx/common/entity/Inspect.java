package com.cyx.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("inspect")
@ApiModel("举报消息实体")
public class Inspect {
    @TableId
    @ApiModelProperty("唯一主键")
    private long id;


    @ApiModelProperty("举报内容")
    private String bans;

    @ApiModelProperty("所在空间")
    private Long bar_id;

    @TableField(exist = false)
    @ApiModelProperty("接收前端的数据")
    private Integer[] banArr;

    @ApiModelProperty("举报者")
    private Long memberId;

    @ApiModelProperty("被举报项目类型")
    private Integer type;

    @ApiModelProperty("被举报项目id")
    private Long itemId;

    @ApiModelProperty("举报时间")
    private Date time;

    @ApiModelProperty("处理结果")
    private int result;

    @ApiModelProperty("删除标记")
    private int flag;
}
