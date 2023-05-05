package com.cyx.auth.service.securiity;

import com.alibaba.fastjson.TypeReference;
import com.cyx.auth.entity.User;
import com.cyx.auth.exception.NoSuchUserException;
import com.cyx.auth.feign.MainFeignService;
import com.cyx.common.entity.Member;
import com.cyx.auth.converter.MemberToUserConverter;
import com.cyx.common.to.MemberFavorTo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AuthUserDetailService implements UserDetailsService {
    @Resource
    MainFeignService memberFeignService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberFavorTo memberFavorTo=new MemberFavorTo();
        memberFavorTo.setUsername(username);
        Member member=memberFeignService.getMember(memberFavorTo).getObject(new TypeReference<Member>(){});
        System.out.println(member);
        if(member==null){
//            抛出自定义异常
            throw new NoSuchUserException("该用户不存在");
        }
        User user=new User();
        MemberToUserConverter.toUser(user,member);
        return user;
    }
}
