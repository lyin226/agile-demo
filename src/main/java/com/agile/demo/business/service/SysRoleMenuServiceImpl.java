package com.agile.demo.business.service;


import com.agile.demo.business.entity.SysRoleMenu;
import com.agile.demo.business.mapper.SysRoleMenuMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务实现类
 * </p>
 *
 * @author liuyi
 * @since 2019-05-17
 */

@DS("master")
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> {


    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 根据角色id获取菜单id
     * @param roleId
     * @return
     */
    public List<Long> queryMenuIdList(Long roleId) {
        return sysRoleMenuMapper.queryMenuIdList(roleId);
    }

}
