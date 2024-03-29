package com.nano.msc.collection.service.impl;

import com.nano.msc.GlobalContext;
import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoDeviceMaintenanceRecord;
import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoDeviceMaintenanceRecordRepository;
import com.nano.msc.collection.repository.InfoDeviceUsageEvaluationRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.DeviceCardService;
import com.nano.msc.collection.utils.DeviceCardUtil;
import com.nano.msc.collection.utils.InfoDeviceDataCollectionUtil;
import com.nano.msc.collection.utils.InfoDeviceUsageEvaluationUtil;
import com.nano.msc.collection.utils.InfoMaintenanceRecordUtil;
import com.nano.msc.collection.vo.DeviceCardVo;
import com.nano.msc.common.utils.TimestampUtils;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;

/**
 * Description: 仪器卡片相关服务实现类
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/31 16:39
 */
@Service
public class DeviceCardServiceImpl implements DeviceCardService {

    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    @Autowired
    private InfoDeviceDataCollectionRepository dataCollectionRepository;

    @Autowired
    private InfoDeviceUsageEvaluationRepository usageEvaluationRepository;

    @Autowired
    private InfoDeviceMaintenanceRecordRepository maintenanceRecordRepository;

    /**
     * 获取某型号仪器的仪器卡片展示信息(某仪器型号)
     * @param deviceCode 仪器号
     * @return 仪器信息
     */
    @Override
    public CommonResult getDeviceCardInfoForOneDeviceModel(Integer deviceCode) {
        if (!GlobalContext.deviceCodeSet.contains(deviceCode)) {
            return CommonResult.failed("仪器号不存在:" + deviceCode);
        }
        // 查找这个仪器号下面的全部仪器信息
        List<InfoMedicalDevice> medicalDeviceList = medicalDeviceRepository.findByDeviceCode(deviceCode);
        if (medicalDeviceList.size() == 0) {
            return CommonResult.failed("该仪器号暂时没有仪器信息.");
        }
        // 1. 构造返回的信息
        DeviceCardVo deviceCardVo = new DeviceCardVo();
        BeanUtil.copyProperties(medicalDeviceList.get(0), deviceCardVo);

        // 2. 查询该仪器的数据采集信息
        List<InfoDeviceDataCollection> dataCollectionList = dataCollectionRepository.findByDeviceCode(deviceCode);
        // 设置总采集场次数
        deviceCardVo.setTotalCollectionCounter(dataCollectionList.size());
        // 设置总采集时长之和
        deviceCardVo.setTotalCollectionTime(DeviceCardUtil.getTotalCollectionTime(dataCollectionList));

        // 3. 构造使用评价信息
        List<InfoDeviceUsageEvaluation> usageEvaluationList = usageEvaluationRepository.findByDeviceCode(deviceCode);
        deviceCardVo.setAverageUsageExperienceLevel(InfoDeviceUsageEvaluationUtil.getAverageUsageEvaluationExperienceLevel(usageEvaluationList));
        deviceCardVo.setAverageUsageReliabilityLevel(InfoDeviceUsageEvaluationUtil.getAverageUsageEvaluationReliabilityLevel(usageEvaluationList));

        // 4. 构造维修记录信息
        List<InfoDeviceMaintenanceRecord> maintenanceRecordList = maintenanceRecordRepository.findByDeviceCode(deviceCode);
        deviceCardVo.setMaintenanceRecordCounter(maintenanceRecordList.size());
        deviceCardVo.setHistoryCostAccessoryNum(InfoMaintenanceRecordUtil.getHistoryCostAccessoryNum(maintenanceRecordList));
        deviceCardVo.setHistoryCostRepairNum(InfoMaintenanceRecordUtil.getHistoryCostRepairNum(maintenanceRecordList));
        deviceCardVo.setHistoryCostOtherNum(InfoMaintenanceRecordUtil.getHistoryCostOtherNum(maintenanceRecordList));
        deviceCardVo.setHistoryMaintenanceCostSum(deviceCardVo.getHistoryCostAccessoryNum() + deviceCardVo.getHistoryCostRepairNum() + deviceCardVo.getHistoryCostOtherNum());
        deviceCardVo.setAverageMaintenanceResponseTimeSatisfactionLevel(InfoMaintenanceRecordUtil.getAverageMaintenanceResponseTimeSatisfactionLevel(maintenanceRecordList));
        deviceCardVo.setAverageMaintenancePriceSatisfactionLevel(InfoMaintenanceRecordUtil.getAverageMaintenancePriceSatisfactionLevel(maintenanceRecordList));
        deviceCardVo.setAverageMaintenanceServiceAttitudeSatisfactionLevel(InfoMaintenanceRecordUtil.getAverageMaintenanceServiceAttitudeSatisfactionLevel(maintenanceRecordList));
        deviceCardVo.setAverageMaintenanceOverallProcessSatisfactionLevel(InfoMaintenanceRecordUtil.getAverageMaintenanceOverallProcessSatisfactionLevel(maintenanceRecordList));

        // 构造经济性评价信息
        // 计算总的采集的收益
        int totalCollectionTime = InfoDeviceDataCollectionUtil.getTotalCollectionTime(dataCollectionList);
        if (totalCollectionTime < 100) {
            deviceCardVo.setTotalProfitMoney(0D);
        } else {
            deviceCardVo.setTotalProfitMoney(((totalCollectionTime / 3600) + 1) * medicalDeviceList.get(0).getProfitMoney());
        }
        long totalDays = 0;
        for (InfoMedicalDevice device : medicalDeviceList) {
            LocalDateTime produceDate = device.getProduceDate();
            totalDays = totalDays + TimestampUtils.getDurationTimeDay(produceDate, TimestampUtils.getCurrentTimeForDataBase());
        }
        // 总技师费
        deviceCardVo.setTotalTechnicianMonthlySalary(((double) totalDays) / 30 * deviceCardVo.getTechnicianMonthlySalary() / medicalDeviceRepository.count());
        // 总耗材费
        deviceCardVo.setTotalConsumableCostMoney(((double) totalDays) / 365 * deviceCardVo.getConsumableCostMoney());
        // 总固定维修费
        deviceCardVo.setTotalFixRepairCostMoney(((double) totalDays) / 365 * deviceCardVo.getFixRepairCostMoney());
        // 返回统计结果
        return CommonResult.success(deviceCardVo);
    }

    /**
     * 获取某型号仪器的仪器卡片展示信息
     * @param deviceCode 仪器号
     * @return 仪器信息
     */
    @Override
    public CommonResult getDeviceCardInfoForOneDeviceDetail(Integer deviceCode, String serialNumber) {
        if (!GlobalContext.deviceCodeSet.contains(deviceCode)) {
            return CommonResult.failed("仪器号不存在:" + deviceCode);
        }
        InfoMedicalDevice medicalDevice = medicalDeviceRepository.findByDeviceCodeAndSerialNumber(deviceCode, serialNumber);
        if (medicalDevice == null) {
            return CommonResult.failed("该仪器不存在:" + deviceCode + ", " + serialNumber);
        }
        // 构造返回的信息
        DeviceCardVo deviceCardVo = new DeviceCardVo();
        // 1. 拷贝基本仪器信息
        BeanUtil.copyProperties(medicalDevice, deviceCardVo);

        // 2. 查询该仪器的数据采集信息
        List<InfoDeviceDataCollection> dataCollectionList = dataCollectionRepository.findByDeviceCodeAndSerialNumber(deviceCode, serialNumber);
        // 设置总采集场次数
        deviceCardVo.setTotalCollectionCounter(dataCollectionList.size());
        // 设置总采集时长之和
        deviceCardVo.setTotalCollectionTime(DeviceCardUtil.getTotalCollectionTime(dataCollectionList));

        // 3. 构造使用评价信息
        List<InfoDeviceUsageEvaluation> usageEvaluationList = usageEvaluationRepository.findByDeviceCodeAndSerialNumber(deviceCode, serialNumber);
        deviceCardVo.setAverageUsageExperienceLevel(InfoDeviceUsageEvaluationUtil.getAverageUsageEvaluationExperienceLevel(usageEvaluationList));
        deviceCardVo.setAverageUsageReliabilityLevel(InfoDeviceUsageEvaluationUtil.getAverageUsageEvaluationReliabilityLevel(usageEvaluationList));

        // 4. 构造维修记录信息
        List<InfoDeviceMaintenanceRecord> maintenanceRecordList = maintenanceRecordRepository.findByDeviceCodeAndSerialNumber(deviceCode, serialNumber);
        deviceCardVo.setMaintenanceRecordCounter(maintenanceRecordList.size());
        deviceCardVo.setHistoryCostAccessoryNum(InfoMaintenanceRecordUtil.getHistoryCostAccessoryNum(maintenanceRecordList));
        deviceCardVo.setHistoryCostRepairNum(InfoMaintenanceRecordUtil.getHistoryCostRepairNum(maintenanceRecordList));
        deviceCardVo.setHistoryCostOtherNum(InfoMaintenanceRecordUtil.getHistoryCostOtherNum(maintenanceRecordList));
        deviceCardVo.setHistoryMaintenanceCostSum(deviceCardVo.getHistoryCostAccessoryNum() + deviceCardVo.getHistoryCostRepairNum() + deviceCardVo.getHistoryCostOtherNum());
        deviceCardVo.setAverageMaintenanceResponseTimeSatisfactionLevel(InfoMaintenanceRecordUtil.getAverageMaintenanceResponseTimeSatisfactionLevel(maintenanceRecordList));
        deviceCardVo.setAverageMaintenancePriceSatisfactionLevel(InfoMaintenanceRecordUtil.getAverageMaintenancePriceSatisfactionLevel(maintenanceRecordList));
        deviceCardVo.setAverageMaintenanceServiceAttitudeSatisfactionLevel(InfoMaintenanceRecordUtil.getAverageMaintenanceServiceAttitudeSatisfactionLevel(maintenanceRecordList));
        deviceCardVo.setAverageMaintenanceOverallProcessSatisfactionLevel(InfoMaintenanceRecordUtil.getAverageMaintenanceOverallProcessSatisfactionLevel(maintenanceRecordList));

        // 构造经济性评价信息
        // 计算采集的收益
        int totalCollectionTime = InfoDeviceDataCollectionUtil.getTotalCollectionTime(dataCollectionList);
        if (totalCollectionTime < 100) {
            deviceCardVo.setTotalProfitMoney(0D);
        } else {
            deviceCardVo.setTotalProfitMoney(((totalCollectionTime / 3600) + 1) * medicalDevice.getProfitMoney());
        }
        LocalDateTime produceDate = medicalDevice.getProduceDate();
        // 计算天数
        long days = TimestampUtils.getDurationTimeDay(produceDate, TimestampUtils.getCurrentTimeForDataBase());
        // 总技师费
        deviceCardVo.setTotalTechnicianMonthlySalary(((double) days) / 30 * deviceCardVo.getTechnicianMonthlySalary() / medicalDeviceRepository.count());
        // 总耗材费
        deviceCardVo.setTotalConsumableCostMoney(((double) days) / 365 * deviceCardVo.getConsumableCostMoney());
        // 总固定维修费
        deviceCardVo.setTotalFixRepairCostMoney(((double) days) / 365 * deviceCardVo.getFixRepairCostMoney());

        // 返回统计结果
        return CommonResult.success(deviceCardVo);
    }
}
