package com.agile.demo.business.service;


import com.agile.demo.business.entity.SysUserRole;
import com.agile.demo.business.mapper.SysUserRoleMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author liuyi
 * @since 2019-05-15
 */

@DS("master")
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> {

}
