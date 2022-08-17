package com.hb.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hb.common.constants.SecurityConstants;
import com.hb.common.enums.BusinessExceptionEnum;
import com.hb.common.exceptions.BusinessException;
import com.hb.common.utils.JwtUtil;
import com.hb.common.utils.RedisUtil;
import com.hb.security.token.OathToken;
import com.hb.system.model.SysUser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * tokenService 服务类
 *
 * @author zhaochengshui
 */
@Service
@Data
@Slf4j
public class TokenService {

    /**
     * 令牌头部(默认'')
     */
    @Value("${jwt.token-header:}")
    private String tokenHeader;

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private JwtUtil jwtUtil;


    /**
     * 登录并获得token
     *
     * @param username
     * @param password
     * @return
     */
    public OathToken login(String username, String password) {
        //使用用户名密码进行登录验证
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //生成JWT
        SysUser sysUser = (SysUser) authentication.getPrincipal();
        OathToken oathToken = getOathToken(sysUser.getUsername());
        redisUtil.set(SecurityConstants.TOKEN_REDIS_PREFIX + username, oathToken.getToken());
        return oathToken;
    }

    /**
     * 刷新token
     *
     * @param refreshToken 刷新令牌
     * @param username     用户名
     * @return OathToken
     */
    public OathToken refreshToken(String refreshToken, String username) {
        if (!validateToken(refreshToken)) {
            throw new BusinessException(BusinessExceptionEnum.TOKEN_VALID_FAIL);
        }
        return getOathToken(username);

    }

    /**
     * 生成OathToken
     *
     * @param username 用户名
     * @return OathToken
     */
    public OathToken getOathToken(String username) {
        OathToken oathToken = new OathToken();
        HashMap<String, Object> tokenParams = new HashMap<>();
        // 刷新令牌
        oathToken.setRefreshToken(this.tokenHeader + jwtUtil.generateRefreshToken(tokenParams));
        tokenParams.put("username", username);
        // 认证令牌
        oathToken.setToken(this.tokenHeader + jwtUtil.generateAccessToken(tokenParams));
        return oathToken;
    }

    /**
     * 校验token
     *
     * @param token 令牌
     * @return boolean
     */
    public boolean validateToken(String token) {
        if (StringUtils.isBlank(token)){
            log.debug("token为空");
            return false;
        }
        if (!token.startsWith(tokenHeader)) {
            log.debug("非法token:[ {} ]", token);
            return false;
        }
        return jwtUtil.verifierToken(token.substring(tokenHeader.length()));
    }


}
