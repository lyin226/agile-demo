package com.agile.demo.business.service;

import com.agile.demo.business.entity.SysUserToken;
import com.agile.demo.business.mapper.SysUserTokenMapper;
import com.agile.demo.common.util.Constants;
import com.agile.demo.common.util.TokenGenerator;
import com.agile.demo.common.util.WebResult;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户Token 服务实现类
 * </p>
 *
 * @author liuyi
 * @since 2019-05-09
 */

@DS("master")
@Service
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenMapper, SysUserToken> {

    @Resource
    private SysUserTokenMapper sysUserTokenMapper;
    /**
     * 生成token
     * @param userId
     * @return
     */


    public WebResult createToken(long userId) {
        String token = TokenGenerator.generateValue();
        //当前时间
        LocalDateTime now = LocalDateTime.now();
        //过期时间
        LocalDateTime expireTime = now.plusHours(Constants.EXPIRE);
        //判断是否生成token
        SysUserToken sysUserToken = sysUserTokenMapper.selectById(userId);
        if(sysUserToken == null){
            sysUserToken = new SysUserToken();
            sysUserToken.setUserId(userId);
            sysUserToken.setToken(token);
            sysUserToken.setUpdateTime(now);
            sysUserToken.setExpireTime(expireTime);
            baseMapper.insert(sysUserToken);
        }else{
            sysUserToken.setToken(token);
            sysUserToken.setUpdateTime(now);
            sysUserToken.setExpireTime(expireTime);
            sysUserTokenMapper.updateById(sysUserToken);
        }
        return new WebResult().put("token", token).put("expire", expireTime);
    }

    public void logout(long userId) {
        sysUserTokenMapper.deleteById(userId);
    }

}
