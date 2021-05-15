package com.nano.msc.collection.vo;

import lombok.Data;

/**
 * Description: 用于返回给采集器的视图实体
 * Usage:
 * 1. 包含数据及Code等.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 19:00
 */
@Data
public class CollectionVo<T> {

    /**
     * 平板端请求代号 根据项目情况自己定义
     */
    private Integer code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    public CollectionVo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
