package com.glacier.modules.sys.service.impl;

import com.glacier.common.core.constant.SysConstants;
import com.glacier.common.core.utils.TreeBuildFactory;
import com.glacier.modules.sys.convert.DeptConvert;
import com.glacier.modules.sys.entity.Dept;
import com.glacier.modules.sys.entity.User;
import com.glacier.modules.sys.entity.dto.dept.DeptForm;
import com.glacier.modules.sys.entity.dto.dept.DeptVo;
import com.glacier.modules.sys.mapper.DeptMapper;
import com.glacier.modules.sys.mapper.RoleDeptMapper;
import com.glacier.modules.sys.mapper.UserMapper;
import com.glacier.modules.sys.service.DeptService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hebin
 * @version 1.0
 * date 2019-10-24 17:12
 */
@Transactional(readOnly = true)
@Service("deptService")
public class DeptServiceImpl implements DeptService {
    private static final Logger log = LoggerFactory.getLogger(DeptServiceImpl.class);
    @Autowired
    private DeptConvert deptConvert;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleDeptMapper roleDeptMapper;
    
    /**
     * 查找所有 组织机构
     *
     * @return 组织机构
     */
    @Override
    public List<DeptVo> findAllList() {
        return this.deptConvert.toDeptVo(
                this.deptMapper.selectAll());
    }
    
    /**
     * 根据用户ID查找所有 组织机构
     *
     * @param userId 用户ID
     * @return 组织机构
     */
    @Override
    public List<DeptVo> findListByUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            return new ArrayList<>(1);
        }
        if (SysConstants.SYS_USER_ID.equals(userId)) {
            return this.findAllList();
        }
        return this.deptConvert.toDeptVo(
                this.deptMapper.findByUserId(userId));
    }
    
    /**
     * 保存
     *
     * @param deptForm 记录
     * @return 保存记录数
     */
    @Transactional(rollbackFor = {})
    @Override
    public int save(DeptForm deptForm) {
        AtomicInteger update = new AtomicInteger(0);
        Optional.ofNullable(deptForm).ifPresent(form -> {
            Dept dept = this.deptConvert.map(form);
            if (!dept.isNewRecord()) {
                dept.preUpdate();
                update.set(this.deptMapper.updateByPrimaryKey(dept));
                // 更新用户表 组织机构名称
                User user = new User();
                user.setDeptId(dept.getId());
                user.setDeptName(dept.getName());
                this.userMapper.updateDeptByDeptId(user);
            } else {
                dept.preInsert();
                update.set(this.deptMapper.insert(dept));
            }
        });
        return update.get();
    }
    
    /**
     * 根据id删除
     *
     * @param id 主键
     * @return 删除记录数
     */
    @Transactional(rollbackFor = {})
    @Override
    public int delete(String id) {
        if (StringUtils.isBlank(id)) {
            return 0;
        }
        this.roleDeptMapper.deleteByDeptId(id);
        return this.deptMapper.deleteByPrimaryKey(id);
    }
    
    @Override
    public List<String> findByRole(String roleId) {
        if (StringUtils.isNotEmpty(roleId)) {
            // TODO: 2020/8/12 根据角色id 查询角色所具有的单位
        }
        return new ArrayList<>(1);
    }
    
    
    /**
     * 根据用户ID 查找组织机构树
     *
     * @param userId 用户id
     * @return 组织机构树
     */
    @Override
    public List<DeptVo> findTree(String userId) {
        return TreeBuildFactory.buildTree(
                this.findListByUserId(userId));
    }
}
