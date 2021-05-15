package com.nano.msc.common.exceptions;


import com.nano.msc.common.enums.ExceptionEnum;

/**
 * 断言处理类，用于抛出各种API异常
 * @author nano
 */
public class ExceptionAsserts {

    public static void fail(String message) {
        throw new CommonException(message);
    }

    public static void fail(ExceptionEnum exceptionEnum) {
        throw new CommonException(exceptionEnum);
    }

    public static void fail(ExceptionEnum exceptionEnum, String message) {
        throw new CommonException(exceptionEnum, message);
    }
}
