package com.hb.controller;

import com.hb.common.core.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhaochengshui
 * @date 2022/8/16
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    /**
     * 刷新token
     * TODO
     *
     * @return
     */
    @PostMapping("/refresh")
    public Result refreshToken(HttpServletRequest request) {
        return Result.success();
    }

}
