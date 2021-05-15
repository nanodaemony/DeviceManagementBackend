package com.nano.msc.devicedata.param;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 由数据采集APP上传到服务器的数据实体参数类
 * Usage:
 *     1. 根据这个格式将仪器数据解析成仪器号+仪器JSON的格式
 *     2. 解析器根据DeviceCode调用不同的数据解析器进行数据解析.
 *
 * @author cz
 */
@Data
@NoArgsConstructor
public class ParamDeviceDataPad {
    /**
     * 仪器号
     */
    private Integer deviceCode;

    /**
     * 采集场次
     */
    private Integer collectionNumber;

    /**
     * 仪器数据JSON类型
     */
    private String deviceData;

    public ParamDeviceDataPad(Integer deviceCode, Integer collectionNumber, String deviceData) {
        this.deviceCode = deviceCode;
        this.collectionNumber = collectionNumber;
        this.deviceData = deviceData;
    }
}
