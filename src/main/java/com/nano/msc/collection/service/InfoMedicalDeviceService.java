package com.nano.msc.collection.service;

import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.common.service.BaseService;
import com.nano.msc.common.vo.CommonResult;

import java.util.Map;

/**
 * Description: 医疗仪器服务
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:17
 */
public interface InfoMedicalDeviceService extends BaseService<InfoMedicalDevice, Integer> {


    /**
     * 获取各类型仪器接入系统的个数
     *
     * @return 个数Map
     */
    CommonResult<Map<String, Integer>> getDeviceSystemAccessInTypeCounter();


}
