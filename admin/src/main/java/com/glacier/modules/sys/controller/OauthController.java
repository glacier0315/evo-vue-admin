package com.glacier.modules.sys.controller;

import com.glacier.common.core.entity.Result;
import com.glacier.common.core.entity.dto.UserDetailsDto;
import com.glacier.common.core.factory.ResultBuildFactory;
import com.glacier.common.utils.SecurityUtils;
import com.glacier.modules.sys.entity.dto.user.UserInfo;
import com.glacier.modules.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息 端点
 * @author glacier
 * @version 1.0
 * date 2020-05-24 18:27
 */
@RestController
@RequestMapping(value = "/oauth")
public class OauthController {
    private static final Logger log = LoggerFactory.getLogger(OauthController.class);
    private final UserService userService;

    @Autowired
    public OauthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取当前用户信息
     *
     * @return 当前用户信息
     */
    @GetMapping(value = "/userInfo")
    public Result<UserInfo> userInfo() {
        return ResultBuildFactory.ok(
                this.userService.findUserInfoByUsername(SecurityUtils.getUsername()));
    }

    /**
     * 根据用户名获取用户信息
     * 涉及到认证服务器登录调用，需排除权限
     *
     * @param username 用户名
     * @return 用户详情响应
     */
    @GetMapping(value = "/user")
    public Result<UserDetailsDto> findByUsername(
            String username) {
        return this.userService.loadUserByUsername(username);
    }
}
