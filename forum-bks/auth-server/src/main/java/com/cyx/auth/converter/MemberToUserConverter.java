package com.cyx.auth.converter;

import com.cyx.auth.entity.User;
import com.cyx.common.entity.Member;


public class MemberToUserConverter {
    static int a;
    private Integer id;
    private String username;
    private String password;
    public static void toUser(User user, Member member){
        user.setId(member.getId());
        user.setUsername(member.getName());
        user.setPassword(member.getPassword());
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
    }
    public static void toMember(User user, Member member){
        member.setId(user.getId());
        member.setName(user.getUsername());
        member.setPassword(user.getPassword());
    }
}
