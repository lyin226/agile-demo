package com.agile.demo.business.service;

import com.agile.demo.business.entity.SysUser;
import com.agile.demo.business.mapper.SysUserMapper;
import com.agile.demo.common.annotation.Cache;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author liuyi
 * @since 2019-05-08
 */

@DS("master")
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> {

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 更新用户密码
     * @param userId
     * @param password
     * @param newPassword
     * @return
     */
    public int updatePassword(Long userId, String password, String newPassword) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("password", password);
        map.put("newPassword", newPassword);
        return sysUserMapper.updatePassword(map);
    }

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Cache
    public SysUser getOne(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return super.getOne(queryWrapper);
    }

}
