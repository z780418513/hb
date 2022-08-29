package com.hb.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.common.core.PageBean;
import com.hb.common.enums.BusinessExceptionEnum;
import com.hb.common.exceptions.BusinessException;
import com.hb.common.utils.PageUtils;
import com.hb.system.dto.UserDTO;
import com.hb.system.entity.SysUser;
import com.hb.system.mapper.SysUserMapper;
import com.hb.system.service.SysMenuService;
import com.hb.system.service.SysUserService;
import com.hb.system.vo.MenuVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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

    @Override
    public boolean deleteById(Long id) {
        SysUser sysUser = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, id));
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(BusinessExceptionEnum.INFO_NOT_FOUND);
        }
        return sysUserMapper.deleteById(id) == 1;
    }

    @Override
    public List<MenuVo> getMenusById(Long userId) {
        List<MenuVo> menus = menuService.getTreeMenuByUserId(userId);
        return menus;
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
        BeanUtils.copyProperties(userDTO,sysUser);
        sysUser.setId(id);
        return baseMapper.updateById(sysUser) == 1;
    }


}
