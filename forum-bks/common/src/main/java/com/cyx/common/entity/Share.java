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
@TableName("share")
public class Share implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	@TableId
	private Long id;
	/**
	 * 分享用户id
	 */
	private Long memberId;
	/**
	 * 帖子id
	 */
	private Long contentId;
	/**
	 * 最后分享时间
	 */
	private Date lastTime;
	/**
	 * 分享次数
	 */
	private Long count;

}
