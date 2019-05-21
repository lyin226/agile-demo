package com.agile.demo.business.controller;

import cn.hutool.core.map.MapUtil;
import com.agile.demo.business.base.BaseController;
import com.agile.demo.business.entity.SysConfig;
import com.agile.demo.business.service.SysConfigServiceImpl;
import com.agile.demo.common.annotation.SysLog;
import com.agile.demo.common.util.ValidateUtils;
import com.agile.demo.common.util.WebResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * @author liuyi
 * @date 2019/5/18
 *
 * 参数管理
 */

@RequestMapping("/sys/config")
@RestController
public class SysConfigController extends BaseController{

    @Resource
    private SysConfigServiceImpl sysConfigService;

    /**
     * 所有配置列表
     * @param params
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    public WebResult list(@RequestParam Map<String, Object> params){
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        if(MapUtil.getStr(params,"key") != null){
            queryWrapper
                    .like("remark",MapUtil.getStr(params,"key"));
        }
        IPage<SysConfig> sysConfigList = sysConfigService.page(paramConvertPage(params),queryWrapper);
        return new WebResult().put("page", pageConvertMap(sysConfigList));
    }


    /**
     * 配置信息
     * @param id
     * @return
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    public WebResult info(@PathVariable("id") Long id){
        SysConfig config = sysConfigService.getById(id);
        return new WebResult().put("config", config);
    }

    /**
     * 保存配置
     * @param config
     * @return
     */
    @SysLog("保存配置")
    @RequestMapping("/save")
    @RequiresPermissions("sys:config:save")
    public WebResult save(@RequestBody SysConfig config){
        ValidateUtils.validateEntity(config);
        sysConfigService.save(config);
        return new WebResult();
    }

    /**
     * 修改配置
     * @param config
     * @return
     */
    @SysLog("修改配置")
    @RequestMapping("/update")
    @RequiresPermissions("sys:config:update")
    public WebResult update(@RequestBody SysConfig config){
        ValidateUtils.validateEntity(config);
        sysConfigService.updateById(config);
        return new WebResult();
    }

    /**
     * 删除配置
     * @param ids
     * @return
     */
    @SysLog("删除配置")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    public WebResult delete(@RequestBody Long[] ids){
        sysConfigService.removeByIds(Arrays.asList(ids));
        return new WebResult();
    }


}
