package com.nano.msc.devicedata.utils;

/**
 * Description: 数据解析工具类
 * Usage:
 * 1. 解析字符串类型的数据为int型
 * 2. 解析字符串类型的数据为double型
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/15 18:45
 */
public class DataParseUtil {

    public static int parseIntValue(String data) {
        return Integer.parseInt(data);
    }


    public static double parseDoubleValue(String data) {
        return Double.parseDouble(data);
    }

}
