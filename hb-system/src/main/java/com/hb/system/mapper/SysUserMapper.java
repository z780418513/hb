package com.hb.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hb.system.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author hanbaolaoba
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户名查询用户信息
     *
     * @param userName 用户名
     * @return User
     */
    SysUser findByUsername(@Param("userName") String userName);
}
