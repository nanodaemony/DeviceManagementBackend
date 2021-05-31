package com.nano.msc.devicedata.manager;


import java.util.Map;

/**
 * Description: 仪器数据管理器
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/31 21:45
 */
public interface DeviceDataManager<T> {

    /**
     * 解析仪器原始数据并返回解析得到的仪器对象
     *
     * @param deviceData 原始数据
     * @return 解析后的仪器对象
     */
    T parseDeviceData(String deviceData);

    /**
     * 通过采集场次号与仪器序列号查询仪器历史数据
     *
     * @param collectionNumber 采集场次号
     * @param serialNumber 仪器序列号
     * @return 返回的仪器数据信息
     */
    Map<String, Object> getDeviceHistoryData(int collectionNumber, String serialNumber);

}
