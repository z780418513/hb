package com.hb.framwork.security.handle;

import com.hb.framwork.security.service.UserDetailsServiceImpl;
import com.hb.system.model.SysUser;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义权限注解验证

 */
@Component
public class UserPermissionEvaluator implements PermissionEvaluator {


    /**
     * hasPermission鉴权方法
     * 这里仅仅判断PreAuthorize注解中的权限表达式
     * 实际中可以根据业务需求设计数据库通过targetUrl和permission做更复杂鉴权
     * @Author Sans
     * @CreateTime 2019/10/6 18:25
     * @Param  authentication  用户身份
     * @Param  targetUrl  请求路径
     * @Param  permission 请求路径权限
     * @Return boolean 是否通过
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        // 获取用户信息
        SysUser user =(SysUser) authentication.getPrincipal();
        // 查询用户权限(这里可以将权限放入缓存中提升效率)
        Set<String> permissions = new HashSet<>();
//        List<SysMenuEntity> sysMenuEntityList = sysUserService.selectSysMenuByUserId(user.getUserId());
//        for (SysMenuEntity sysMenuEntity:sysMenuEntityList) {
//            permissions.add(sysMenuEntity.getPermission());
//        }
        // 权限对比
        if (permissions.contains(permission.toString())){
            return true;
        }
        return false;
    }
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}

