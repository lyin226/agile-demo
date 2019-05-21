package com.agile.demo.business.mapper;


import com.agile.demo.business.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author liuyi
 * @since 2019-05-08
 */

public interface SysUserMapper extends BaseMapper<SysUser> {


    /**
     * 修改密码
     * @param map
     * @return
     */
    int updatePassword(Map<String, Object> map);

    /**
     * 查询用户所有权限
     * @param userId
     * @return
     */
    List<String> queryAllPerms(Long userId);

}
