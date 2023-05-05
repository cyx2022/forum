package com.cyx.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//验证码公共返回对象
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgResult {
    // 该验证码对应的UUID
    private String imgUUID;
    // 该验证码对应的 base64 加密格式
    private String img;
    // 该验证码对应的具体的 码
    private String code;
}

