package com.cyx.gateway.filter;

import com.cyx.common.exception.TokenException;
import com.cyx.common.resp.RespBeanEnum;
import com.cyx.common.utils.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//在gateway进行token校验,并设置memberId在head域
@Component
public class VerifyFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();
        HttpHeaders headers = request.getHeaders();
//       来自auth的鉴权数据请求
        if(headers.get("relief")!=null){
            return chain.filter(exchange);
        }
//        auth服务直接放行
        if(path.startsWith("/auth")){
            return chain.filter(exchange);
        }

//        TODO 去netty的请求，先独立处理
        List<String> ss=headers.get("Sec-WebSocket-Protocol");
        if(ss!=null){
            String accessToken=ss.get(0);
            boolean v = JwtUtil.verify(accessToken);
            if(v){
                ServerHttpRequest r = request.mutate().header("memberToken", accessToken).build();
                return chain.filter(exchange.mutate().request(r).build());
            }
        }

        List<String> accessList = headers.get("access_token");
        List<String> refreshList=headers.get("refresh_token");
        if (accessList!=null) {
            String accessToken=accessList.get(0);
            boolean v = JwtUtil.verify(accessToken);
            if (v) {
//                设置memberId在对应的memberId
//                headers.add("memberId",JwtUtil.resolveToken(accessToken));
                ServerHttpRequest r = request.mutate().header("memberId", JwtUtil.resolveToken(accessToken)).build();
                return chain.filter(exchange.mutate().request(r).build());
            } else {
//                    判断是否是进行token延期的请求,放行
                if(refreshList!=null){
                    chain.filter(exchange);
                }
//            token过期,进行token延期或者重新登录
                throw new TokenException(RespBeanEnum.TOKEN_EXPIRE);
            }
        } else {
//            无access_token,非法用户
            throw new TokenException(RespBeanEnum.TOKEN_NO);
        }
//        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
