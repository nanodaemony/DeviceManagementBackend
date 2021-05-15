package com.nano.msc.devicedata.parser;


/**
 * 解析采集器上传而来的仪器监测数据接口
 *
 * @author nano
 */
public interface DeviceDataParser<T> {

    /**
     * 解析仪器原始数据并返回解析得到的仪器对象
     *
     * @param deviceData 原始数据
     * @return 解析后的仪器对象
     */
    T parseDeviceData(String deviceData);

}
