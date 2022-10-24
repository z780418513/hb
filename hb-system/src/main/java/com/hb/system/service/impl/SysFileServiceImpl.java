package com.hb.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.hb.common.exceptions.FileException;
import com.hb.oss.service.AliyunOssService;
import com.hb.system.entity.SysFile;
import com.hb.system.mapper.SysFileMapper;
import com.hb.system.service.SysFileService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/9/26
 */
@Service
@ConditionalOnProperty(prefix = "aliyun", name = "enable-oss", havingValue = "true")
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    private final String DEFAULT_BUCKET = "hb-admin-oss";
    private final ArrayList<String> imgTypeList = Lists.newArrayList("bmp", "jpg", "jpeg", "png");

    @Resource
    private AliyunOssService aliyunOssService;


    @Override
    public SysFile getFileById(Long fileId) {
        SysFile sysFile = baseMapper.selectById(fileId);
        if (sysFile == null) {
            throw new FileException("file can't be find with fileId :{}" + fileId);
        }
        return sysFile;
    }

    @Override
    public SysFile uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new FileException("file is empty ,please check !!!");
        }
        String md5 = DigestUtils.md5Hex(file.getBytes());
        // md5已有相同的，直接返回，不存储
        SysFile preSysFile = preGetSysFileByMd5(md5);
        if (preSysFile != null) {
            return preSysFile;
        }
        String originalFilename = file.getOriginalFilename();
        String upLoadPath = aliyunOssService.upLoadFile(file.getInputStream(), originalFilename, DEFAULT_BUCKET);


        String fileName = originalFilename.split("\\.")[0];
        String fileType = originalFilename.split("\\.")[1];

        return insertSysFile(fileName, file.getSize(), md5, fileType, file.getContentType(), upLoadPath);
    }

    private boolean checkFileIsImg(String fileType) {
        return imgTypeList.contains(fileType);
    }

    private SysFile preGetSysFileByMd5(String md5) {
        return baseMapper.selectOne(Wrappers.<SysFile>lambdaQuery().eq(SysFile::getMd5, md5));
    }

    private SysFile insertSysFile(String fileName, Long fileSize, String md5, String fileType, String contentType, String ossUrl) {
        SysFile sysFile = new SysFile();
        // 文件长度太长就切断
        sysFile.setFileName(fileName.length() >= 255 ? fileName.substring(0, 255) : fileName);
        sysFile.setFileSize(fileSize);
        sysFile.setMd5(md5);
        sysFile.setImg(checkFileIsImg(fileType));
        sysFile.setContentType(contentType);
        sysFile.setOssUrl(ossUrl);
        baseMapper.insert(sysFile);
        return sysFile;
    }
}
