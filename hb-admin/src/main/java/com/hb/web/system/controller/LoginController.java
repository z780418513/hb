package com.hb.web.system.controller;


import com.hb.common.constants.SecurityConstants;
import com.hb.common.core.Result;
import com.hb.common.core.ValidGroup;
import com.hb.common.utils.RedisUtils;
import com.hb.core.security.service.TokenService;
import com.hb.system.model.CustomUserDetails;
import com.hb.system.model.LoginBody;
import com.wf.captcha.SpecCaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author zhaochengshui
 */
@RestController
public class LoginController {
    public static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private RedisUtils redisUtil;
    @Resource
    private TokenService tokenService;
    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public Result login(@RequestBody @Validated(value = {ValidGroup.Search.class}) LoginBody user, HttpServletRequest request, HttpServletResponse response) {
        //使用用户名密码进行登录验证
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        //生成JWT
        String token = tokenService.getOathToken(userDetails, request);
        long expirationTime = tokenService.getExpirationDateTime(token);
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("token", token);
        map.put("expireTime", expirationTime);
        return Result.success("登录成功", map);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @PostMapping("/login/out")
    public Result loginOut(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER_FLAG);
        String username = tokenService.getUserNameFromToken(token);
        redisUtil.del(SecurityConstants.TOKEN_REDIS_PREFIX + username);
        return Result.success();
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
