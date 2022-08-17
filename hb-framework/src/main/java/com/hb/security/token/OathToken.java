package com.hb.security.token;

import lombok.Data;

/**
 * @author zhaochengshui
 * @date 2022/8/16
 */
@Data
public class OathToken {
    /**
     * token
     */
    private String token;

    /**
     * 刷新token
     */
    private String refreshToken;

}
