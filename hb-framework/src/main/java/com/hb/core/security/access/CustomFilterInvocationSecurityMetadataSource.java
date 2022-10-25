package com.hb.core.security.access;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hb.common.constants.SecurityConstants;
import com.hb.common.utils.RedisUtils;
import com.hb.system.model.ResourceAuth;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/8/26
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Resource
    private RedisUtils redisUtils;


    //TODO 白名单 黑名单

    /**
     * 方法返回本次访问需要的权限，可以有多个权限。
     * 在上面的实现中如果没有匹配的url直接返回null，也就是没有配置权限的url默认都为白名单，
     * 想要换成默认是黑名单只要修改这里即可。
     *
     * @param object the object being secured
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 匿名用户返回 ROLE_ANONYMOUS，不然会直接放行，不走CustomAccessDecisionManager
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            SecurityConfig roleAnonymous = new SecurityConfig("ROLE_ANONYMOUS");
            ArrayList<ConfigAttribute> list = new ArrayList<>();
            list.add(roleAnonymous);
            return list;
        }
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        String module = (requestUrl.split("/"))[1];
        Object resourceAuth = redisUtils.get(SecurityConstants.RESOURCE_AUTH_REDIS_PREFIX);
        if (StringUtils.isNotBlank(module) && !Objects.isNull(resourceAuth)) {
            List<ResourceAuth> auths = JSONObject.parseArray(resourceAuth.toString(), ResourceAuth.class);
            Collection<ConfigAttribute> roles = auths.stream()
                    .filter(auth -> auth.getModule().equals(module))
                    .map(auth -> new SecurityConfig(auth.getRole()))
                    .collect(Collectors.toCollection(ArrayList::new));
            return CollectionUtils.isEmpty(roles) ? null : roles;
        }
        return null;
    }


    /**
     * 方法如果返回了所有定义的权限资源，Spring Security会在启动时校验每个ConfigAttribute是否配置正确，
     * 不需要校验直接返回null。
     *
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 方法返回类对象是否支持校验，web项目一般使用FilterInvocation来判断，或者直接返回true。
     *
     * @param clazz the class that is being queried
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}

