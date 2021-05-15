package com.nano.msc.system.log.enums;

import lombok.Getter;

/**
 * 系统日志类型枚举
 * @author nano
 */
@Getter
public enum SystemLogEnum {

    /**
     * 调试信息
     */
    DEBUG(0, "DEBUG"),

    /**
     * 日常信息
     */
    INFO(1, "INFO"),

    /**
     * 错误
     */
    ERROR(2, "ERROR"),
    ;

    private Integer code;

    private String msg;

    SystemLogEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
