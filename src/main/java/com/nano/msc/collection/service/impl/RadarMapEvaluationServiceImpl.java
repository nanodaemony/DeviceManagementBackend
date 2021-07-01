package com.nano.msc.collection.service.impl;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoDeviceMaintenanceRecord;
import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.enums.MedicalDeviceEnum;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoDeviceMaintenanceRecordRepository;
import com.nano.msc.collection.repository.InfoDeviceUsageEvaluationRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.RadarMapEvaluationService;
import com.nano.msc.collection.utils.DeviceFollowUpEvaluationUtil;
import com.nano.msc.collection.utils.InfoDeviceDataCollectionUtil;
import com.nano.msc.collection.utils.InfoDeviceUsageEvaluationUtil;
import com.nano.msc.collection.utils.InfoMaintenanceRecordUtil;
import com.nano.msc.collection.utils.MaintenanceEvaluationUtil;
import com.nano.msc.collection.vo.RadarMapVo;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description: 雷达图评价
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/29 16:25
 */
@Service
public class RadarMapEvaluationServiceImpl implements RadarMapEvaluationService {

    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    @Autowired
    private InfoDeviceDataCollectionRepository dataCollectionRepository;

    @Autowired
    private InfoDeviceUsageEvaluationRepository usageEvaluationRepository;

    @Autowired
    private InfoDeviceMaintenanceRecordRepository maintenanceRecordRepository;

    /**
     * 获取仪器雷达图Map
     */
    @Override
    public CommonResult getDeviceRadarMapEvaluationMap() {

        // 存结果的Map,按照仪器类别进行统计
        Map<String, List<RadarMapVo>> resMap = new HashMap<>();
        for (int i = 1; i < 7; i++) {
            resMap.put(i + "", new ArrayList<>());
        }

        // 全部仪器信息
        List<InfoMedicalDevice> medicalDeviceListAll = medicalDeviceRepository.findAll();
        // 全部仪器号
        Set<Integer> deviceCodeSet = medicalDeviceListAll.stream().map(InfoMedicalDevice::getDeviceCode).collect(Collectors.toSet());

        // 遍历每个仪器号
        for (Integer deviceCode : deviceCodeSet) {
            // 找到当前仪器号的全部仪器
            List<InfoMedicalDevice> medicalDeviceList = medicalDeviceRepository.findByDeviceCode(deviceCode);
            // 找到当前仪器的全部采集信息
            List<InfoDeviceDataCollection> dataCollectionList = dataCollectionRepository.findByDeviceCode(deviceCode);
            // 找到当前仪器的全部使用评价信息
            List<InfoDeviceUsageEvaluation> usageEvaluationList = usageEvaluationRepository.findByDeviceCode(deviceCode);
            // 找到当前仪器的全部维修信息
            List<InfoDeviceMaintenanceRecord> maintenanceRecordList = maintenanceRecordRepository.findByDeviceCode(deviceCode);
            RadarMapVo radarMapVo = new RadarMapVo();
            radarMapVo.setDeviceCode(deviceCode);
            InfoMedicalDevice medicalDevice = medicalDeviceList.get(0);
            if (medicalDevice != null) {
                radarMapVo.setDeviceName(medicalDevice.getDeviceName());
                radarMapVo.setDeviceType(medicalDevice.getDeviceType());
                radarMapVo.setCompanyName(medicalDevice.getCompanyName());
            }

            radarMapVo.setUsedDaysInPast30Days(DeviceFollowUpEvaluationUtil.getUsedDaysInPastDays(dataCollectionList, 30));
            radarMapVo.setAverageUsageExperienceLevel(InfoDeviceUsageEvaluationUtil.getAverageUsageEvaluationExperienceLevel(usageEvaluationList));
            radarMapVo.setAverageUsageReliabilityLevel(InfoDeviceUsageEvaluationUtil.getAverageUsageEvaluationReliabilityLevel(usageEvaluationList));

            radarMapVo.setAverageMaintenanceRecordCounter(MaintenanceEvaluationUtil.getAverageMaintenanceRecordCounter(maintenanceRecordList, medicalDeviceList.size()));
            radarMapVo.setAverageMaintenanceOverallProcessSatisfactionLevel(InfoMaintenanceRecordUtil.getAverageMaintenanceOverallProcessSatisfactionLevel(maintenanceRecordList));
            double cost1 = InfoMaintenanceRecordUtil.getHistoryCostAccessoryNum(maintenanceRecordList);
            double cost2 = InfoMaintenanceRecordUtil.getHistoryCostRepairNum(maintenanceRecordList);
            double cost3 = InfoMaintenanceRecordUtil.getHistoryCostOtherNum(maintenanceRecordList);

            if (maintenanceRecordList.size() == 0) {
                radarMapVo.setAverageHistoryMaintenanceCostSum(0D);
            } else {
                radarMapVo.setAverageHistoryMaintenanceCostSum((cost1 + cost2 + cost3) / maintenanceRecordList.size());
            }

            // 计算采集的收益
            radarMapVo.setDevicePurchasePrice(medicalDeviceList.get(0).getDevicePurchasePrice());

            int totalCollectionTime = InfoDeviceDataCollectionUtil.getTotalCollectionTime(dataCollectionList);
            if (totalCollectionTime < 100) {
                radarMapVo.setTotalProfitMoney(0D);
            } else {
                radarMapVo.setTotalProfitMoney(((totalCollectionTime / 3600) + 1) * medicalDeviceList.get(0).getProfitMoney());
            }
            // TODO: 这里新增其他经济性评价

            resMap.get(radarMapVo.getDeviceType()).add(radarMapVo);
        }

        return CommonResult.success(resMap);
    }
}
