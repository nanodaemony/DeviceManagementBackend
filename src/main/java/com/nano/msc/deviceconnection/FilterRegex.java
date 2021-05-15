package com.nano.msc.deviceconnection;


/**
 * 正则表达式类，用于筛选过滤各个仪器接收到的数据
 * @author cz
 */
public interface FilterRegex {

    /**
     * 威浩康的数据过滤正则式
     */
    String WEI_HAO_KANG_FILTER = "AA0[168A].*";

}
