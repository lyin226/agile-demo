package com.agile.demo.business.controller;

import cn.hutool.core.map.MapUtil;
import com.agile.demo.business.base.BaseController;
import com.agile.demo.business.entity.SysLog;
import com.agile.demo.business.service.SysLogServiceImpl;
import com.agile.demo.common.util.WebResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author liuyi
 * @date 2019/5/18
 */

@RestController
@RequestMapping("/sys/log")
public class SysLogController extends BaseController {

    @Resource
    private SysLogServiceImpl sysLogService;


    /**
     * 列表查询
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    public WebResult list(@RequestParam Map<String, Object> params){
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        if(MapUtil.getStr(params,"key") != null){
            queryWrapper
                    .like("username",MapUtil.getStr(params,"key"))
                    .or()
                    .like("operation",MapUtil.getStr(params,"key"));
        }
        IPage<SysLog> sysLogList = sysLogService.page(paramConvertPage(params),queryWrapper);
        return new WebResult().put("page", pageConvertMap(sysLogList));
    }
}
