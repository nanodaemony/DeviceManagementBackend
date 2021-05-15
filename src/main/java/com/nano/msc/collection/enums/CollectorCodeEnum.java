package com.nano.msc.collection.enums;

import lombok.Getter;

/**
 * 采集器请求码的枚举
 *
 * @author nano
 * 根据项目情况来自定义
 */
@Getter
public enum CollectorCodeEnum {

    /**
     * 服务器是否在线请求
     */
    GET_SERVER_STATUS(101, "服务器在线"),

    /**
     * 上传手术信息
     */
    POST_BASIC_OPERATION_INFO(103, "上传手术基本信息"),

    /**
     * 开始手术采集数据
     */
    POST_DEVICE_COLLECTION_START(105, "某仪器开始采集数据"),

    /**
     * 仪器数据
     */
    POST_DEVICE_DATA(107, "OK."),

    /**
     * 返回对当前数据不存储了
     */
    RESPONSE_COLLECTION_DEVICE_DATA_BUT_NOT_STORE_CURRENT_DATA(108, "当前数据不被存储"),

    /**
     * 标记数据
     */
    POST_OPERATION_MARK_EVENT(109, "收到手术标记数据"),

    /**
     * 停止手术采集数据
     */
    POST_DEVICE_COLLECTION_STOP(111, "停止采集数据"),

    /**
     * 术后仪器评价信息
     */
    POST_DEVICE_EVALUATION_TABLE(113, "收到术后仪器评价数据"),

    /**
     * 采集器采集发生异常
     */
    POST_COLLECTOR_ERROR_INFO(119, "采集器异常"),

    /**
     * 仪器放弃本次采集
     */
    POST_DEVICE_COLLECTION_ABANDON(123, "仪器放弃本次采集"),


    /**
     * 查询匹配的标记事件
     */
    QUERY_MATCHED_MARK_EVENT_LIST_BY_KEY_WORD(131, "查询匹配的标记事件"),

    /**
     * 查询常用事件列表
     */
    QUERY_OFTEN_USED_MARK_EVENT_LIST(133, "查询常用事件列表"),

    /**
     * 添加新的标记事件到数据库中
     */
    POST_ADD_SOME_OTHER_NEW_MARK_EVENT(135, "添加新的标记事件至数据库"),


    /**
     * 更新一个标记事件(比如需要同步两个标记的时间)
     */
    POST_MODIFY_EVENT_MARK_TIME(137, "更新标记事件"),

    /**
     * 删除一个标记事件
     */
    DELETE_A_MARK_EVENT(139, "删除标记事件"),

    ;

    Integer code;

    String msg;

    public int getCode() {
        return code;
    }

    CollectorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
