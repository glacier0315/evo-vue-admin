package com.glacier.common.utils;

import com.glacier.modules.sys.entity.User;
import com.glacier.modules.sys.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * security 工具类
 *
 * @author glacier
 * @version 1.0
 * date 2019-10-28 09:08
 */
public class SecurityUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtils.class);
    
    private static final UserMapper USER_MAPPER = SpringContextHolder.getBean(UserMapper.class);
    
    
    private SecurityUtils() {
    }
    
    /**
     * 获取用户id
     * @return
     */
    public static String geUserId() {
        String username = getUsername();
        if(!username.isEmpty()) {
            return Optional.ofNullable(USER_MAPPER.selectOneByUsername(username))
                    .map(User::getId)
                    .orElse("");
        }
        return "";
    }
    
    /**
     * 获取当前用户名
     *
     * @return 当前用户名
     */
    public static String getUsername() {
        return getAuthentication()
                .map(Principal::getName)
                .orElse("");
    }
    
    /**
     * 获取当前用户角色
     *
     * @return 当前用户角色
     */
    public static List<String> getRoles() {
        return getAuthentication()
                .map(authentication ->
                        authentication.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }
    
    /**
     * 获取 上下文
     *
     * @return
     */
    public static Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication);
    }
    
    
}
