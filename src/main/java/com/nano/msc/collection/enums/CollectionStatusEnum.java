package com.nano.msc.collection.enums;

import lombok.Getter;

/**
 * Description: 仪器采集状态枚举
 *
 * @version: 1.0
 * @author: nano
 * @date: 2020/12/29 18:30
 */
@Getter
public enum CollectionStatusEnum {

    /**
     * 等待开始
     */
    WAITING(0, "等待开始采集"),

    /**
     * 采集中
     */
    COLLECTING(1, "正在采集中"),

    /**
     * 采集完成
     */
    FINISHED(2, "采集完成"),

    /**
     * 失效
     */
    ABANDON(-1, "无效采集")

    ;

    /**
     * 状态code
     */
    Integer code;

    /**
     * 信息
     */
    String message;


    CollectionStatusEnum(Integer deviceCollectionStatus, String message) {
        this.code = deviceCollectionStatus;
        this.message = message;
    }
}
