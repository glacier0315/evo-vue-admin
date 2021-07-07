package com.glacier.modules.sys.controller;

import com.glacier.common.core.constant.CommonConstant;
import com.glacier.common.core.constant.MediaConstants;
import com.glacier.common.core.entity.Result;
import com.glacier.common.core.entity.dto.IdDto;
import com.glacier.common.core.entity.page.PageRequest;
import com.glacier.common.core.entity.page.PageResponse;
import com.glacier.common.core.factory.ResultBuildFactory;
import com.glacier.common.utils.SecurityUtils;
import com.glacier.modules.sys.entity.dto.user.*;
import com.glacier.modules.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 用户管理
 *
 * @author glacier
 * @version 1.0
 * date 2019-08-04 22:13
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 分页查询用户
     *
     * @param pageRequest 分页请求
     * @return 分页响应
     */
    @PostMapping("/findPage")
    public Result<PageResponse<UserDto>> findPage(
            @RequestBody PageRequest<UserQuery> pageRequest) {
        return ResultBuildFactory.ok(
                this.userService.findPage(pageRequest));
    }

    /**
     * 保存用户
     *
     * @param userAddForm 用户
     * @return 保存响应
     */
    @PostMapping("/add")
    public Result<Integer> add(
            @RequestBody UserAddForm userAddForm) {
        return ResultBuildFactory.ok(
                this.userService.save(userAddForm));
    }

    /**
     * 更新用户
     *
     * @param userDto 用户更新实体
     * @return 更新结果
     */
    @PostMapping("/update")
    public Result<Integer> update(
            @RequestBody UserDto userDto) {
        return ResultBuildFactory.ok(
                this.userService.save(userDto));
    }

    /**
     * 删除指定用户
     *
     * @param idDto 主键
     * @return 删除响应
     */
    @PostMapping("/delete")
    public Result<Integer> delete(@RequestBody IdDto idDto) {
        return ResultBuildFactory.ok(
                this.userService.delete(idDto.getId()));
    }

    /**
     * 获取当前用户信息
     *
     * @return 当前用户信息
     */
    @GetMapping(value = "/profile")
    public Result<UserProfile> getProfile() {
        return ResultBuildFactory.ok(
                this.userService.findUserProfileByUsername(SecurityUtils.getUsername()));
    }

    /**
     * 更新当前用户信息
     *
     * @param userProfileForm 当前用户信息
     * @return 更新响应
     */
    @PostMapping(value = "/profile")
    public Result<Integer> updateProfile(
            @RequestBody UserProfileForm userProfileForm) {
        return this.userService.updateProfile(userProfileForm);
    }

    /**
     * 更新当前用户头像信息
     *
     * @param userAvatarForm 当前用户头像信息
     * @return 更新响应
     */
    @PostMapping(value = "/avatar")
    public Result<Integer> avatar(
            @RequestBody UserAvatarForm userAvatarForm) {
        return this.userService.updateAvatar(userAvatarForm);
    }

    /**
     * 重置用户密码
     *
     * @param passwordResetForm 用户密码
     * @return 重置响应
     */
    @PostMapping("/resetPwd")
    public Result<Integer> resetPwd(
            @RequestBody UserPasswordResetForm passwordResetForm) {
        // 设置重置密码
        return this.userService.resetPassword(passwordResetForm);
    }

    /**
     * 修改用户密码
     *
     * @param userPasswordForm 用户密码
     * @return 修改用户密码响应
     */
    @PostMapping("/updatePwd")
    public Result<Integer> updatePwd(
            @RequestBody UserPasswordForm userPasswordForm) {
        return this.userService.updatePassword(userPasswordForm);
    }

    /**
     * 检验用户名
     *
     * @param userDto 用户封装实体类
     * @return 校验响应
     */
    @PostMapping("/checkUsername")
    public Result<Boolean> checkUsername(
            @RequestBody UserDto userDto) {
        return ResultBuildFactory.ok(this.userService.checkUsername(userDto));
    }

    /**
     * 检验用户身份证号
     *
     * @param userDto 用户封装实体类
     * @return 校验响应
     */
    @PostMapping("/checkIdCard")
    public Result<Boolean> checkIdCard(
            @RequestBody UserDto userDto) {
        return ResultBuildFactory.ok(this.userService.checkIdCard(userDto));
    }

    /**
     * 导出用户
     *
     * @param userQuery 用户参数
     * @param response  请求响应
     * @throws IOException IO异常
     */
    @PostMapping("/export")
    public void export(
            @RequestBody UserQuery userQuery,
            HttpServletResponse response) throws IOException {
        response.setContentType(MediaConstants.APPLICATION_VND_MS_EXCEL);
        response.setCharacterEncoding(CommonConstant.CHARSET_UTF_8);
        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("用户", CommonConstant.CHARSET_UTF_8);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        // TODO: 2021/4/20 导出用户
        // this.userService.findList(userQuery)
    }
}
