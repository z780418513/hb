package com.hb.controller;

import com.hb.common.core.Result;
import com.hb.common.core.ValidGroup;
import com.hb.system.dto.LogDTO;
import com.hb.system.service.SysOperateLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhaochengshui
 * @description 操作日志controller
 * @date 2022/9/1
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Resource
    private SysOperateLogService logService;

    /**
     * 分页查询日志信息
     *
     * @param log
     * @return
     */
    @GetMapping("/list")
    public Result getLogPage(@Validated(value = {ValidGroup.Page.class}) LogDTO log) {
        return Result.success(logService.getLogPage(log));
    }
}
