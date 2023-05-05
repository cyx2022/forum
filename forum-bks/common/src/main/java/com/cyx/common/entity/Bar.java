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
@TableName("bar")
@ApiModel("bar实体")
public class Bar implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	@TableId
	@ApiModelProperty("唯一主键")
	private Long id;
	/**
	 * 标签id
	 */
	@ApiModelProperty("标签id")
	private Long labelId;
	/**
	 * 名称
	 */
	@ApiModelProperty("bar名称")
	private String name;
	/**
	 * 创建者id
	 */
	@ApiModelProperty("创建者id")
	private Long builderId;

	/**
	 * 简介
	 */
	@ApiModelProperty("简介")
	private String tip;
	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private Date buildDate;
	/**
	 * 吧内人数
	 */
	@ApiModelProperty("bar内人数")
	private Long number;
	/**
	 * 删除标志
	 */
	@ApiModelProperty("删除标记")
	private Integer flag;

	@ApiModelProperty("头像url")
	private String img;


}
