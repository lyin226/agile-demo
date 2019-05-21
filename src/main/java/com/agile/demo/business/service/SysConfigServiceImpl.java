package com.agile.demo.business.service;


import com.agile.demo.business.entity.SysConfig;
import com.agile.demo.business.mapper.SysConfigMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统配置信息表 服务实现类
 * </p>
 *
 * @author liuyi
 * @since 2019-05-18
 */

@DS("master")
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> {

}
