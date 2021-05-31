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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

/**
 * Description: 医疗仪器服务实现类
 * Usage:
 * @see #getMedicalDeviceAccessInSystemCounterByType() 统计已经接入系统的仪器种类数量
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
     * 获取接入仪器的全部仪器个数(总的)
     */
    @Override
    public CommonResult<Integer> getMedicalDeviceAccessInSystemCounterTotal() {
        return CommonResult.success((int)medicalDeviceRepository.count());
    }

    /**
     * 获取各类型仪器接入系统的个数(按仪器类别分)
     *
     * @return 个数Map
     */
    @Override
    public CommonResult<Map<String, Integer>> getMedicalDeviceAccessInSystemCounterByType() {
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
     * 通过仪器号查询对应拥有的仪器列表
     * @param deviceCode 仪器号
     * @return 仪器列表
     */
    @Override
    public CommonResult<List<String>> getSerialNumberListByDeviceCode(int deviceCode) {
        // 获取序列号列表
        return CommonResult.success(medicalDeviceRepository.findByDeviceCode(deviceCode).stream()
                .map(InfoMedicalDevice::getSerialNumber).collect(toList()));
    }



    /**
     * 根据仪器类别找到符合条件的仪器列表
     *
     * @param deviceType 仪器类别枚举
     * @return 复合条件的仪器信息
     */
    public CommonResult<List<InfoMedicalDevice>> findByDeviceType(DeviceTypeEnum deviceType) {
        return CommonResult.success(medicalDeviceRepository.findAll().stream()
                // 筛选出包含给定类型的仪器信息
                .filter(device -> device.getDeviceType().contains(deviceType.getCode()))
                .collect(toList()));
    }


    @Override
    protected JpaRepository<InfoMedicalDevice, Integer> initRepository() {
        return medicalDeviceRepository;
    }



}
