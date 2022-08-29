package com.hb.common.core;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaochengshui
 * @description pageBean
 * @date 2022/8/21
 */
@Data
public class PageBean<T> implements Serializable {
    private static final long serialVersionUID = -3200538385338884588L;
    /**
     * 内容
     */
    private List<T> content;

    /**
     * 当前页
     */
    private Integer current;

    /**
     * 每页条数
     */
    private Integer size;

    /**
     * 总条数
     */
    private Integer total;
}
