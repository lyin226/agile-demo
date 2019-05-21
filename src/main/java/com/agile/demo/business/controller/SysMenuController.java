package com.agile.demo.business.controller;

import com.agile.demo.business.base.BaseController;
import com.agile.demo.business.entity.SysMenu;
import com.agile.demo.business.service.ShiroServiceImpl;
import com.agile.demo.business.service.SysMenuServiceImpl;
import com.agile.demo.common.annotation.Cache;
import com.agile.demo.common.annotation.SysLog;
import com.agile.demo.common.util.BusinessException;
import com.agile.demo.common.util.MenuType;
import com.agile.demo.common.util.WebResult;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author liuyi
 * @date 2019/5/15
 *
 * 菜单管理
 */


@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {

    @Resource
    private SysMenuServiceImpl sysMenuService;
    @Resource
    private ShiroServiceImpl shiroService;


    /**
     * 导航菜单
     * @return
     */
    @RequestMapping("/nav")
    public WebResult nav() {
        List<SysMenu> menuList = sysMenuService.getUserMenuList(getUserId());
        Set<String> permissions = shiroService.getUserPermissions(getUserId());
        return new WebResult().put("menuList", menuList).put("permissions", permissions);
    }

    /**
     * 所有列表菜单
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenu> list() {
        return sysMenuService.list();
    }

    /**
     * 选择菜单(添加、修改菜单)
     * @return
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public WebResult select(){
        //查询列表数据
        List<SysMenu> menuList = sysMenuService.queryNotButtonList();
        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);
        return new WebResult().put("menuList", menuList);
    }

    /**
     * 菜单信息
     * @param menuId
     * @return
     */
    @Cache(expired = "20")
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public WebResult info(@PathVariable("menuId") Long menuId){
        SysMenu menu = sysMenuService.getById(menuId);
        return new WebResult().put("menu", menu);
    }

    /**
     * 保存
     * @param menu
     * @return
     */
    @SysLog("保存菜单")
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public WebResult save(@RequestBody SysMenu menu){
        //数据校验
        verifyForm(menu);
        sysMenuService.save(menu);
        return new WebResult();
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public WebResult update(@RequestBody SysMenu menu){
        //数据校验
        verifyForm(menu);
        sysMenuService.updateById(menu);
        return new WebResult();
    }

    /**
     * 删除
     * @param menuId
     * @return
     */
    @SysLog("删除菜单")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public WebResult delete(long menuId){
        //判断是否有子菜单或按钮
        List<SysMenu> menuList = sysMenuService.queryListByParentId(menuId);
        if(menuList.size() > 0){
            return WebResult.error("请先删除子菜单或按钮");
        }

        sysMenuService.removeById(menuId);

        return new WebResult();
    }


    /**
     * 验证参数是否正确
     * @param menu
     */
    private void verifyForm(SysMenu menu){
        if(StringUtils.isBlank(menu.getName())){
            throw new BusinessException("菜单名称不能为空");
        }

        if(menu.getParentId() == null){
            throw new BusinessException("上级菜单不能为空");
        }

        //菜单
        if(menu.getType() == MenuType.MENU.getValue()){
            if(StringUtils.isBlank(menu.getUrl())){
                throw new BusinessException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = MenuType.CATALOG.getValue();
        if(menu.getParentId() != 0){
            SysMenu parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if(menu.getType() == MenuType.CATALOG.getValue() ||
                menu.getType() == MenuType.MENU.getValue()){
            if(parentType != MenuType.CATALOG.getValue()){
                throw new BusinessException("上级菜单只能为目录类型");
            }
            return ;
        }

        //按钮
        if(menu.getType() == MenuType.BUTTON.getValue()){
            if(parentType != MenuType.MENU.getValue()){
                throw new BusinessException("上级菜单只能为菜单类型");
            }
            return ;
        }
    }


}
