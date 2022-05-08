package com.zhao.hb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.hb.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户信息
     *
     * @param userName 用户名
     * @return User
     */
    User findByUsername(@Param("userName") String userName);
}
