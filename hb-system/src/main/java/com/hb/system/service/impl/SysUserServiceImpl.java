package com.hb.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.common.core.PageBean;
import com.hb.common.enums.BusinessExceptionEnum;
import com.hb.common.exceptions.BusinessException;
import com.hb.common.utils.PageUtils;
import com.hb.system.config.DefaultUserConfig;
import com.hb.system.dto.UserDTO;
import com.hb.system.entity.SysFile;
import com.hb.system.entity.SysUser;
import com.hb.system.mapper.SysUserMapper;
import com.hb.system.service.SysFileService;
import com.hb.system.service.SysMenuService;
import com.hb.system.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/8/21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysMenuService menuService;
    @Resource
    private SysFileService sysFileService;
    @Resource
    private DefaultUserConfig defaultUserConfig;

    @Override
    public boolean deleteById(Long id) {
        SysUser sysUser = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, id));
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(BusinessExceptionEnum.INFO_NOT_FOUND);
        }
        return sysUserMapper.deleteById(id) == 1;
    }


    @Override
    public PageBean<SysUser> getUserList(UserDTO userDTO) {
        Page<SysUser> userPage = baseMapper.selectPage(new Page<>(userDTO.getCurrent(), userDTO.getSize()),
                Wrappers.<SysUser>lambdaQuery()
                        .like(StringUtils.isNotBlank(userDTO.getUsername()), SysUser::getUsername, userDTO.getUsername())
                        .and(StringUtils.isNotBlank(userDTO.getKeyword()), wrapper -> wrapper
                                .like(StringUtils.isNotBlank(userDTO.getKeyword()), SysUser::getRealName, userDTO.getKeyword()).or()
                                .like(StringUtils.isNotBlank(userDTO.getKeyword()), SysUser::getMobile, userDTO.getKeyword()))
                        .orderByDesc(SysUser::getCreateTime));
        PageBean<SysUser> pageBean = PageUtils.getPageBean(userPage);
        return pageBean;
    }

    @Override
    public boolean modifyUser(Long id, UserDTO userDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDTO, sysUser);
        sysUser.setId(id);
        return baseMapper.updateById(sysUser) == 1;
    }

    @Override
    public String uploadAvatar(MultipartFile file, Long id) {
        SysFile sysFile = null;
        try {
            sysFile = sysFileService.uploadFile(file);
        } catch (IOException e) {
            log.error("SysUserServiceImpl uploadAvatar msg: 上传头像失败");
        }
        if (sysFile != null && StringUtils.isNotBlank(sysFile.getOssUrl())) {
            SysUser sysUser = new SysUser();
            sysUser.setId(id);
            sysUser.setAvatar(sysFile.getOssUrl());
            baseMapper.updateById(sysUser);
            return sysFile.getOssUrl();
        }
        return "";
    }

    @Override
    public SysUser getUserInfo(Long userId, String username) {
        SysUser sysUser = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .eq(userId != null, SysUser::getId, userId)
                .eq(StringUtils.isNotBlank(username), SysUser::getUsername, username));
        return sysUser;
    }

    @Override
    public SysUser getUserInfoByMobile(String mobile) {
        if (StringUtils.isBlank(mobile)){
            throw new BusinessException("mobile can`t be blank");
        }
        SysUser sysUser = baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getMobile, mobile));
        return sysUser;
    }

    @Override
    public SysUser getUserInfoByUserName(String username) {
        SysUser sysUser = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .eq(StringUtils.isNotBlank(username), SysUser::getUsername, username));
        return sysUser;
    }

    @Override
    public SysUser getUserInfoById(Long userId) {
        SysUser sysUser = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .eq(userId != null, SysUser::getId, userId));
        return sysUser;
    }


}
