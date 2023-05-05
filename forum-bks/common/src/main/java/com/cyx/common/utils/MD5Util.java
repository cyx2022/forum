package com.cyx.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;



/**
 * MD5工具类
 * 乐字节：专注线上IT培训
 * 答疑老师微信：lezijie
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Component
public class MD5Util {

	public static String md5(String src){
		return DigestUtils.md5Hex(src);
	}


	public static String inputPassToFromPass(String inputPass,String salt){
		String str = "" +salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
		return md5(str);
	}

	public static String formPassToDBPass(String formPass,String salt){
		String str = "" +salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
		return md5(str);
	}

	public static String inputPassToDBPass(String inputPass,String salt){
		String fromPass = inputPassToFromPass(inputPass,salt);
		String dbPass = formPassToDBPass(fromPass, salt);
		return dbPass;
	}


	public static void main(String[] args) {
		// d3b1294a61a07da9b49b6e22b2cbd7f9
		System.out.println(inputPassToFromPass("123456","123456"));
		System.out.println(formPassToDBPass("d3b8d9e2e41c8c52100e44799e8862a0","123456"));
		System.out.println(inputPassToDBPass("123456","123456"));
	}

}