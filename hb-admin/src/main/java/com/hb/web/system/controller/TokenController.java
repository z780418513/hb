package com.hb.web.system.controller;

import com.hb.common.core.Result;
import com.hb.security.token.OathToken;
import com.hb.security.token.RefreshOathToken;
import com.hb.service.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhaochengshui
 * @date 2022/8/16
 */
@RestController
@RequestMapping("/token")
public class TokenController {
    @Resource
    private TokenService tokenService;

    /**
     * 刷新token
     *
     * @return
     */
    @PostMapping("/refresh")
    public Result refreshToken(@RequestBody RefreshOathToken refreshToken) {
        OathToken oathToken = tokenService.refreshToken(refreshToken.getRefreshToken(), refreshToken.getUsername());
        return Result.success(oathToken);
    }

}
