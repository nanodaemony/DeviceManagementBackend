package com.nano.msc.collection.service;

import com.nano.msc.common.vo.CommonResult;

/**
 * Description: 追踪评价服务
 * Usage:
 * 1. 获取全部仪器的追踪评价信息(按DeviceCode统计)
 * 2. 获取全部仪器的追踪评价信息(按DeviceCode与SerialNumber统计)
 * 3. 获取某大类(麻醉机、麻醉深度监测仪等)类型仪器的追踪评价信息(按DeviceCode统计)
 * 4. 获取某大类(麻醉机、麻醉深度监测仪等)类型仪器的追踪评价信息(按DeviceCode与SerialNumber统计)
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/8 20:07
 */
public interface FollowUpEvaluationService {

    /**
     * 获取全部仪器的追踪评价信息(按DeviceCode与SerialNumber统计)
     */
    CommonResult getDeviceFollowUpEvaluationCoverDeviceAllByDeviceCodeAndSerialNumber();

}
