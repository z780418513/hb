package com.hb.web.system.controller;

import com.hb.core.R;
import com.hb.system.model.SysUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SysLoginController {
    @Resource
    private AuthenticationManager authenticationManager;

    @PostMapping("/user/login")
    public R login(@RequestBody SysUser user) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        return R.success();
    }
}
