package com.agile.demo.business.controller;

import cn.hutool.core.map.MapUtil;
import com.agile.demo.business.base.BaseController;
import com.agile.demo.business.entity.SysRole;
import com.agile.demo.business.entity.SysRoleMenu;
import com.agile.demo.business.service.SysRoleMenuServiceImpl;
import com.agile.demo.business.service.SysRoleServiceImpl;
import com.agile.demo.common.annotation.Cache;
import com.agile.demo.common.util.Constants;
import com.agile.demo.common.util.ValidateUtils;
import com.agile.demo.common.util.WebResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author liuyi
 * @date 2019/5/16
 *
 * 角色管理
 */


@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {

    @Resource
    private SysRoleServiceImpl sysRoleService;
    @Resource
    private SysRoleMenuServiceImpl sysRoleMenuService;

    /**
     * 角色列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public WebResult list(@RequestParam Map<String, Object> params){
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        //如果不是超级管理员，则只查询自己创建的角色列表
        if(getUserId() != Constants.SUPER_ADMIN){
            queryWrapper.eq("create_user_id",getUserId());
        }
        if(MapUtil.getStr(params,"roleName") != null){
            queryWrapper
                    .like("role_name", MapUtil.getStr(params,"roleName"));
        }
        IPage<SysRole> sysConfigList = sysRoleService.page(paramConvertPage(params),queryWrapper);
        return new WebResult().put("page", pageConvertMap(sysConfigList));
    }

    /**
     * 角色列表
     * @return
     */

    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public WebResult select() {
        List<SysRole> list = null;
        if (getUserId() != Constants.SUPER_ADMIN) {
            QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
            list = sysRoleService.list(queryWrapper.lambda().eq(SysRole::getCreateUserId, getUserId()));
        } else {
            list = sysRoleService.list();
        }
        return new WebResult().put("list", list);
    }

    /**
     * 角色信息
     * @param roleId
     * @return
     */
    @Cache(expired = "20")
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public WebResult info(@PathVariable("roleId") Long roleId) {
        SysRole role = sysRoleService.getById(roleId);
        role.setMenuIdList(sysRoleMenuService.queryMenuIdList(roleId));
        return new WebResult().put("role", role);
    }

    /**
     * 保存角色
     * @param role
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public WebResult save(@RequestBody SysRole role) {
        ValidateUtils.validateEntity(role);
        role.setCreateUserId(getUserId());
        role.setCreateTime(LocalDateTime.now());
        sysRoleService.save(role);
        return new WebResult();
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public WebResult update(@RequestBody SysRole role) {
        ValidateUtils.validateEntity(role);
        sysRoleService.updateById(role);
        return new WebResult();
    }

    /**
     * 删除角色
     * @param roleIds
     * @return
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public WebResult delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatchIds(roleIds);
        return new WebResult();
    }


}
