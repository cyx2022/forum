package com.cyx.common.utils;


import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号码校验类
 *
 * @author: LC
 * @date 2022/3/2 2:16 下午
 * @ClassName: ValidatorUtil
 */
public class ValidatorUtil {

    private static final Pattern mobile_patten = Pattern.compile("[1]([3-9])[0-9]{9}$");
    private static final Pattern url_patten=Pattern.compile("^(http|https):\\/\\/[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,}(\\/\\S*)?$");

    /**
     * 手机号码校验
     * @author LC
     * @operation add
     * @date 2:19 下午 2022/3/2
     * @param mobile
     * @return boolean
     **/
    public static boolean isMobile(String mobile) {
        Matcher matcher = mobile_patten.matcher(mobile);
        return matcher.matches();
    }

    public static boolean isUrl(String url){
        Matcher matcher=url_patten.matcher(url);
        return matcher.matches();
    }
}
