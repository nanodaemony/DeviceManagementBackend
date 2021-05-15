package com.nano.msc.devicedata.service;

import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.devicedata.param.ParamDeviceDataPad;

/**
 * Description: 仪器数据服务接口
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 23:10
 */
public interface DeviceDataService {

    /**
     * 解析仪器数据并保存(处理来自Pad的数据)
     *
     * @param data 仪器数据
     * @return 是否成功
     */
    CommonResult<String> parseDataAndSaveFromPad(ParamDeviceDataPad data);


    /**
     * 解析仪器数据并保存(处理来自串口设备的数据)
     *
     * @param data 仪器数据
     * @return 是否成功
     */
    CommonResult<String> parseDataAndSaveFromSerial(String data);

    /**
     * 获取仪器历史数据
     *
     * @param collectionNumber 采集场次号
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @param page 页数
     * @param size 大小
     * @return 数据列表
     */
    CommonResult getHistoryDeviceData(int collectionNumber, int deviceCode, String serialNumber, Integer page, Integer size);

}
