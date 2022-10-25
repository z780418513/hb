package com.hb.system.service.impl;

import com.hb.common.enums.BusinessExceptionEnum;
import com.hb.common.enums.RegisterTypeEnum;
import com.hb.common.exceptions.BusinessException;
import com.hb.common.utils.IdUtils;
import com.hb.system.config.DefaultUserConfig;
import com.hb.system.dto.MobileRegisterDTO;
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
 * @description 手机号注册服务类
 * @date 2022/10/25
 */
@Service("mobileRegisterService")
public class MobileRegisterServiceImpl implements RegisterService {

    @Resource
    private DefaultUserConfig userConfig;
    @Resource
    private SysUserService sysUserService;

    @Override
    public void checkRegisterParams(RegisterBody registerBody) {
        MobileRegisterDTO registerDTO = (MobileRegisterDTO) registerBody;
        if (!RegisterTypeEnum.MOBILE.getCode().equals(registerDTO.getRegisterType())) {
            throw new BusinessException(BusinessExceptionEnum.REGISTER_TYPE_ERROR);
        }
        // TODO 校验短信验证码
        SysUser user = sysUserService.getUserInfoByMobile(registerDTO.getMobile());
        if (Objects.nonNull(user)) {
            throw new BusinessException("this mobile have be used");
        }

    }

    @Override
    public SysUser generateUser(RegisterBody registerBody) {
        MobileRegisterDTO registerDTO = (MobileRegisterDTO) registerBody;
        SysUser sysUser = new SysUser();
        sysUser.setUsername(IdUtils.simpleUUID());
        sysUser.setPassword(new BCryptPasswordEncoder().encode(userConfig.getDefaultPassword()));
        sysUser.setMobile(registerDTO.getMobile());
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
