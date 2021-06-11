package com.nano.msc.collection.service;

import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.common.service.BaseService;
import com.nano.msc.common.vo.CommonResult;

import java.time.LocalDate;
import java.util.List;
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
     * 新增一个医疗仪器信息
     *
     * @param medicalDevice 医疗仪器
     * @return 是否成功
     */
    CommonResult<String> addMedicalDeviceInfo(InfoMedicalDevice medicalDevice);


    /**
     * 更新一个医疗仪器信息
     *
     * @param medicalDevice 医疗仪器
     * @return 是否成功
     */
    CommonResult<String> updateMedicalDeviceInfo(InfoMedicalDevice medicalDevice);


    /**
     * 获取医疗仪器信息列表(按仪器号)
     *
     * @param deviceCode 仪器号
     * @return 仪器信息列表
     */
    CommonResult<List<InfoMedicalDevice>> getMedicalDeviceInfoListByDeviceCode(int deviceCode);


    /**
     * 获取接入仪器的全部仪器个数(总的仪器个数)
     *
     * @return 仪器个数
     */
    CommonResult<Integer> getMedicalDeviceAccessInSystemCounterTotal();


    /**
     * 获取各类型仪器接入系统的个数(按仪器类别分)
     *
     * @return 个数Map
     */
    CommonResult<Map<String, Integer>> getMedicalDeviceAccessInSystemCounterByDeviceType();


    /**
     * 通过仪器号查询对应拥有的仪器列表
     *
     * @param deviceCode 仪器号
     * @return 仪器列表
     */
    CommonResult<List<String>> getMedicalDeviceSerialNumberListByDeviceCode(int deviceCode);


}
