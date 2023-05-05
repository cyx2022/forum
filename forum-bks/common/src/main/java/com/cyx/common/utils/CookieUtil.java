package com.cyx.common.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class CookieUtil {
    public static void setCookie(HttpServletResponse response, String cookieKey, String cookieValue, int cookieMaxAge){
        Cookie cookie = new Cookie(cookieKey, cookieValue);
        cookie.setMaxAge(cookieMaxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String cookieKey){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(cookieKey)) {
                    return cookies[i].getValue();
                }
            }
        }
        return null;


    }
}
