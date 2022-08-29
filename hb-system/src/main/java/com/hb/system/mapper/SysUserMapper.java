package com.hb.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hb.system.entity.SysUser;
import com.hb.system.model.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhaochengshui
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户名查询用户信息
     *
     * @param userName 用户名
     * @return User
     */
    LoginUser findByUsername(@Param("userName") String userName);
}
