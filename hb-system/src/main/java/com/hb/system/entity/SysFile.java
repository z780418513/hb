package com.hb.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hb.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhaochengshui
 */
@Data
@TableName("sys_file")
@EqualsAndHashCode(callSuper = true)
public class SysFile extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 原始文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 是否图片 1是 0不是
     */
    @TableField("is_img")
    private boolean isImg;


    /**
     * 上传文件类型
     */
    @TableField("content_type")
    private String contentType;

    /**
     * 文件大小
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 文件md5
     */
    @TableField("md5")
    private String md5;

    /**
     * oss文件上传路径
     */
    @TableField("oss_url")
    private String ossUrl;

}
