package com.hb.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hb.system.entity.SysFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/9/26
 */
public interface SysFileService extends IService<SysFile> {

    /**
     * 根据文件id查询文件
     *
     * @param fileId 文件id
     * @return
     */
    SysFile getFileById(Long fileId);

    /**
     * 上传文件
     * @param file
     * @return
     * @throws IOException
     */
    SysFile uploadFile(MultipartFile file) throws IOException;
}
