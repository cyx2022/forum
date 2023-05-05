package com.cyx.auth.service.common;

import com.cyx.auth.utils.CaptchaUtil;
import com.cyx.common.utils.CookieUtil;
import com.cyx.common.utils.ImgResult;
import com.cyx.common.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaService {
    @Value("${captchaLimit}")
    int captchaLimit;

    @Value("${expireLimit}")
    int expireLimit;

    //    验证码前缀
    final String expireHead="expire:";

    @Autowired
    RedisTemplate redisTemplate;
    public ImgResult getCaptcha(HttpServletRequest request, HttpServletResponse response,String addr){
        //画图工具类
        CaptchaUtil imageCode = new CaptchaUtil();
        // 获取验证码对应的 base64  编码
        String base64 = CaptchaUtil.getBase64(imageCode.getImage());

        // 获取对应的 验证码 code
        String code = imageCode.getCode();

        // 生成 UUID
        String imgUUID = UUIDUtil.uuid();

        // 封装 获取的 验证码相关的数据 到 验证码对象中，并响应
        ImgResult imgResult = new ImgResult();
        imgResult.setImgUUID(imgUUID);
        imgResult.setImg(base64);
        imgResult.setCode(code);


        // 将验证码的信息保存到 redis中,并设置 有效时间！
        redisTemplate.opsForValue().set(imgUUID, code, captchaLimit, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(expireHead+addr, code, expireLimit, TimeUnit.SECONDS);
//        生成uuid 得ThreadLocal由于后续登录
        // 将封装好的验证码对象响应给前端
        return imgResult;
    }
}
