package com.hb.common.core;


import com.hb.common.constants.SysConstant;
import com.hb.common.enums.BusinessExceptionEnum;
import com.hb.common.enums.SysExceptionEnum;
import com.hb.common.exceptions.BaseException;
import lombok.Data;

import java.io.Serializable;

/**
 * 操作消息提醒
 *
 * @author hanbaolaoba
 */
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 数据对象
     */
    private Object data;

    /**
     * 初始化一个新创建的 Result 对象，使其表示一个空消息。
     */
    public Result() {
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;

    }

    //-------------------SUCCESS-------------------

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static Result success() {
        return Result.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static Result success(Object data) {
        return Result.success("操作成功", data);
    }



    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static Result success(String msg, Object data) {
        return new Result(SysConstant.RESULT_SUCCESS, msg, data);
    }


    //-------------------ERROR-------------------

    /**
     * 返回错误消息
     *
     * @return error
     */
    public static Result error() {
        return Result.error("操作失败",null);
    }

    /**
     * 返回错误消息
     *
     * @param exceptionEnum 业务异常枚举
     * @return error
     */
    public static Result error(BusinessExceptionEnum exceptionEnum) {
        return Result.error(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    /**
     * 返回错误消息
     *
     * @param exceptionEnum 系统异常
     * @return error
     */
    public static Result error(SysExceptionEnum exceptionEnum) {
        return Result.error(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    /**
     * 返回错误消息
     *
     * @param e 基础异常
     * @return error
     */
    public static Result error(BaseException e) {
        return Result.error(e.getExceptionCode(), e.getMessage());
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static Result error(String msg) {
        return Result.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static Result error(String msg, Object data) {
        return new Result(SysConstant.RESULT_FAIL, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static Result error(int code, String msg) {
        return new Result(code, msg, null);
    }

}
