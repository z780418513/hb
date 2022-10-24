package com.hb.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hb.common.core.PageBean;
import com.hb.system.dto.UserDTO;
import com.hb.system.entity.SysUser;
import com.hb.system.vo.MenuVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author zhaochengshui
 * @description sys用户 服务接口
 * @date 2022/8/21
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据id删除用户信息
     *
     * @param id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * 查询用户菜单
     *
     * @param userId 用户ID
     * @return List<MenuVo>
     */
    List<MenuVo> getMenusById(Long userId);

    /**
     * 查询用户列表
     *
     * @param userDTO dto
     * @return PageBean<SysUser>
     */
    PageBean<SysUser> getUserList(UserDTO userDTO);


    /**
     * 修改用户（ID）
     *
     * @param id      用户id
     * @param userDTO dto
     * @return 成功与否
     */
    boolean modifyUser(Long id, UserDTO userDTO);

    /**
     * 上传头像
     *
     * @param file
     * @param id
     */
    String uploadAvatar(MultipartFile file, Long id);

    /**
     * 根据userid或用户名获得用户信息
     *
     * @param userId   userId
     * @param username username
     * @return SysUser
     */
    SysUser getUserInfo(Long userId, String username);
}
