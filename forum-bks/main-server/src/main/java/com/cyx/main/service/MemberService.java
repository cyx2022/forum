package com.cyx.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.common.entity.Member;
import com.cyx.common.to.MemberFavorTo;
import com.cyx.common.to.MemberTo;
import com.cyx.main.to.MemberUpdateTo;

import javax.servlet.http.HttpServletRequest;

public interface MemberService extends IService<Member> {
    long add(MemberTo to);

    Member getMember(MemberFavorTo memberFavorTo,HttpServletRequest request);

    boolean toUpdate(MemberUpdateTo to);

    Member getMemberInner(MemberFavorTo memberFavorTo);
}
