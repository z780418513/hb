package com.hb.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hb.common.core.PageBean;
import com.hb.system.dto.LogDTO;
import com.hb.system.entity.SysOperateLog;

/**
 * @author zhaochengshui
 * @description 操作日志服务类
 * @date 2022/8/31
 */
public interface SysOperateLogService extends IService<SysOperateLog> {

    /**
     * 分页查询
     * @param log
     * @return
     */
    PageBean<SysOperateLog> getLogPage(LogDTO log);
}
