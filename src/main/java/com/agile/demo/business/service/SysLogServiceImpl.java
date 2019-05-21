package com.agile.demo.business.service;

import com.agile.demo.business.entity.SysLog;
import com.agile.demo.business.mapper.SysLogMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author liuyi
 * @since 2019-05-16
 */

@DS("master")
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> {

}
