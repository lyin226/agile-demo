package com.agile.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;


/**
 * @author liuyi
 * @date 2019/5/13
 */
public class AuthenticationTest extends BaseTest {


    @Test
    public void testAuthentication() {
        JdbcRealm jdbcRealm =new JdbcRealm();
        IniRealm iniRealm = new IniRealm("classpath:user.ini");
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("Mark","123456");
        subject.login(token);
        System.out.println("isAuthenticated:" + subject.isAuthenticated());
        subject.checkRoles("admin");
        subject.checkPermission("user:delete");
    }
}
