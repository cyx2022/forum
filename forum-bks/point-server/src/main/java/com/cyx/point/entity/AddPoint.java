package com.cyx.point.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@TableName("addPoint")
public class AddPoint {
    @TableId
    private Long id;
    private Long commentId;

    @TableField("subComment_id")
    private Long subCommentId;

    private Date publicTime;
}
