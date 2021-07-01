package com.nano.msc.devicedata.utils;

import java.math.BigInteger;
import java.text.DecimalFormat;

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

    // 保留三位小数
    private static final DecimalFormat DF = new DecimalFormat("#.000");

    public static int parseIntValue(String data) {
        return Integer.parseInt(data);
    }


    public static double parseDoubleValue(String data) {
        return Double.parseDouble(data);
    }

    /**
     * 通过十六进制字符串计算其浮点值
     *
     * @param str 十六进制字符串
     * @return 对应的浮点值
     */
    public static double getDoubleValueByHexString(String str) {
        if (Float.isNaN(Float.intBitsToFloat(new BigInteger(str, 16).intValue()))) {
            return -1000;
        }
        return Double.parseDouble(DF.format(Float.intBitsToFloat(new BigInteger(str, 16).intValue())));
    }
}
