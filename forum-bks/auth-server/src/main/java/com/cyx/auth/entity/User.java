package com.cyx.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;//账户是否激活
    private Boolean accountNonExpired;//账户是否过期
    private Boolean accountNonLocked; //账户是否被锁定
    private Boolean credentialsNonExpired;//密码是否过期
//    private List<Role> roles = new ArrayList<>();//关系属性 用来存储当前用户所有角色信息


//    暂时不做权限
    //返回权限信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        roles.forEach(role -> {
//            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getName());
//            authorities.add(simpleGrantedAuthority);
//        });
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = true;
    }
    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = true;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = true;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = true;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }



    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }



    @Override
    public boolean isEnabled() {
        return true;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public List<Role> getRoles() {
//        return roles;
//    }

//    public void setRoles(List<Role> roles) {
//        this.roles = roles;
//    }
}
