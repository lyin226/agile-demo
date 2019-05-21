package com.agile.demo.business.service;

import com.agile.demo.business.entity.SysMenu;
import com.agile.demo.business.mapper.SysMenuMapper;
import com.agile.demo.common.util.Constants;
import com.agile.demo.common.util.MenuType;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyi
 * @date 2019/5/15
 *
 * 系统菜单
 */

@DS("master")
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> {

    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
     * 根据用户id获取菜单信息
     * @param userId
     * @return
     */
    @DS("slave_2")
    public List<SysMenu> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if(userId == Constants.SUPER_ADMIN){
            return getAllMenuList(null);
        }
        //用户菜单列表
        List<Long> menuIdList = sysMenuMapper.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    /**
     * 获取所有菜单列表
     * @param menuIdList
     * @return
     */
    private List<SysMenu> getAllMenuList(List<Long> menuIdList){
        //查询根菜单列表
        List<SysMenu> menuList = queryListByParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 获取匹配菜单id的菜单
     * @param parentId
     * @param menuIdList
     * @return
     */
    @DS("slave_2")
    public List<SysMenu> queryListByParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenu> menuList = queryListByParentId(parentId);
        if(menuIdList == null){
            return menuList;
        }
        List<SysMenu> userMenuList = new ArrayList<>();
        for(SysMenu menu : menuList){
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    /**
     * 递归获取菜单树
     * @param menuList
     * @param menuIdList
     * @return
     */
    private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList){
        List<SysMenu> subMenuList = new ArrayList<SysMenu>();
        for(SysMenu entity : menuList){
            //目录
            if(entity.getType() == MenuType.CATALOG.getValue()){
                List<SysMenu> tempList = queryListByParentId(entity.getMenuId(), menuIdList);
                List<SysMenu> subTempList = getMenuTreeList(tempList, menuIdList);
                entity.setList(subTempList);
            }
            subMenuList.add(entity);
        }
        return subMenuList;
    }

    /**
     * 根据父菜单id查询父菜单
     * @param parentId
     * @return
     */
    @DS("slave_2")
    public List<SysMenu> queryListByParentId(Long parentId) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId).orderByAsc("order_num");
        return sysMenuMapper.selectList(queryWrapper);
    }

    /**
     * 获取不包含菜单列表
     * @return
     */
    @DS("slave_2")
    public List<SysMenu> queryNotButtonList() {
        return sysMenuMapper.queryNotButtonList();
    }

}
