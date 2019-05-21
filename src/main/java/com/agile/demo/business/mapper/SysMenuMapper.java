package com.agile.demo.business.mapper;

import com.agile.demo.business.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author liuyi
 * @since 2019-05-15
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 查询用户的所有菜单id
     * @param userId
     * @return
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 获取不包含菜单列表
     * @return
     */
    List<SysMenu> queryNotButtonList();
}
