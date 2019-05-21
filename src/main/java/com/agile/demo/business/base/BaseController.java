package com.agile.demo.business.base;

import com.agile.demo.business.entity.SysUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyi
 * @date 2019/5/5
 * Controller公共组件
 */


public abstract class BaseController {

    /**
     * 获取用户信息
     * @return
     */
    protected SysUser getUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取用户id
     * @return
     */
    protected Long getUserId()  {
        return getUser().getUserId();
    }

    /**
     * 构建Mybatis plus的page
     * @param param
     * @param <T>
     * @return
     */
    public <T> IPage<T> paramConvertPage(Map<String, Object> param) {
        int currentPage = 1;
        int limit = 10;
        if (param.get("page") != null) {
            currentPage = Integer.parseInt(String.valueOf(param.get("page")));
        }
        if (param.get("limit") != null) {
            limit = Integer.parseInt(String.valueOf(param.get("limit")));
        }
        IPage<T> page = new Page<>(currentPage, limit);
        return page;
    }

    /**
     * Mybatis plus 的page转为map
     * @param page
     * @return
     */
    public Map<String, Object> pageConvertMap(IPage<?> page) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", page.getRecords());
        map.put("totalCount", page.getTotal());
        map.put("pageSize", page.getSize());
        map.put("currPage", page.getCurrent());
        map.put("totalPage", page.getPages());
        return map;
    }
}
