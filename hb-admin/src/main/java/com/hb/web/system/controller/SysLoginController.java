package com.hb.web.system.controller;


import com.hb.common.Result;
import com.hb.common.constants.SysConstant;
import com.hb.common.vaild.ValidGroup;
import com.hb.common.enums.BusinessExceptionEnum;
import com.hb.common.expection.BusinessException;
import com.hb.common.utils.RedisUtil;
import com.hb.framwork.security.service.JwtTokenService;
import com.hb.framwork.security.token.MobileCodeAuthenticationToken;
import com.hb.system.model.LoginUser;
import com.hb.system.model.SysUser;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author hanbaolaoba
 */
@RestController
public class SysLoginController {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private JwtTokenService jwtTokenService;

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public Result login(@RequestBody LoginUser user) {
        String loginType = user.getLoginType();
        Authentication token;
        // 手机号登入
        if ("2".equals(loginType)) {
            token = new MobileCodeAuthenticationToken(user.getPhone(), user.getMsgCode());
        } else if ("1".equals(loginType)) {
            token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        }else {
            throw new BusinessException("登入方式错误");
        }
        // 验证登入方式
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return Result.success();
    }


}
