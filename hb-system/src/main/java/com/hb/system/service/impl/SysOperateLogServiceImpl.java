package com.hb.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.common.core.PageBean;
import com.hb.common.utils.PageUtils;
import com.hb.system.dto.LogDTO;
import com.hb.system.entity.SysOperateLog;
import com.hb.system.mapper.SysOperateLogMapper;
import com.hb.system.service.SysOperateLogService;
import org.springframework.stereotype.Service;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/8/31
 */
@Service
public class SysOperateLogServiceImpl extends ServiceImpl<SysOperateLogMapper, SysOperateLog>
        implements SysOperateLogService {
    @Override
    public PageBean<SysOperateLog> getLogPage(LogDTO dto) {
        Page<SysOperateLog> logPage = baseMapper.selectPage(new Page<>(dto.getCurrent(), dto.getSize()),
                Wrappers.<SysOperateLog>lambdaQuery().orderByDesc(SysOperateLog::getCreateTime));
        return PageUtils.getPageBean(logPage);
    }
}
