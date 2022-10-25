package com.hb.system.service;

import com.hb.system.entity.SysUser;
import com.hb.system.model.RegisterBody;

/**
 * @author zhaochengshui
 * @description 用户注册服务类
 * @date 2022/10/25
 */
public interface RegisterService {

    /**
     * 校验参数
     *
     * @param registerBody
     */
    void checkRegisterParams(RegisterBody registerBody);

    /**
     * 生成sysuser对象
     *
     * @param registerBody
     * @return SysUser
     */
    SysUser generateUser(RegisterBody registerBody);

    /**
     * 注册用户
     *
     * @param registerBody
     * @return
     */
    SysUser register(RegisterBody registerBody);
}
