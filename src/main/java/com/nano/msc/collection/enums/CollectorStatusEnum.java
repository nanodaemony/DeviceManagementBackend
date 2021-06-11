package com.nano.msc.collection.enums;

import lombok.Getter;

/**
 * Description: 数据仪器器采集状态枚举
 *
 * @version: 1.0
 * @author: nano
 * @date: 2020/12/29 18:30
 */
@Getter
public enum CollectorStatusEnum {

    /**
     * 下线
     */
    OFF_LINE(0, "下线"),

    /**
     * 在线
     */
    ON_LINE(1, "在线"),

    /**
     * 采集中
     */
    COLLECTING(2, "采集中"),


    ;

    /**
     * 状态code
     */
    Integer code;

    /**
     * 信息
     */
    String message;


    CollectorStatusEnum(Integer deviceCollectionStatus, String message) {
        this.code = deviceCollectionStatus;
        this.message = message;
    }
}
