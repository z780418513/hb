package com.hb.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hb.system.entity.SysOperateLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhaochengshui
 * @description 操作日志
 * @date 2022/8/31
 */
@Mapper
public interface SysOperateLogMapper extends BaseMapper<SysOperateLog> {
}
