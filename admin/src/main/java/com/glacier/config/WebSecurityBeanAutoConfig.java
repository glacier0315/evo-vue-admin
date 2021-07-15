package com.glacier.config;

import com.glacier.common.security.filter.JwtAuthorizationFilter;
import com.glacier.common.security.handler.CustomAccessDeniedHandler;
import com.glacier.common.security.handler.CustomAuthenticationEntryPoint;
import com.glacier.common.security.handler.CustomAuthenticationSuccessHandler;
import com.glacier.common.security.handler.CustomLogoutSuccessHandler;
import com.glacier.common.security.provider.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * date 2021-07-15 10:37
 *
 * @author glacier
 * @version 1.0
 */
@Configuration
public class WebSecurityBeanAutoConfig {
    /**
     * 登录成功处理器
     * @param jwtTokenProvider
     * @return
     */
    @Bean
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        return new CustomAuthenticationSuccessHandler(jwtTokenProvider);
    }
    
    /**
     * 令牌过滤器
     * @param jwtTokenProvider
     * @return
     */
    @Bean
    JwtAuthorizationFilter jwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider){
        return new JwtAuthorizationFilter(jwtTokenProvider);
    }
    
    /**
     * 自定义权限不足异常
     *
     * @return 自定义权限不足异常处理类
     */
    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
    
    /**
     * 自定义需要授权异常
     *
     * @return 自定义需要授权异常处理类
     */
    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }
    
    /**
     * 自定义退出处理类
     *
     * @return 自定义退出处理类
     */
    @Bean
    CustomLogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }
}
