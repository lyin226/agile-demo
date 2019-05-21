package com.agile.demo.business.controller;

import com.agile.demo.business.base.BaseController;
import com.agile.demo.business.entity.SysUser;
import com.agile.demo.business.service.SysUserServiceImpl;
import com.agile.demo.business.service.SysUserTokenServiceImpl;
import com.agile.demo.common.util.Constants;
import com.agile.demo.common.util.WebResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author liuyi
 * @date 2019/5/5
 *
 * 登录相关
 */
@Slf4j
@RestController
@RequestMapping("/sys")
public class SysLoginController extends BaseController {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private SysUserServiceImpl sysUserService;
    @Resource
    private SysUserTokenServiceImpl sysUserTokenService;

    /**
     * 验证码
     * @param time
     * @param response
     */
    @SneakyThrows
    @RequestMapping("/captcha/{time}")
    public void captcha(@PathVariable("time") String time, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = defaultKaptcha.createText();
        //生成图片验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        //redis 60秒
        redisTemplate.opsForValue().set(Constants.NUMBER_CODE_KEY + time,text,60, TimeUnit.SECONDS);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     * @param username
     * @param password
     * @param captcha
     * @param randomStr
     * @return
     */
    @RequestMapping (value="/login", method = RequestMethod.POST)
    public Map<String, Object> login(String username, String password,String captcha, String randomStr) {
        String value = (String)redisTemplate.opsForValue().get(Constants.NUMBER_CODE_KEY + randomStr);
        if (StringUtils.isBlank(value)) {
            return WebResult.error("验证码过期");
        }
        if (!captcha.equals(value)) {
            return WebResult.error("验证码不正确");
        }
        SysUser sysUser = sysUserService.getOne(username);
        if(sysUser == null || !sysUser.getPassword().equals(new Sha256Hash(password, sysUser.getSalt()).toHex())) {
            return WebResult.error("账号或密码不正确");
        }
        if(sysUser.getStatus() == 0){
            return WebResult.error("账号已被锁定，请联系管理员");
        }
        //生成token 保存到数据库  可优化为存储到redis和数据库
        return sysUserTokenService.createToken(sysUser.getUserId());
    }

    /**
     * 退出系统
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public WebResult logout() {
        sysUserTokenService.logout(getUserId());
        return new WebResult();
    }

}
