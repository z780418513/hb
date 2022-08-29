package com.hb.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hb.common.core.PageBean;

/**
 * @author zhaochengshui
 * @description Page工具类
 * @date 2022/8/21
 */
public class PageUtils {

    public static <T> PageBean<T> getPageBean(IPage<T> page){
        PageBean<T> pageBean=new PageBean<>();
        pageBean.setCurrent(Math.toIntExact(page.getCurrent()));
        pageBean.setSize(Math.toIntExact(page.getSize()));
        pageBean.setTotal(Math.toIntExact(page.getTotal()));
        pageBean.setContent(page.getRecords());
        return pageBean;
    }
}
