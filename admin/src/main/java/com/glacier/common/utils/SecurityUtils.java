package com.glacier.common.utils;

import com.glacier.common.core.constant.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * security 工具类
 *
 * @author glacier
 * @version 1.0
 * date 2019-10-28 09:08
 */
public class SecurityUtils {
    private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);
    private SecurityUtils() {
    }

    /**
     * 获取当前用户ID
     *
     * @return 当前用户ID
     */
    public static String geUserId() {
        return (String) getClaims().get(CommonConstant.OAUTH_USER_ID);
    }

    /**
     * 获取当前用户名
     *
     * @return 当前用户名
     */
    public static String getUsername() {
        return (String) getClaims().get(CommonConstant.OAUTH_USERNAME);
    }

    /**
     * 获取令牌中的属性 键值对
     *
     * @return 令牌中的属性 键值对
     */
    public static Map<String, Object> getClaims() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication: {}", authentication);
        // TODO: 2021/7/6 待实现
        return new HashMap<>(4);
    }
}
