package com.hb.init;

import com.hb.common.constants.SecurityConstants;
import com.hb.common.utils.RedisUtils;
import com.hb.system.mapper.SysRoleMapper;
import com.hb.system.model.ResourceAuth;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaochengshui
 * @description 初始化授权资源到redis中去
 * @date 2022/8/27
 */
@Component
public class ResourceAuthInit {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private RedisUtils redisUtils;

    @PostConstruct
    public void init() {
        List<ResourceAuth> resourceAuths = sysRoleMapper.queryRolesWithModule();
        redisUtils.set(SecurityConstants.RESOURCE_AUTH_REDIS_PREFIX, resourceAuths);
    }
}
