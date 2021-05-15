package com.nano.msc.serial;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 串口仪器采集静态信息
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/10 17:26
 */
public class SerialStaticInfo {

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




    public static Map<String, SerialDeviceEntity> collectionMap = new HashMap<>();


    /**
     * 构造返回给采集器的采集场次号格式字符串
     * @param collectionNumber 采集场次号
     */
    public static String formatCollectionNumberForCollector(int collectionNumber) {
        return "#2#" + collectionNumber + "#";
    }
}
