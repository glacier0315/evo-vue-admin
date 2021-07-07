package com.glacier.modules.sys.controller;

import com.glacier.common.core.entity.Result;
import com.glacier.common.core.entity.dto.IdDto;
import com.glacier.common.core.factory.ResultBuildFactory;
import com.glacier.common.utils.SecurityUtils;
import com.glacier.modules.sys.entity.dto.menu.MenuForm;
import com.glacier.modules.sys.entity.dto.menu.MenuQuery;
import com.glacier.modules.sys.entity.dto.menu.MenuVo;
import com.glacier.modules.sys.entity.dto.menu.Router;
import com.glacier.modules.sys.service.MenuService;
import com.glacier.modules.sys.utils.RouteBuildFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 *
 * @author glacier
 * @version 1.0
 * date 2019-10-09 15:59
 */
@RestController
@RequestMapping(value = "/menu")
public class MenuController {
    private static final Logger log = LoggerFactory.getLogger(MenuController.class);
    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 查询所有菜单
     *
     * @return 菜单
     */
    @GetMapping("/list")
    public Result<List<MenuVo>> list() {
        return ResultBuildFactory.ok(
                this.menuService.findList(new MenuQuery()));
    }

    /**
     * 保存菜单
     *
     * @param menuForm 菜单
     * @return 保存响应
     */
    @PostMapping("/save")
    public Result<Integer> save(
            @RequestBody MenuForm menuForm) {
        return ResultBuildFactory.ok(
                this.menuService.save(menuForm));
    }

    /**
     * 删除指定菜单
     *
     * @param idDto 主键
     * @return 删除响应
     */
    @PostMapping("/delete")
    public Result<Integer> delete(@RequestBody IdDto idDto) {
        return ResultBuildFactory.ok(
                this.menuService.delete(idDto.getId()));
    }

    /**
     * 查询角色具有的菜单
     *
     * @param roleId 角色id
     * @return 菜单
     */
    @GetMapping("/findByRole")
    public Result<List<String>> findByRole(String roleId) {
        return ResultBuildFactory.ok(
                this.menuService.findByRole(roleId));
    }

    /**
     * 查询用户所有路由
     *
     * @return 路由
     */
    @GetMapping(value = "/getRouters")
    public Result<List<Router>> getRouters() {
        String userId = SecurityUtils.geUserId();
        log.debug("userId: {}", userId);
        return ResultBuildFactory.ok(
                RouteBuildFactory.buildRouters(
                        this.menuService.findMenuTreeByUserId(userId)));
    }

    /**
     * 查询所有权限标识
     *
     * @return 权限标识
     */
    @GetMapping(value = "/getPermissions")
    public Result<Set<String>> getPermissions() {
        String userId = SecurityUtils.geUserId();
        log.debug("userId: {}" , userId);
        return ResultBuildFactory.ok(
                this.menuService.findPermissionsByUserId(userId));
    }
}
