package com.glacier.modules.sys.controller;

import com.glacier.common.core.entity.Result;
import com.glacier.common.core.entity.dto.IdDto;
import com.glacier.common.core.entity.page.PageRequest;
import com.glacier.common.core.entity.page.PageResponse;
import com.glacier.common.core.factory.ResultBuildFactory;
import com.glacier.modules.sys.entity.dto.role.RoleForm;
import com.glacier.modules.sys.entity.dto.role.RoleQuery;
import com.glacier.modules.sys.entity.dto.role.RoleVo;
import com.glacier.modules.sys.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 *
 * @author glacier
 * @version 1.0
 * date 2019-08-11 21:24
 */
@RestController
@RequestMapping(value = "/role")
public class RoleController {
    private static final Logger log = LoggerFactory.getLogger(RoleController.class);
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 查找所有角色
     *
     * @return 角色
     */
    @GetMapping("/findAll")
    public Result<List<RoleVo>> findAll() {
        return ResultBuildFactory.ok(
                this.roleService.findAllList());
    }

    /**
     * 分页查询角色
     *
     * @param pageRequest 分页请求
     * @return 分页响应
     */
    @PostMapping("/findPage")
    public Result<PageResponse<RoleVo>> findPage(
            @RequestBody PageRequest<RoleQuery> pageRequest) {
        return ResultBuildFactory.ok(
                this.roleService.findPage(pageRequest));
    }

    /**
     * 保存角色 （新增或者更新）
     *
     * @param roleForm 角色
     * @return 保存响应
     */
    @PostMapping("/save")
    public Result<Integer> save(
            @RequestBody RoleForm roleForm) {
        return ResultBuildFactory.ok(
                this.roleService.save(roleForm));
    }

    /**
     * 删除指定角色
     *
     * @param idDto 主键
     * @return 删除响应
     */
    @PostMapping("/delete")
    public Result<Integer> delete(@RequestBody IdDto idDto) {
        return ResultBuildFactory.ok(
                this.roleService.delete(idDto.getId()));
    }

    /**
     * 检验角色编码
     *
     * @param roleForm  角色
     * @return 校验响应
     */
    @PostMapping("/checkCode")
    public Result<Boolean> checkCode(
            @RequestBody RoleForm roleForm) {
        return ResultBuildFactory.ok(this.roleService.checkCode(roleForm));
    }

    /**
     * 根据用户id查询角色
     *
     * @param userId 用户id
     * @return 角色
     */
    @GetMapping("/findByUserId")
    public Result<List<RoleVo>> findByUserId(String userId) {
        return ResultBuildFactory.ok(
                this.roleService.findByUserId(userId));
    }
}
