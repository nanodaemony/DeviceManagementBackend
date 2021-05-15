package com.nano.msc.collection.enums.operationinfo;

/**
 * Description: 手术肺功能分级枚举
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/5 22:21
 */
public enum OperationLungFunctionLevelEnum {

    /**
     * 没有输入
     */
    NO_INPUT(0, "无"),

    /**
     * I级
     */
    LEVEL1(1, "1级"),

    /**
     * II级
     */
    LEVEL2(2, "2级"),

    /**
     * III级
     */
    LEVEL3(3, "3级"),

    /**
     * IV级
     */
    LEVEL4(4, "4级"),

    ;

    Integer level;

    String info;

    OperationLungFunctionLevelEnum(Integer level, String info) {
        this.level = level;
        this.info = info;
    }
}
