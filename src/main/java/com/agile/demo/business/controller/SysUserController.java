package com.agile.demo.business.controller;

import cn.hutool.core.map.MapUtil;
import com.agile.demo.business.base.BaseController;
import com.agile.demo.business.entity.SysUser;
import com.agile.demo.business.entity.SysUserRole;
import com.agile.demo.business.service.SysUserRoleServiceImpl;
import com.agile.demo.business.service.SysUserServiceImpl;
import com.agile.demo.common.annotation.Cache;
import com.agile.demo.common.annotation.SysLog;
import com.agile.demo.common.util.Constants;
import com.agile.demo.common.util.RandomStrUtils;
import com.agile.demo.common.util.ValidateUtils;
import com.agile.demo.common.util.WebResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuyi
 * @date 2019/5/12
 *
 * 系统用户
 */

@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {


    @Resource
    private SysUserServiceImpl sysUserService;
    @Resource
    private SysUserRoleServiceImpl sysUserRoleService;


    /**
     * 修改密码
     * @param password
     * @param newPassword
     * @return
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    public WebResult password(String password, String newPassword) {
        ValidateUtils.isBlank(newPassword, "新密码不能为空");
        ValidateUtils.isBlank(password, "旧密码不能为空");
        password = new Sha256Hash(password, getUser().getSalt()).toHex();
        newPassword = new Sha256Hash(newPassword, getUser().getSalt()).toHex();
        int count = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (count == 0) {
            return new WebResult(-1, "原密码不正确");
        }
        return new WebResult();
    }

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping("/info")
    public WebResult info() {
        return new WebResult().put("user", getUser());
    }


    /**
     * 获取所有用户列表
     * @param params
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public WebResult list(@RequestParam  Map<String, Object> params) {
        if (getUserId() != Constants.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }
        //查询列表数据
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if(MapUtil.getStr(params,"username") != null){
            queryWrapper
                    .like("username", MapUtil.getStr(params,"username"))
                    .or()
                    .like("mobile",MapUtil.getStr(params,"username"));
        }
        IPage<SysUser> sysUserIPage = sysUserService.page(paramConvertPage(params),queryWrapper);
        return new WebResult().put("page", pageConvertMap(sysUserIPage));
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public WebResult save(@RequestBody SysUser user) {
        ValidateUtils.validateEntity(user);
        user.setSalt(RandomStrUtils.getRandomStr(20));
        user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        user.setCreateUserId(getUserId());
        user.setCreateTime(LocalDateTime.now());
        sysUserService.save(user);
        return new WebResult();
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public WebResult update(@RequestBody SysUser user) {
        ValidateUtils.validateEntity(user);
        user.setCreateUserId(getUserId());
        user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        sysUserService.updateById(user);
        return new WebResult();
    }

    /**
     * 删除用户
     * @param userIds
     * @return
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public WebResult delete(@RequestBody Long[] userIds) {
        if(ArrayUtils.contains(userIds, 1L)){
            return WebResult.error("系统管理员不能删除");
        }
        if(ArrayUtils.contains(userIds, getUserId())){
            return WebResult.error("当前用户不能删除");
        }
        sysUserService.removeByIds(Arrays.asList(userIds));
        return new WebResult();
    }

    @Cache(expired = "20")
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public WebResult info(@PathVariable("userId") Long userId) {
        SysUser user = sysUserService.getById(userId);
        List<Long> roleIdList = sysUserRoleService.list(
                new QueryWrapper<SysUserRole>()
                        .lambda()
                        .eq(SysUserRole::getUserId, userId)).stream()
                .map(sysUserRole -> sysUserRole.getRoleId())
                        .collect(Collectors.toList());
        user.setRoleIdList(roleIdList);
        return new WebResult().put("user", user);
    }


}
