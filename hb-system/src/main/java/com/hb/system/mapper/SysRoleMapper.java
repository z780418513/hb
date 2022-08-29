package com.hb.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hb.system.entity.SysRole;
import com.hb.system.model.ResourceAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhaochengshui
 * @description RoleMapper
 * @date 2022/8/24
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户id查询用户所有角色
     *
     * @param userId 用户ID
     * @return 所有角色
     */
    List<SysRole> queryRolesByUserId(@Param("id") Long userId);


    /**
     * 查询所有模块对应的角色
     *
     * @return List<RoleWithModule>
     */
    List<ResourceAuth> queryRolesWithModule();
}
