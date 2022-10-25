package com.hb.web.system.controller;

import com.hb.common.core.PageBean;
import com.hb.common.core.Result;
import com.hb.common.core.ValidGroup;
import com.hb.common.enums.BusinessTypeEnum;
import com.hb.core.annotations.WebLog;
import com.hb.system.dto.MobileRegisterDTO;
import com.hb.system.dto.UserDTO;
import com.hb.system.dto.UserRegisterDTO;
import com.hb.system.entity.SysUser;
import com.hb.system.service.RegisterService;
import com.hb.system.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/8/21
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private RegisterService mobileRegisterService;
    @Resource
    private RegisterService userPasswordRegisterService;


    /**
     * 查询用户列表
     *
     * @return
     */
    @GetMapping("/list")
    public Result getUserList(@Validated(value = {ValidGroup.Page.class}) UserDTO userDTO) {
        PageBean<SysUser> userList = sysUserService.getUserList(userDTO);
        return Result.success(userList);
    }

    /**
     * 新增用户
     *
     * @param userDTO
     * @return
     */
    @WebLog(module = "用户模块", businessType = BusinessTypeEnum.INSERT)
    @PostMapping("/add")
    public Result addUser(@RequestBody UserDTO userDTO) {
        SysUser user = new SysUser();
        if (Objects.isNull(userDTO.getEnable())) {
            userDTO.setEnable(true);
        }
        BeanUtils.copyProperties(userDTO, user);
        user.setPassword(passwordEncoder.encode("123456"));
        boolean save = sysUserService.save(user);
        return Result.success(save);
    }

    /**
     * 修改用户
     *
     * @param id
     * @param userDTO
     * @return
     */
    @WebLog(module = "用户模块", businessType = BusinessTypeEnum.UPDATE)
    @PutMapping("/modify/{id}")
    public Result modifyUser(@PathVariable("id") Long id,
                             @RequestBody UserDTO userDTO) {
        boolean update = sysUserService.modifyUser(id, userDTO);
        return Result.success(update);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @WebLog(module = "用户模块", businessType = BusinessTypeEnum.DELETED)
    @DeleteMapping("/delete/{id}")
    public Result deleteUser(@PathVariable("id") Long id) {
        if (sysUserService.deleteById(id)) {
            return Result.success();
        }
        return Result.error();
    }


    /**
     * 根据用户ID查询用户对应菜单
     *
     * @param userId
     * @return
     */
    @GetMapping("/menus")
    public Result getMenus(@RequestParam("id") Long userId) {
        return Result.success(sysUserService.getMenusById(userId));
    }


    /**
     * 上传用户头像
     *
     * @param file 文件
     * @param id   用户id
     * @return 上传后地址
     */
    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(@RequestParam("file") MultipartFile file,
                               @RequestParam("id") Long id) {
        String avatarUrl = sysUserService.uploadAvatar(file, id);
        return Result.success(avatarUrl);
    }

    /**
     * 根据用户id或用户名获取用户信息
     *
     * @param userId   用户id
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestParam(value = "id", required = false) Long userId,
                              @RequestParam(value = "username", required = false) String username) {
        SysUser userInfo = sysUserService.getUserInfo(userId, username);
        return Result.success(userInfo);
    }

    /**
     * 注册用户
     *
     * @param registerBody
     * @return 用户信息
     */
    @PostMapping("/register")
    public Result register(@Validated(value = {ValidGroup.Add.class}) @RequestBody UserRegisterDTO registerBody) {
        SysUser userInfo = userPasswordRegisterService.register(registerBody);
        return Result.success(userInfo);
    }

    /**
     * 注册用户（手机号）
     *
     * @param registerBody
     * @return 用户信息
     */
    @PostMapping("/register/mobile")
    public Result register(@Validated(value = {ValidGroup.Add.class}) @RequestBody MobileRegisterDTO registerBody) {
        SysUser userInfo = mobileRegisterService.register(registerBody);
        return Result.success(userInfo);
    }
}
