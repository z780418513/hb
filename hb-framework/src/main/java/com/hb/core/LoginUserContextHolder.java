package com.hb.core;

import com.hb.system.model.LoginUser;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/9/1
 */
public class LoginUserContextHolder {
    private static final ThreadLocal<LoginUser> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setLoginUser(LoginUser user) {
        CONTEXT_HOLDER.set(user);
    }

    public static LoginUser getLoginUser() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearLoginUser() {
        CONTEXT_HOLDER.remove();
    }
}
