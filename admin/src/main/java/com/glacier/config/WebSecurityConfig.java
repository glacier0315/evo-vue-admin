package com.glacier.config;

import com.glacier.common.security.filter.JwtAuthorizationFilter;
import com.glacier.common.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * security配置
 *
 * @author glacier
 * @version 1.0
 * date 2019-08-04 10:03
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    /**
     * 用户服务类
     */
    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * 配置
     */
    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 令牌过滤器
     */
    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;
    /**
     * 登录成功处理器
     */
    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
    /**
     * 权限拒绝
     */
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    /**
     * 自定义授权异常
     */
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    
    
    /**
     * 配置静态资源拦截问题
     *
     * @param web web层安全
     * @throws Exception 异常
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/favicon.ico", "/error", "/static/**", "/webjars/**", "/css/**", "/js/**", "/fonts/**");
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(authorize -> {
                    authorize
                            .antMatchers(this.securityProperties.permitAll2Array())
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                // 配置session
                .sessionManagement((sessionManagement) -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // 登录
                .formLogin(formLogin -> {
                    formLogin.successHandler(authenticationSuccessHandler)
                            .permitAll();
                })
                // 配置异常处理
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.authenticationEntryPoint(authenticationEntryPoint)
                            .accessDeniedHandler(accessDeniedHandler);
                })
                // 添加自定义过滤器
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic();
        
    }
    
    /**
     * 授权中心管理器，解决依赖注入问题
     *
     * @return 认证管理器
     * @throws Exception 异常
     */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    /**
     * 密码工具类
     *
     * @return 密码工具类
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
