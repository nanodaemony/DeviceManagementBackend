package com.nano.msc.collection.service;

import com.nano.msc.common.vo.CommonResult;

/**
 * Description: 仪器卡片相关服务
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/31 16:39
 */
public interface DeviceCardService {

    /**
     * 获取某型号仪器的仪器卡片展示信息(某仪器型号)
     * @param deviceCode 仪器号
     * @return 仪器信息
     */
    CommonResult getDeviceCardInfoForOneDeviceModel(Integer deviceCode);


    /**
     * 获取某型号仪器的仪器卡片展示信息(某仪器型号下一个具体仪器)
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @return 仪器信息
     */
    CommonResult getDeviceCardInfoForOneDeviceDetail(Integer deviceCode, String serialNumber);

}
