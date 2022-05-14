package com.hb.web.system.controller;

import com.hb.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public Result test(){
        return Result.success();
    }
}
