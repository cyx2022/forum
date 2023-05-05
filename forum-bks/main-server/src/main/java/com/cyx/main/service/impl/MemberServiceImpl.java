package com.cyx.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.common.entity.Member;
import com.cyx.common.exception.GlobalException;
import com.cyx.common.resp.RespBeanEnum;
import com.cyx.common.to.MemberFavorTo;
import com.cyx.common.to.MemberTo;
import com.cyx.main.coderc.PasswordEncoder;
import com.cyx.main.constant.RedisHeaderConstant;
import com.cyx.main.constant.RedisTimeOutConstant;
import com.cyx.common.holder.MemberIdHolder;
import com.cyx.main.mapper.MemberMapper;
import com.cyx.main.service.MemberService;
import com.cyx.main.to.MemberUpdateTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    RedisScript<Boolean> storeImage;

    @Override
    public long add(MemberTo to) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Member member = new Member();
        member.setId(null);
        member.setName(to.getUsername());
        member.setPassword(bCryptPasswordEncoder.encode(to.getPassword()));
        member.setSex(1);
        member.setEmail("");
        member.setPhone("");
        member.setLevelId(0);
        member.setAvatar("");
        member.setLastLogin(new Date());
        member.setGiteeToken("");
        member.setWxToken("");
        member.setFlag(0);
        this.save(member);
        return member.getId();
    }

    @Override
    public Member getMember(MemberFavorTo memberFavorTo, HttpServletRequest request) {
        String key=null;
//        来自网关的请求不能走缓存
        if (!"1".equals(request.getHeader("relief"))) {
            key = RedisHeaderConstant.getMemberKey(memberFavorTo.getId());
            Member member = (Member) redisTemplate.opsForValue().get(key);
            if (member != null) {
                return member;
            }
        }
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq(!StringUtils.isEmpty(memberFavorTo.getUsername()), "name", memberFavorTo.getUsername()).
                or().eq(!StringUtils.isEmpty(memberFavorTo.getId()), "id", memberFavorTo.getId()).
                or().eq(!StringUtils.isEmpty(memberFavorTo.getPhone()), "phone", memberFavorTo.getPhone());
        Member member = this.getOne(wrapper);
//        不是来自 auth 的请求,隐藏密码
        if (!"1".equals(request.getHeader("relief"))) {
            member.setPassword("");
            redisTemplate.opsForValue().set(key,member,RedisTimeOutConstant.MEMBER_TIMEOUT, TimeUnit.SECONDS);
        }
        return member;
    }

    @Override
    public Member getMemberInner(MemberFavorTo memberFavorTo) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq(!StringUtils.isEmpty(memberFavorTo.getUsername()), "name", memberFavorTo.getUsername()).
                or().eq(!StringUtils.isEmpty(memberFavorTo.getId()), "id", memberFavorTo.getId()).
                or().eq(!StringUtils.isEmpty(memberFavorTo.getPhone()), "phone", memberFavorTo.getPhone());
        Member member = this.getOne(wrapper);
        return member;
    }

    @Override
    public boolean toUpdate(MemberUpdateTo to) {
        long memberId = MemberIdHolder.getMemberId();
        MemberFavorTo favor = new MemberFavorTo();
        favor.setUsername(to.getOldName());
        Member original = getMemberInner(favor);
//        密码相同
        boolean res = false;
        if (original.getPassword().equals(PasswordEncoder.encoder.encode(to.getOldPassword()))) {
            //判断图片是否过期
            boolean imageFlag = holdImage(to.getAvatar());
            QueryWrapper<Member> wrapper = new QueryWrapper<>();
            wrapper.eq("id", memberId);
            Member member = getOne(wrapper);
            member.setName(to.getNewName());
            member.setPassword(PasswordEncoder.encoder.encode(to.getNewPassword()));
            member.setSex(to.getSex());
            member.setAvatar(to.getAvatar());
            member.setPhone(to.getPhone());
            member.setEmail(to.getEmail());
            res = this.update(member, wrapper);
            if(res){
                String key = RedisHeaderConstant.getMemberKey(memberId);
                redisTemplate.delete(key);
            }
        } else {
//            密码错误
            throw new GlobalException(RespBeanEnum.PASSWORD_ERROR);
        }
        return res;
    }

//    查看图片是否过期删除
    public boolean holdImage(String key){
        List<String> list=new ArrayList<>();
        list.add(key);
        boolean res = (Boolean) redisTemplate.execute(storeImage,list, Collections.EMPTY_LIST);
        return res;
    }
}
