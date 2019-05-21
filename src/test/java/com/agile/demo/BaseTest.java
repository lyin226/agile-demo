package com.agile.demo;

import com.agile.demo.business.entity.SysUser;
import com.agile.demo.business.mapper.SysUserMapper;
import com.agile.demo.business.service.SysUserServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author liuyi
 * @date 2019/5/8
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = {"com.agile.demo.*"})
public abstract class BaseTest {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserServiceImpl sysUserService;

    @Test
    public void testInsertSysUser() {
        SysUser sysUser = new SysUser();
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setCreateUserId(1L);
        sysUser.setEmail("test");
        sysUser.setMobile("test");
        sysUser.setPassword("test");
        sysUser.setSalt("test");
        sysUser.setStatus(1);
        sysUser.setUsername("test");
//        Integer insert = sysUserMapper.insert(sysUser);
        boolean insert = sysUserService.save(sysUser);
        System.out.println("test insert sys_user "+ insert);
    }

    @Test
    public void testSelectPage() {
        Page page = new Page(1, 5);
        IPage iPage = sysUserMapper.selectPage(page, null);
        System.out.println("testSelectPage " + iPage.getTotal());
    }

    @Test
    public void testGetOne() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", "admin");
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        System.out.println("123123");
    }

    @Test
    public void testMapper() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", "admin");
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        System.out.println("test " + sysUser.getUserId());
    }

    @Test
    public void test() {
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");
        System.out.println("token "+ token);
    }

}
