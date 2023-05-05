package com.cyx.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author cyx
 * @email 2906665963@qq.com
 * @date 2023-03-24 21:05:26
 */
@Data
@TableName("barstatus")
public class Barstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	@TableId
	private Long id;
	/**
	 * 吧id
	 */
	private Long barId;
	/**
	 * 人员id
	 */
	private Long memberId;
	/**
	 * 成员具体状态
	 */
	private Integer statusId;
	/**
	 * 入吧时间
	 */
	private Date joinTime;
	/**
	 * 删除标志
	 */
	private Integer flag;

}
