package com.nano.msc.collection.service.impl;

import com.alibaba.fastjson.JSON;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.enums.DeviceTypeEnum;
import com.nano.msc.collection.enums.MedicalDeviceEnum;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.InfoMedicalDeviceService;
import com.nano.msc.common.service.BaseServiceImpl;
import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.system.log.service.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

/**
 * Description: 医疗仪器服务实现类
 * Usage:
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:42
 * @see #addMedicalDeviceInfo(InfoMedicalDevice) 保存医疗仪器信息,目前由Mobile端上传
 * @see #getMedicalDeviceAccessInSystemCounterTotal() 获取接入仪器的全部仪器个数(总的)
 * @see #getMedicalDeviceAccessInSystemCounterByType() 统计已经接入系统的仪器种类数量
 * @see #getSerialNumberListByDeviceCode(int) 通过仪器号查询对应拥有的仪器列表
 * @see #getDeviceInfoByDeviceType(DeviceTypeEnum) 根据仪器类别找到符合条件的仪器列表
 */
@Slf4j
@Service
public class InfoMedicalDeviceServiceImpl extends BaseServiceImpl<InfoMedicalDevice, Integer> implements InfoMedicalDeviceService {

    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;


    @Autowired
    private SystemLogService logger;

    /**
     * 保存或更新来自Mobile端的医疗仪器信息
     *
     * @param medicalDevice 医疗仪器
     * @return 是否成功
     */
    @Override
    public CommonResult<String> addMedicalDeviceInfo(InfoMedicalDevice medicalDevice) {
        logger.info("添加医疗仪器信息.");
        if (medicalDevice.getDeviceCode() == null) {
            return CommonResult.failed("添加失败,缺少DeviceCode.");
        }
        if (medicalDevice.getSerialNumber() == null || medicalDevice.getSerialNumber().length() == 0) {
            return CommonResult.failed("添加失败,仪器序列号格式错误.");
        }
        // 根据仪器号获取静态信息
        MedicalDeviceEnum deviceEnum = MedicalDeviceEnum.matchDeviceCodeEnum(medicalDevice.getDeviceCode());
        if (deviceEnum == null) {
            return CommonResult.failed("添加失败,当前DeviceCode不存在.");
        }

        InfoMedicalDevice hisDevice = medicalDeviceRepository.findByDeviceCodeAndSerialNumber(medicalDevice.getDeviceCode(), medicalDevice.getSerialNumber());
        if (hisDevice != null) {
            return CommonResult.failed("添加失败,当前仪器信息已经存在,如需更新请进行修改.");
        }
        // 设置基本信息
        medicalDevice.setDeviceName(deviceEnum.getDeviceName());
        medicalDevice.setDeviceType(deviceEnum.getDeviceType());
        medicalDevice.setCompanyName(deviceEnum.getCompanyName());
        medicalDevice.setInterfaceType(deviceEnum.getInterfaceType());
        medicalDevice = medicalDeviceRepository.save(medicalDevice);
        logger.info("新增医疗仪器信息成功:" + medicalDevice);
        return CommonResult.success(JSON.toJSONString(medicalDevice));
    }


    /**
     * 更新来自Mobile端的医疗仪器信息
     *
     * @param medicalDevice 医疗仪器
     * @return 是否成功
     */
    @Override
    public CommonResult<String> updateMedicalDeviceInfo(InfoMedicalDevice medicalDevice) {

        if (medicalDevice.getId() == 0) {
            return CommonResult.failed("更新失败,仪器ID非法:" + medicalDevice);
        }
        // 查询历史信息
        InfoMedicalDevice hisDevice = medicalDeviceRepository.findByIdAndDeviceCode(medicalDevice.getId(), medicalDevice.getDeviceCode());
        if (hisDevice == null) {
            return CommonResult.failed("更新失败,查询不到该仪器信息:" + medicalDevice);
        }
        // 更新仪器信息
        hisDevice.setSerialNumber(medicalDevice.getSerialNumber());
        hisDevice.setDeviceDepartment(medicalDevice.getDeviceDepartment());
        hisDevice.setProduceDate(medicalDevice.getProduceDate());
        hisDevice.setCollectorUniqueId(medicalDevice.getCollectorUniqueId());
        // historyDevice.setServiceLife(medicalDevice.getServiceLife());
        medicalDevice = medicalDeviceRepository.save(hisDevice);
        logger.info("更新医疗仪器信息成功:" + medicalDevice);
        return CommonResult.success(JSON.toJSONString(medicalDevice));
    }

    /**
     * 获取医疗仪器信息列表
     */
    @Override
    public CommonResult<List<InfoMedicalDevice>> getMedicalDeviceInfoListByDeviceCode(int deviceCode) {
        return CommonResult.success(medicalDeviceRepository.findByDeviceCode(deviceCode));
    }

    /**
     * 获取接入仪器的全部仪器个数(总的)
     */
    @Override
    public CommonResult<Integer> getMedicalDeviceAccessInSystemCounterTotal() {
        return CommonResult.success((int) medicalDeviceRepository.count());
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
     *
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
    public CommonResult<List<InfoMedicalDevice>> getDeviceInfoByDeviceType(DeviceTypeEnum deviceType) {
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
