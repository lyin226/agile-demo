package com.agile.demo.business.mapper;

import com.agile.demo.business.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 Mapper 接口
 * </p>
 *
 * @author liuyi
 * @since 2019-05-17
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {


    List<Long> queryMenuIdList(Long roleId);

}
