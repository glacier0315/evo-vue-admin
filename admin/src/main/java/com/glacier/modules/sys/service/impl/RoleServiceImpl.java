package com.glacier.modules.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.glacier.common.core.constant.SysConstants;
import com.glacier.common.core.entity.page.PageRequest;
import com.glacier.common.core.entity.page.PageResponse;
import com.glacier.modules.sys.convert.RoleConvert;
import com.glacier.modules.sys.entity.Role;
import com.glacier.modules.sys.entity.RoleDept;
import com.glacier.modules.sys.entity.RoleMenu;
import com.glacier.modules.sys.entity.dto.role.RoleForm;
import com.glacier.modules.sys.entity.dto.role.RoleQuery;
import com.glacier.modules.sys.entity.dto.role.RoleVo;
import com.glacier.modules.sys.handler.GlobalExceptionHandler;
import com.glacier.modules.sys.mapper.RoleDeptMapper;
import com.glacier.modules.sys.mapper.RoleMapper;
import com.glacier.modules.sys.mapper.RoleMenuMapper;
import com.glacier.modules.sys.mapper.UserRoleMapper;
import com.glacier.modules.sys.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色业务类
 *
 * @author glacier
 * @version 1.0
 * date 2019-08-11 21:21
 */
@Transactional(readOnly = true)
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Autowired
    private RoleConvert roleConvert;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private RoleDeptMapper roleDeptMapper;
    
    @Override
    public RoleVo findById(String id) {
        return this.roleConvert.toRoleVo(
                this.roleMapper.selectByPrimaryKey(id));
    }
    
    /**
     * 查询所有
     *
     * @return 角色
     */
    @Override
    public List<RoleVo> findAllList() {
        return this.roleConvert.toRoleVo(
                this.roleMapper.selectAll());
    }
    
    /**
     * 根据用户id 查询角色
     *
     * @param userId 用户id
     * @return 角色
     */
    @Override
    public List<RoleVo> findByUserId(String userId) {
        return this.roleConvert.toRoleVo(
                this.roleMapper.findByUserId(userId));
    }
    
    @Override
    public boolean checkCode(final RoleForm roleForm) {
        if (roleForm != null
                && StringUtils.isNotEmpty(roleForm.getCode())) {
            Role role = new Role();
            role.setId(roleForm.getId());
            role.setCode(roleForm.getCode());
            return this.roleMapper.checkCodeExist(role) > 0;
        }
        return false;
    }
    
    @Override
    public PageResponse<RoleVo> findPage(PageRequest<RoleQuery> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        PageInfo<Role> pageInfo = PageInfo.of(this.roleMapper.selectList(pageRequest.getParams()));
        return new PageResponse<>(
                pageInfo.getPageNum(),
                pageInfo.getPages(),
                pageInfo.getTotal(),
                this.roleConvert.toRoleVo(pageInfo.getList()));
    }
    
    /**
     * 保存
     *
     * @param roleForm 记录
     * @return 保存记录数
     */
    @Transactional(rollbackFor = {})
    @Override
    public int save(RoleForm roleForm) {
        Role role = this.roleConvert.map(roleForm);
        int update = 0;
        if (!role.isNewRecord()) {
            role.preUpdate();
            update = this.roleMapper.updateByPrimaryKey(role);
        } else {
            role.preInsert();
            update = this.roleMapper.insert(role);
        }
        // 保存角色和菜单
        this.saveRoleMenu(role.getId(), roleForm.getMenus());
        // 保存角色和单位
        this.saveRoleDept(role.getId(), role.getDataScope(), roleForm.getDepts());
        return update;
    }
    
    /**
     * 根据id批量删除
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
        int unpdate = this.roleMapper.deleteByPrimaryKey(id);
        // 删除用户角色关系
        this.userRoleMapper.deleteByRoleId(id);
        // 删除角色资源关系
        this.roleMenuMapper.deleteByRoleId(id);
        // 删除角色单位关系
        this.roleDeptMapper.deleteByRoleId(id);
        return unpdate;
    }
    
    /**
     * 保存角色菜单关系
     * 1 先清空
     * 2 保存
     *
     * @param roleId  角色id
     * @param menuIds 菜单id
     * @return 保存记录数
     */
    private int saveRoleMenu(String roleId, List<String> menuIds) {
        int update = 0;
        if (StringUtils.isNotEmpty(roleId)) {
            // 清空原角色和菜单关系
            this.roleMenuMapper.deleteByRoleId(roleId);
            // 保存角色菜单关系
            if (menuIds != null
                    && !menuIds.isEmpty()) {
                for (String menuId : menuIds) {
                    update += this.roleMenuMapper.insert(new RoleMenu(roleId, menuId));
                }
            }
        }
        return update;
    }
    
    /**
     * 保存角色单位关系
     * 1 先清空
     * 2 保存 仅自定义时保存
     *
     * @param roleId    角色id
     * @param dataScope 数据权限
     * @param deptIds   单位id
     * @return 保存记录数
     */
    private int saveRoleDept(String roleId, String dataScope, List<String> deptIds) {
        int update = 0;
        if (StringUtils.isNotEmpty(roleId)) {
            // 清空原角色和单位关系
            this.roleDeptMapper.deleteByRoleId(roleId);
            // 保存角单位单关系
            if (SysConstants.DATASCOPE_CUSTOMIZE_DEPT.equals(dataScope)
                    && deptIds != null
                    && !deptIds.isEmpty()) {
                for (String deptId : deptIds) {
                    update += this.roleDeptMapper.insert(new RoleDept(roleId, deptId));
                }
            }
        }
        return update;
    }
}
