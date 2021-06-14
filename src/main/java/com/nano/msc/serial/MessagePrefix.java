package com.nano.msc.serial;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 报文前缀
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/10 17:26
 */
public class MessagePrefix {

    /**
     * 心跳报文前缀
     */
    public static final String HEART_MESSAGE_PREFIX = "#1#";

    /**
     * 连接信息前缀
     */
    @Deprecated
    public static final String COLLECTOR_CONNECTION_MESSAGE_PREFIX = "#2#";

    /**
     * 数据报文前缀
     */
    public static final String DEVICE_DATA_MESSAGE_PREFIX = "#3#";


}
