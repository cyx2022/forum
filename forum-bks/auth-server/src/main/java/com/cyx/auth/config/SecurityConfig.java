package com.cyx.auth.config;


import com.cyx.auth.filter.LoginFilter;
import com.cyx.auth.handler.AuthLoginSuccessHandler;
import com.cyx.auth.handler.AuthLogoutSuccessHandler;
import com.cyx.auth.handler.SecurityHandler;
import com.cyx.auth.service.securiity.AuthUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.type.MethodsMetadata;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.lang.reflect.Method;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    AuthLogoutSuccessHandler authLogoutSuccessHandler;

    @Autowired
    SecurityHandler securityHandler;

    @Autowired
    private AuthUserDetailService authUserDetailService;


    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(authUserDetailService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/auth/login");
        loginFilter.setUsernameParameter("username");
        loginFilter.setPasswordParameter("password");
        loginFilter.setKaptcha("code");
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        loginFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return loginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()

                .antMatchers("/auth/captcha")
                .permitAll()
                .and()

                .authorizeRequests()
                .antMatchers("/auth/test")
                .permitAll()
                .and()

                .authorizeRequests()
                .antMatchers("/auth/sign")
                .permitAll()

                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
//                .and()
//                .exceptionHandling()
////                认证异常处理
//                .authenticationEntryPoint(securityHandler)
                .and()
                .logout()
                .logoutUrl("/auth/logout")
//                退出成功处理器
                .logoutSuccessHandler(authLogoutSuccessHandler)
                .and()
                .csrf()
                .disable();



        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
