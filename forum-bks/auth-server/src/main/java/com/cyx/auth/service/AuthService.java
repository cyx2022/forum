package com.cyx.auth.service;

import com.cyx.auth.to.MemberTo;
import com.cyx.common.entity.Member;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    Long register(MemberTo member, HttpServletRequest request);

}
