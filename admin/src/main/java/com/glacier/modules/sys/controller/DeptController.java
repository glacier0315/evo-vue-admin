package com.glacier.modules.sys.controller;

import com.glacier.common.core.entity.Result;
import com.glacier.common.core.entity.dto.IdDto;
import com.glacier.common.core.factory.ResultBuildFactory;
import com.glacier.common.utils.SecurityUtils;
import com.glacier.modules.sys.entity.dto.dept.DeptForm;
import com.glacier.modules.sys.entity.dto.dept.DeptVo;
import com.glacier.modules.sys.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组织机构管理
 *
 * @author glacier
 * @version 1.0
 * date 2019-10-24 17:15
 */
@RestController
@RequestMapping(value = "/dept")
public class DeptController {
    private static final Logger log = LoggerFactory.getLogger(DeptController.class);
    private final DeptService deptService;

    @Autowired
    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    /**
     * 查询所有组织机构
     *
     * @return 组织机构
     */
    @GetMapping("/list")
    public Result<List<DeptVo>> list() {
        return ResultBuildFactory.ok(
                this.deptService.findAllList());
    }

    /**
     * 保存组织机构
     *
     * @param deptForm 组织机构表单
     * @return 响应
     */
    @PostMapping("/save")
    public Result<Integer> save(@RequestBody DeptForm deptForm) {
        return ResultBuildFactory.ok(
                this.deptService.save(deptForm));
    }

    /**
     * 删除指定组织机构
     *
     * @param idDto 主键封装
     * @return 响应
     */
    @PostMapping("/delete")
    public Result<Integer> delete(@RequestBody IdDto idDto) {
        return ResultBuildFactory.ok(
                this.deptService.delete(idDto.getId()));
    }

    /**
     * 查询所有组织机构树
     *
     * @return 组织机构树响应
     */
    @GetMapping("/findTree")
    public Result<List<DeptVo>> findTree() {
        String userId = SecurityUtils.geUserId();
        log.debug("userId: {}", userId);
        return ResultBuildFactory.ok(
                this.deptService.findTree(userId));
    }

    /**
     * 查询角色具有的单位
     *
     * @param roleId 角色id
     * @return 单位集合
     */
    @GetMapping("/findByRole")
    public Result<List<String>> findByRole(String roleId) {
        return ResultBuildFactory.ok(
                this.deptService.findByRole(roleId));
    }
}
