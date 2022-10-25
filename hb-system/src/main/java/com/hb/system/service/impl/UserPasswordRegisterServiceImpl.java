package com.hb.system.service.impl;

import com.hb.common.enums.BusinessExceptionEnum;
import com.hb.common.enums.RegisterTypeEnum;
import com.hb.common.exceptions.BusinessException;
import com.hb.system.config.DefaultUserConfig;
import com.hb.system.dto.UserRegisterDTO;
import com.hb.system.entity.SysUser;
import com.hb.system.model.RegisterBody;
import com.hb.system.service.RegisterService;
import com.hb.system.service.SysUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zhaochengshui
 * @description 用户名密码注册服务类
 * @date 2022/10/25
 */
@Service("userPasswordRegisterService")
public class UserPasswordRegisterServiceImpl implements RegisterService {
    @Resource
    private DefaultUserConfig userConfig;
    @Resource
    private SysUserService sysUserService;

    @Override
    public void checkRegisterParams(RegisterBody registerBody) {
        UserRegisterDTO registerDTO = (UserRegisterDTO) registerBody;
        if (!RegisterTypeEnum.USERNAME_PASSWORD.getCode().equals(registerDTO.getRegisterType())) {
            throw new BusinessException(BusinessExceptionEnum.REGISTER_TYPE_ERROR);
        }
        SysUser user = sysUserService.getUserInfo(null, registerDTO.getUsername());
        if (Objects.nonNull(user)) {
            throw new BusinessException("this username has be used");
        }

    }

    @Override
    public SysUser generateUser(RegisterBody registerBody) {
        UserRegisterDTO registerDTO = (UserRegisterDTO) registerBody;
        SysUser sysUser = new SysUser();
        sysUser.setUsername(registerDTO.getUsername());
        sysUser.setPassword(new BCryptPasswordEncoder().encode(registerDTO.getPassword()));
        sysUser.setNickName(userConfig.getDefaultNickName());
        sysUser.setAvatar(userConfig.getDefaultAvatar());
        return sysUser;
    }

    @Override
    public SysUser register(RegisterBody registerBody) {
        // 校验参数
        checkRegisterParams(registerBody);
        // 封装user
        SysUser sysUser = generateUser(registerBody);
        // 写入数据库
        sysUserService.getBaseMapper().insert(sysUser);
        return sysUser;
    }
}
