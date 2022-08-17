package com.hb.security.token;

import lombok.Data;

/**
 * @author zhaochengshui
 * @description 刷新token
 * @date 2022/8/16
 */
@Data
public class RefreshOathToken {
    /**
     * 刷新token
     */
    private String refreshToken;

    /**
     * 用户名
     */
    private String username;
}
