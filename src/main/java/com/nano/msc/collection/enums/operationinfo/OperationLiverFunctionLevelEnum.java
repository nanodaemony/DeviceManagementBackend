package com.nano.msc.collection.enums.operationinfo;

/**
 * Description: 手术肝功能分级枚举
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/5 22:21
 */
public enum OperationLiverFunctionLevelEnum {

    /**
     * 没有输入
     */
    NO_INPUT(0, "无"),

    /**
     * I级
     */
    LEVEL_A(1, "A级"),

    /**
     * II级
     */
    LEVEL_B(2, "B级"),

    /**
     * III级
     */
    LEVEL_C(3, "C级"),

    ;

    Integer level;

    String info;

    OperationLiverFunctionLevelEnum(Integer level, String info) {
        this.level = level;
        this.info = info;
    }
}
