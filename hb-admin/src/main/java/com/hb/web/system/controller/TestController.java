package com.hb.web.system.controller;

import com.hb.common.core.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/8/17
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public Result test(){
        return Result.success();
    }
}
