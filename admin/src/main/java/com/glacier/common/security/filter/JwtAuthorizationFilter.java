package com.glacier.common.security.filter;

import com.glacier.common.security.provider.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * date 2021-07-15 10:10
 *
 * @author glacier
 * @version 1.0
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final static Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private final JwtTokenProvider jwtTokenProvider;
    
    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequestHeader(request);
        Authentication authentication = verefyToken(token);
        if (authentication != null) {
            LOGGER.info("token令牌验证成功, {}", authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 即便验证失败，也继续调用过滤链，匿名过滤器生成匿名令牌
        filterChain.doFilter(request, response);
    }
    
    
    /**
     * 从请求头获取token
     *
     * @param request
     * @return
     */
    private String getTokenFromRequestHeader(HttpServletRequest request) {
        String header = request.getHeader(JwtTokenProvider.TOKEN_HEADER);
        if (header == null || !header.startsWith(JwtTokenProvider.TOKEN_PREFIX)) {
            LOGGER.info("请求头不含JWT token， 调用下个过滤器");
            return "";
        }
        return header.replaceFirst(JwtTokenProvider.TOKEN_PREFIX, "");
    }
    
    /**
     * 验证token，并生成认证后的token
     *
     * @param token
     * @return
     */
    private UsernamePasswordAuthenticationToken verefyToken(String token) {
        if (token == null) {
            return null;
        }
        
        // 认证失败，返回null
        if (!jwtTokenProvider.verifyToken(token)) {
            return null;
        }
        // 提取用户名
        // 定义权限列表
        // 构建认证过的token
        return new UsernamePasswordAuthenticationToken(
                jwtTokenProvider.getUsername(token),
                null,
                jwtTokenProvider.getRoles(token)
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
    }
}
