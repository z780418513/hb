package com.hb.core.security.access;

import com.hb.common.enums.RoleEnum;
import com.hb.core.security.config.SecurityWhiteListConfig;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * 决策访问管理器：当用户进行认证通过后，会处理授权决策，用户判断是否通过授权
 *
 * @author zhaochengshui
 * @description 自定义决策访问管理器
 * @date 2022/8/26
 */
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {
    @Resource
    private SecurityWhiteListConfig whiteListConfig;

    /**
     * 决策是否授权通过
     * 授权失败 throw AccessDeniedException，通过直接return
     *
     * @param authentication   包含了当前的用户信息，包括拥有的权限。这里的权限来源就是前面登录时UserDetailsService中设置的authorities
     * @param configAttributes 是本次访问需要的权限,来源于 {@link CustomFilterInvocationSecurityMetadataSource#getAttributes(Object)}
     * @param object           FilterInvocation对象，可以得到request等web资源
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        // 白名单放行
        boolean inWhiteList = inWhiteList(request.getRequestURI());
        if (inWhiteList){
            return;
        }
        // 匿名用户
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException("访问失败，未得到授权");
        }
        // 认证成功后未含有角色
        if (CollectionUtils.isEmpty(authentication.getAuthorities())) {
            throw new AccessDeniedException("访问失败，未得到授权");
        }
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            // 超级管理员拥有所有权限
            if (RoleEnum.ADMIN.getRoleName().equals(grantedAuthority.getAuthority())) {
                return;
            }
            for (ConfigAttribute configAttribute : configAttributes) {
                if (grantedAuthority.getAuthority().equals(configAttribute.getAttribute())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("访问失败，未得到授权");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    private boolean inWhiteList(String requestUrl){
        List<String> whiteList = whiteListConfig.getWhiteList();
        return whiteList.contains(requestUrl);
    }

}
