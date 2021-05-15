package com.nano.msc.common.enums;

import lombok.Getter;

/**
 * 错误码枚举
 * @author nano
 */
@Getter
public enum ExceptionEnum {

    /**
     * 未知错误
     */
    UNKNOWN_ERROR(-1, "未知错误"),

    /**
     * 数据格式错误
     */
    DATA_FORMAT_ERROR(-2, "数据格式错误"),

    /**
     * 请求的TYPE是错误的
     */
    CODE_ERROR(-3, "请求的Code是错误的"),

    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(-4, "数据不存在"),

    /**
     * 请求参数不存在
     */
    REQUEST_PARAMETER_DOES_NOT_EXIST(-5, "请求参数不存在"),

    /**
     * 数据已经存在
     */
    DATA_EXISTED(-6, "数据已经存在"),

    /**
     * 该数据已经处于结束状态，不可更改
     */
    DATA_STATE_FINISHED(-7, "该数据已经处于结束状态，不可更改"),

    /**
     * 没有前置数据
     */
    NO_LEADING_DATA(-8, "没有前置数据"),

    /**
     * 更新失败，ID字段错误或为空
     */
    UPDATE_ID_ERROR(-9, "更新失败，ID字段错误或为空"),

    /**
     * 未知数据类型
     */
    UNKNOWN_DATA_TYPE(-10, "未知数据类型"),

    /**
     * 未知请求Code
     */
    UNKNOWN_REQUEST_CODE(-11, "未知请求Code"),

    /**
     * 数据保存失败
     */
    DATA_SAVE_ERROR(-12, "数据保存失败"),

    /**
     * 数据更新失败
     */
    DATA_UPDATE_ERROR(-13, "数据更新失败"),


    /**
     * 数据删除失败
     */
    DATA_DELETE_ERROR(-14, "数据删除失败"),


    /**
     * 数据解析异常
     */
    DATA_PARSE_EXCEPTION(-14, "数据解析异常"),

    /**
     * 采集器状态错误
     */
    COLLECTOR_STATUS_ERROR(-15, "状态异常")

    ;

    /**
     * 错误码
     */
    Integer errorCode;

    /**
     * 错误消息
     */
    String message;

    ExceptionEnum(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String toString() {
        return  "errorCode: " + errorCode +
                "\nmessage: " + message;
    }
}
