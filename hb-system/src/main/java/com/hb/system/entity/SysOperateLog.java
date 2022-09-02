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
 * @description 操作日志实体类
 * @date 2022/8/31
 */
@Data
@TableName("sys_oper_log")
@EqualsAndHashCode(callSuper = true)
public class SysOperateLog extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;


    /**
     * 模块标题
     */
    @TableField("module")
    private String module;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @TableField("business_type")
    private Integer businessType;

    /**
     * 方法名称
     */
    @TableField("method")
    private String method;

    /**
     * 请求方式
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 操作类别（0其它 1后台用户 2=手机端用户）
     */
    @TableField("operator_type")
    private Integer operatorType;

    /**
     * 请求URL
     */
    @TableField("operator_url")
    private String operatorUrl;

    /**
     * 主机地址
     */
    @TableField("operator_ip")
    private String operatorIp;

    /**
     * 操作地点
     */
    @TableField("operator_location")
    private String operatorLocation;

    /**
     * 请求参数
     */
    @TableField("operator_param")
    private String operatorParam;

    /**
     * 返回参数
     */
    @TableField("json_result")
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    @TableField("status")
    private Integer status;

    /**
     * 错误消息
     */
    @TableField("error_msg")
    private String errorMsg;

}
