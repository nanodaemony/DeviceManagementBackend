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

import static java.util.stream.Collectors.toList;

/**
 * Description: 医疗仪器服务实现类
 * Usage:
 * @see #addMedicalDeviceInfo(InfoMedicalDevice) 保存医疗仪器信息,目前由Mobile端上传
 * @see #updateMedicalDeviceInfo(InfoMedicalDevice) 更新医疗仪器信息
 * @see #getMedicalDeviceInfoListByDeviceCode(int) 通过仪器号获取仪器信息
 * @see #getMedicalDeviceAccessInSystemCounterTotal() 获取接入仪器的全部仪器个数(总的)
 * @see #getMedicalDeviceAccessInSystemCounterByDeviceType() 统计已经接入系统的仪器种类数量
 * @see #getMedicalDeviceSerialNumberListByDeviceCode(int) 通过仪器号查询对应拥有的仪器列表
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:42
 */
@Service
public class InfoMedicalDeviceServiceImpl extends BaseServiceImpl<InfoMedicalDevice, Integer> implements InfoMedicalDeviceService {

    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    @Autowired
    private SystemLogService logger;

    /**
     * 新增一条医疗仪器信息
     *
     * @param medicalDevice 医疗仪器
     * @return 是否成功
     */
    @Override
    public CommonResult<String> addMedicalDeviceInfo(InfoMedicalDevice medicalDevice) {
        logger.info("准备入库医疗仪器信息.");
        if (medicalDevice.getDeviceCode() == null || medicalDevice.getSerialNumber() == null || medicalDevice.getSerialNumber().length() == 0) {
            logger.error("添加医疗仪器信息失败,缺少关键仪器信息.");
            return CommonResult.failed("添加医疗仪器信息失败,缺少关键仪器信息.");
        }
        // 根据仪器号获取静态信息
        MedicalDeviceEnum deviceEnum = MedicalDeviceEnum.matchDeviceCodeEnum(medicalDevice.getDeviceCode());
        if (deviceEnum == null) {
            logger.error("添加医疗仪器信息失败,当前DeviceCode不存在.");
            return CommonResult.failed("添加医疗仪器信息失败,当前DeviceCode不存在.");
        }
        // 查询该仪器是否已经存在于数据库中
        InfoMedicalDevice historyDevice = medicalDeviceRepository.findByDeviceCodeAndSerialNumber(medicalDevice.getDeviceCode(), medicalDevice.getSerialNumber());
        if (historyDevice != null) {
            logger.error("添加医疗仪器信息失败,当前仪器信息已经存在,如需更新请进行修改.");
            return CommonResult.failed("添加医疗仪器信息失败,当前仪器信息已经存在,如需更新请进行修改.");
        }
        // 设置仪器的静态基本信息
        medicalDevice.setCompanyName(deviceEnum.getCompanyName());
        medicalDevice.setDeviceName(deviceEnum.getDeviceName());
        medicalDevice.setDeviceType(deviceEnum.getDeviceType());
        medicalDevice.setInterfaceType(deviceEnum.getInterfaceType());
        medicalDevice.setCanCollectData(deviceEnum.getCanCollectData());
        // 仪器信息入库
        medicalDevice = medicalDeviceRepository.save(medicalDevice);
        logger.info("成功添加医疗仪器信息:" + medicalDevice);
        return CommonResult.success(JSON.toJSONString(medicalDevice));
    }

    /**
     * 更新一条医疗仪器信息
     *
     * @param updatedDeviceInfo 医疗仪器
     * @return 是否更新成功
     */
    @Override
    public CommonResult<String> updateMedicalDeviceInfo(InfoMedicalDevice updatedDeviceInfo) {
        // TODO: 更新仪器信息时需要一并更新其他全部的相关数据信息
        // 由于是更新信息,必须携带仪器的ID号,否则为非法更新
        if (updatedDeviceInfo.getId() == 0) {
            logger.error("更新医疗仪器信息失败,仪器ID非法:" + updatedDeviceInfo);
            return CommonResult.failed("更新医疗仪器信息失败,仪器ID非法:" + updatedDeviceInfo);
        }
        // 查询历史医疗仪器信息
        InfoMedicalDevice historyDevice = medicalDeviceRepository.findByIdAndDeviceCode(updatedDeviceInfo.getId(), updatedDeviceInfo.getDeviceCode());
        if (historyDevice == null) {
            logger.error("更新医疗仪器信息失败,查询不到该仪器信息:" + updatedDeviceInfo);
            return CommonResult.failed("更新医疗仪器信息失败,查询不到该仪器信息:" + updatedDeviceInfo);
        }
        // 系统库中有这个仪器则更新仪器信息(仅更新移动端传输的数据,仪器静态信息无需更新)
        historyDevice.setCollectorUniqueId(updatedDeviceInfo.getCollectorUniqueId());
        historyDevice.setSerialNumber(updatedDeviceInfo.getSerialNumber());
        historyDevice.setProduceDate(updatedDeviceInfo.getProduceDate());
        historyDevice.setServiceLife(updatedDeviceInfo.getServiceLife());
        historyDevice.setDeviceDepartment(updatedDeviceInfo.getDeviceDepartment());
        historyDevice.setConsumableCostMoney(updatedDeviceInfo.getConsumableCostMoney());
        historyDevice.setProfitMoney(updatedDeviceInfo.getProfitMoney());
        historyDevice.setDailyPowerCost(updatedDeviceInfo.getDailyPowerCost());
        // 更新信息至数据库中
        updatedDeviceInfo = medicalDeviceRepository.save(historyDevice);
        logger.info("成功更新医疗仪器信息:" + updatedDeviceInfo);
        return CommonResult.success(JSON.toJSONString(updatedDeviceInfo));
    }

    /**
     * 获取医疗仪器信息列表(根据仪器号)
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
    public CommonResult<Map<String, Integer>> getMedicalDeviceAccessInSystemCounterByDeviceType() {
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
    public CommonResult<List<String>> getMedicalDeviceSerialNumberListByDeviceCode(int deviceCode) {
        // 获取序列号列表
        return CommonResult.success(medicalDeviceRepository.findByDeviceCode(deviceCode).stream()
                .map(InfoMedicalDevice::getSerialNumber).collect(toList()));
    }

    /**
     * 初始化仓库
     */
    @Override
    protected JpaRepository<InfoMedicalDevice, Integer> initRepository() {
        return medicalDeviceRepository;
    }


}
