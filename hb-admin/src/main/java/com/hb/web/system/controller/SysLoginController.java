package com.hb.web.system.controller;


import com.hb.common.Result;
import com.hb.common.constants.SysConstant;
import com.hb.common.vaild.ValidGroup;
import com.hb.common.enums.BusinessExceptionEnum;
import com.hb.common.expection.BusinessException;
import com.hb.common.utils.RedisUtil;
import com.hb.system.model.LoginBody;
import com.wf.captcha.SpecCaptcha;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author hanbaolaoba
 */
@RestController
public class SysLoginController {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public Result login(@RequestBody @Validated(value = {ValidGroup.Search.class}) LoginBody user) {
        // 校验验证码
        String captchaCode = (String) redisUtil.get(SysConstant.CAPTCHA_PREFIX + user.getUuid());
        redisUtil.del(SysConstant.CAPTCHA_PREFIX + user.getUuid());
        if (!user.getCaptcha().equals(captchaCode)) {
           throw new BusinessException(BusinessExceptionEnum.CODE_OR_PASSWORD_ERROR);
        }
        // 校验用户名密码
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        // 可以拿到用户登录信息,并对应做处理
        System.out.println(authenticate);
        return Result.success();
    }


    /**
     * 获取验证码(过期时间1分钟)
     *
     * @return
     */
    @GetMapping("/captcha")
    public Result getCaptcha() {
        // 输出验证码
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String uuid = UUID.randomUUID().toString();
        redisUtil.set(SysConstant.CAPTCHA_PREFIX + uuid, verCode, 60);
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        return Result.success(map);

    }
}
