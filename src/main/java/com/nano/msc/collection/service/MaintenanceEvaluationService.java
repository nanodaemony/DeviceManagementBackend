package com.nano.msc.collection.service;

import com.nano.msc.common.vo.CommonResult;

/**
 * Description: 维保评价服务类
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/9 15:10
 */
public interface MaintenanceEvaluationService {


    /**
     * 获取仪器维保评价信息(根据DeviceCode与SerialNumber)
     */
    CommonResult getMaintenanceEvaluationByDeviceCodeAndSerialNumber();

}
