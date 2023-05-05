package com.cyx.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

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
@TableName("point")
@ApiModel("积分信息实体")
public class Point implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	@TableId
	@ApiModelProperty("唯一主键")
	private Long id;
	/**
	 * 用户id
	 */
	@ApiModelProperty("积分拥有者id")
	private Long memberId;
	/**
	 * 持有积分
	 */
	@ApiModelProperty("持有的积分数量")
	private Long hold;
	/**
	 * 消耗积分
	 */
	@ApiModelProperty("消耗的积分数量")
	private Long consume;
	/**
	 * 删除标志
	 */
	@ApiModelProperty("删除标记")
	private Integer flag;

}
