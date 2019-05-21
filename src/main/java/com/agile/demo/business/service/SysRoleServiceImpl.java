package com.agile.demo.business.service;


import com.agile.demo.business.entity.SysRole;
import com.agile.demo.business.mapper.SysRoleMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author liuyi
 * @since 2019-05-16
 */

@DS("master")
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> {


    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 删除角色
     * @param ids
     */
    public void deleteBatchIds(Long[] ids) {
        sysRoleMapper.deleteBatchIds(Arrays.asList(ids));
    }

}
