package com.nano.msc.collection.service;

import com.nano.msc.collection.param.ParamPad;
import com.nano.msc.common.vo.CommonResult;

/**
 * Description: 网口类仪器数据采集接口
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/16 21:36
 */
public interface EthernetDeviceDataCollectionService {

    /**
     * 保存Pad发送而来的医疗仪器信息
     */
    CommonResult saveEthernetMedicalDeviceInfoFromPad(ParamPad paramPad);

    /**
     * 开始采集仪器数据(由Pad控制)
     *
     * @param paramPad 采集器构造实体
     * @return 是否开始
     */
    CommonResult startEthernetDeviceDataCollectionPad(ParamPad paramPad);

    /**
     * 结束采集仪器数据(由Pad控制)
     *
     * @param paramPad 采集器构造实体
     * @return 是否开始
     */
    CommonResult finishEthernetDeviceDataCollectionPad(ParamPad paramPad);

    /**
     * 放弃采集仪器数据(由Pad控制)
     *
     * @param paramPad 采集器构造实体
     * @return 是否开始
     */
    CommonResult abandonEthernetDeviceDataCollectionPad(ParamPad paramPad);

}
