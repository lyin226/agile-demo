package com.agile.demo.business.service;

import com.agile.demo.business.entity.SysMenu;
import com.agile.demo.business.entity.SysUser;
import com.agile.demo.business.entity.SysUserToken;
import com.agile.demo.business.mapper.SysUserMapper;
import com.agile.demo.business.mapper.SysUserTokenMapper;
import com.agile.demo.common.util.Constants;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author liuyi
 * @date 2019/5/15
 */

@DS("slave_1")
@Service
public class ShiroServiceImpl {

    @Resource
    private SysMenuServiceImpl sysMenuService;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserTokenMapper sysUserTokenMapper;

    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == Constants.SUPER_ADMIN){
            List<SysMenu> menuList = sysMenuService.list();
            permsList = new ArrayList<>(menuList.size());
            for(SysMenu menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserMapper.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    public SysUserToken queryByToken(String token) {
        QueryWrapper<SysUserToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token", token);
        return sysUserTokenMapper.selectOne(queryWrapper);
    }

    public SysUser queryUser(Long userId) {
        return sysUserMapper.selectById(userId);
    }

}
