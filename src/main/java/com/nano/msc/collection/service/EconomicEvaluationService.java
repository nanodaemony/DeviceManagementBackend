package com.nano.msc.collection.service;

import com.nano.msc.common.vo.CommonResult;

/**
 * Description: 经济性评价接口
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/9 16:43
 */
public interface EconomicEvaluationService {

    /**
     * 获取全部仪器的经济性评价信息(按DeviceCode与SerialNumber统计)
     */
    CommonResult getDeviceEconomicEvaluationCoverDeviceAllByDeviceCodeAndSerialNumber();

}
