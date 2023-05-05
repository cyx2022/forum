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
@TableName("content")
@ApiModel("内容实体")
public class Content implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	@TableId
	@ApiModelProperty("唯一主键")
	private Long id;
	/**
	 * 发表者id
	 */
	@ApiModelProperty("发布者id")
	private Long memberId;

	/**
	 * bar id
	 */
	@ApiModelProperty("所属barid")
	private Long barId;
	/**
	 * 帖子类型 0 文字 1 图片 2 视频 3文字+图片 4 文字+视频
	 */
	@ApiModelProperty("内容包含的内容类型")
	private Long contentType;
	/**
	 * 存储路径
	 */
	@ApiModelProperty("发布的图片路径")
	private String url;

	/**
	 * 标题
	 */
	@ApiModelProperty("标题")
	private String title;
	/***
	 * 文字内容
	 */
	@ApiModelProperty("内容")
	private String text;
	/**
	 * 发布时间
	 */
	@ApiModelProperty("发布时间")
	private Date publishTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;
	/**
	 * 分享人数
	 */
	private Long shareCount;
	/**
	 * 收藏人数
	 */
	@ApiModelProperty("收藏人数")
	private Long storeCount;
	/**
	 * 评论人数
	 */
	@ApiModelProperty("评论人数")
	private Long commentCount;
	/**
	 * 删除标志
	 */
	@ApiModelProperty("删除标记")
	private Integer flag;

}
