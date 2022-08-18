package com.hb.web.system.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hb.common.constants.SecurityConstants;
import com.hb.common.core.Result;
import com.hb.common.core.ValidGroup;
import com.hb.common.utils.RedisUtil;
import com.hb.security.token.OathToken;
import com.hb.service.TokenService;
import com.hb.system.model.LoginBody;
import com.wf.captcha.SpecCaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author zhaochengshui
 */
@RestController
public class SysLoginController {
    public static final Logger log = LoggerFactory.getLogger(SysLoginController.class);

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private TokenService tokenService;

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public Result login(@RequestBody @Validated(value = {ValidGroup.Search.class}) LoginBody user, HttpServletRequest request) {
        System.out.println(request);
        redisUtil.lSet("bc:black","");
        // 校验验证码
        if (StringUtils.isNotBlank(user.getUuid())) {
            String captchaCode = (String) redisUtil.get(SecurityConstants.CAPTCHA_PREFIX + user.getUuid());
            redisUtil.del(SecurityConstants.CAPTCHA_PREFIX + user.getUuid());
            if (!user.getCaptcha().equals(captchaCode)) {
                return Result.error("验证码错误");
            }
        }
        // 校验用户名密码
        OathToken oathToken = tokenService.login(user.getUsername(), user.getPassword());
        return Result.success("登录成功",oathToken);
    }


    /**
     * 获取验证码(过期时间1分钟)
     *
     * @return uuid 和 image
     */
    @GetMapping("/captcha")
    public Result getCaptcha() {
        // 输出验证码
        SpecCaptcha specCaptcha = new SpecCaptcha(100, 30, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String uuid = UUID.randomUUID().toString();
        redisUtil.set(SecurityConstants.CAPTCHA_PREFIX + uuid, verCode, 60);
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("uuid", uuid);
        map.put("image", specCaptcha.toBase64());
        return Result.success(map);
    }
}
