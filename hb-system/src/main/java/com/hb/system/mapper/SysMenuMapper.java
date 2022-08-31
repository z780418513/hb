package com.hb.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hb.system.entity.SysMenu;
import com.hb.system.vo.MenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhaochengshui
 * @description 菜单mapper
 * @date 2022/8/24
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户ID 查询用户菜单
     *
     * @param id 用户id
     * @return List<MenuVo>
     */
    List<MenuVo> getTreeMenuByUserId(@Param("id") Long id);


}