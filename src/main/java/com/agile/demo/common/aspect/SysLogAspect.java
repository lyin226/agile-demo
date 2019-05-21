package com.agile.demo.common.aspect;

import com.agile.demo.business.entity.SysLog;
import com.agile.demo.business.entity.SysUser;
import com.agile.demo.business.service.SysLogServiceImpl;
import com.agile.demo.common.util.IPUtils;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author liuyi
 * @date 2019/5/16
 *
 * 系统日志切面
 */

@Aspect
@Component
public class SysLogAspect {

    @Resource
    private HttpServletRequest request;

    @Resource
    private SysLogServiceImpl sysLogService;

    @Pointcut("@annotation(com.agile.demo.common.annotation.SysLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - beginTime;
        saveSysLog(joinPoint, time);
        return result;
    }

    /**
     * 系统保存日志
     * @param joinPoint
     * @param time
     * @throws Throwable
     */
    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        com.agile.demo.common.annotation.SysLog log = signature.getMethod().getAnnotation(com.agile.demo.common.annotation.SysLog.class);
        Object[] args = joinPoint.getArgs();
        SysLog sysLog = new SysLog();
        if (log != null) {
            sysLog.setOperation(log.value());
        }
        sysLog.setParams(JSON.toJSONString(args[0]));
        sysLog.setCreateDate(LocalDateTime.now());
        sysLog.setIp(IPUtils.getIpAdrress(request));
        sysLog.setMethod(joinPoint.getTarget().getClass().getName() + "." + signature.getName());
        sysLog.setTime(time);
        sysLog.setUsername(((SysUser) SecurityUtils.getSubject().getPrincipal()).getUsername());
        sysLogService.save(sysLog);
    }

}
