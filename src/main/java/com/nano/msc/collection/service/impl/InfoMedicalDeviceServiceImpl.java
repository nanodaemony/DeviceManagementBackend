package com.nano.msc.collection.service.impl;

import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.enums.DeviceTypeEnum;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.InfoMedicalDeviceService;
import com.nano.msc.common.service.BaseServiceImpl;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

/**
 * Description: 医疗仪器服务实现类
 * Usage:
 * @see #getDeviceSystemAccessInTypeCounter() 统计已经接入系统的仪器种类数量
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:42
 */
@Slf4j
@Service
public class InfoMedicalDeviceServiceImpl extends BaseServiceImpl<InfoMedicalDevice, Integer> implements InfoMedicalDeviceService {

    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    /**
     * 获取各类型仪器接入系统的个数
     *
     * @return 个数Map
     */
    @Override
    public CommonResult<Map<String, Integer>> getDeviceSystemAccessInTypeCounter() {
        // 获取全部仪器信息
        List<InfoMedicalDevice> deviceList = medicalDeviceRepository.findAll();
        Map<String, Integer> numberMap = new HashMap<>();
        // 看仪器类别是否包含特定的仪器即可,仪器类别(1#4).
        numberMap.put(DeviceTypeEnum.ANESTHESIA_MACHINE.getTypeName(),
                (int) deviceList.stream().filter((device) -> device.getDeviceType().contains(DeviceTypeEnum.ANESTHESIA_MACHINE.getCode())).count());
        numberMap.put(DeviceTypeEnum.RESPIRATOR_MACHINE.getTypeName(),
                (int) deviceList.stream().filter((device) -> device.getDeviceType().contains(DeviceTypeEnum.RESPIRATOR_MACHINE.getCode())).count());
        numberMap.put(DeviceTypeEnum.BLOOD_PRESSURE_MONITOR.getTypeName(),
                (int) deviceList.stream().filter((device) -> device.getDeviceType().contains(DeviceTypeEnum.BLOOD_PRESSURE_MONITOR.getCode())).count());
        numberMap.put(DeviceTypeEnum.ANESTHESIA_DEPTH_MONITOR.getTypeName(),
                (int) deviceList.stream().filter((device) -> device.getDeviceType().contains(DeviceTypeEnum.ANESTHESIA_DEPTH_MONITOR.getCode())).count());
        numberMap.put(DeviceTypeEnum.HEMOGLOBIN_MONITOR.getTypeName(),
                (int) deviceList.stream().filter((device) -> device.getDeviceType().contains(DeviceTypeEnum.HEMOGLOBIN_MONITOR.getCode())).count());
        numberMap.put(DeviceTypeEnum.BRAIN_OXYGEN_MONITOR.getTypeName(),
                (int) deviceList.stream().filter((device) -> device.getDeviceType().contains(DeviceTypeEnum.BRAIN_OXYGEN_MONITOR.getCode())).count());
        return CommonResult.success(numberMap);
    }


    /**
     * 根据仪器类别找到符合条件的仪器列表
     *
     * @param deviceType 仪器类别枚举
     * @return 复合条件的仪器信息
     */
    public List<InfoMedicalDevice> findByDeviceType(DeviceTypeEnum deviceType) {
        return medicalDeviceRepository.findAll().stream()
                // 筛选出包含给定类型的仪器信息
                .filter(device -> device.getDeviceType().contains(deviceType.getCode()))
                .collect(toList());
    }


    @Override
    protected JpaRepository<InfoMedicalDevice, Integer> initRepository() {
        return medicalDeviceRepository;
    }



}