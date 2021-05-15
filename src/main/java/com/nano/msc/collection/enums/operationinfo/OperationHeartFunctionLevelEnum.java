package com.nano.msc.collection.enums.operationinfo;

/**
 * Description: 手术心功能分级枚举
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/5 22:21
 */
public enum OperationHeartFunctionLevelEnum {

    /**
     * 没有输入
     */
    NO_INPUT(0, "无"),

    /**
     * I级
     */
    LEVEL1(1, "I级"),

    /**
     * II级
     */
    LEVEL2(2, "II级"),

    /**
     * III级
     */
    LEVEL3(3, "III级"),

    /**
     * IV级
     */
    LEVEL4(4, "IV级"),

    ;

    Integer level;

    String info;

    OperationHeartFunctionLevelEnum(Integer level, String info) {
        this.level = level;
        this.info = info;
    }
}
