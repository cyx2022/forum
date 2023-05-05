package com.cyx.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 
 * @author cyx
 * @email 2906665963@qq.com
 * @date 2023-03-24 21:05:26
 */
@Data
@TableName("comment")
@ApiModel("一级评论实体")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	@TableId
	@ApiModelProperty("唯一主键")
	private Long id;
	/**
	 * 帖子id
	 */
	@ApiModelProperty("评论的内容id")
	private Long contentId;
	/**
	 * 用户id
	 */
	@ApiModelProperty("评论者id")
	private Long memberId;
	/**
	 * 评论内容
	 */
	@ApiModelProperty("评论文字内容")
	private String content;
	/**
	 * 图片
	 */
	@ApiModelProperty("评论的图片")
	private String urls;
	/**
	 * 评论时间
	 */
	@ApiModelProperty("评论时间")
	private Date time;
	/**
	 * 删除标志
	 */
	@ApiModelProperty("删除标记")
	private Integer flag;

}
