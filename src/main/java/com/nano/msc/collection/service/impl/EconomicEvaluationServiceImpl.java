package com.nano.msc.collection.service.impl;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoDeviceMaintenanceRecord;
import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoDeviceMaintenanceRecordRepository;
import com.nano.msc.collection.repository.InfoDeviceUsageEvaluationRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.EconomicEvaluationService;
import com.nano.msc.collection.utils.DeviceFollowUpEvaluationUtil;
import com.nano.msc.collection.utils.InfoDeviceDataCollectionUtil;
import com.nano.msc.collection.utils.InfoDeviceUsageEvaluationUtil;
import com.nano.msc.collection.utils.InfoMaintenanceRecordUtil;
import com.nano.msc.collection.vo.EconomicEvaluationVo;
import com.nano.msc.collection.vo.FollowUpEvaluationVo;
import com.nano.msc.common.utils.TimestampUtils;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 经济性评价接口服务实现类
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/9 16:43
 */
@Service
@Slf4j
public class EconomicEvaluationServiceImpl implements EconomicEvaluationService {


    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    @Autowired
    private InfoDeviceDataCollectionRepository dataCollectionRepository;

    @Autowired
    private InfoDeviceMaintenanceRecordRepository maintenanceRecordRepository;

    @Autowired
    private InfoDeviceUsageEvaluationRepository usageEvaluationRepository;

    /**
     * 获取全部仪器的追踪评价信息(按DeviceCode与SerialNumber统计)
     */
    @Override
    public CommonResult getDeviceEconomicEvaluationCoverDeviceAllByDeviceCodeAndSerialNumber() {
        // 查询全部医疗仪器信息
        List<InfoMedicalDevice> medicalDeviceList = medicalDeviceRepository.findAll();

        List<EconomicEvaluationVo> resList = new ArrayList<>(medicalDeviceList.size());
        for (InfoMedicalDevice device : medicalDeviceList) {
            EconomicEvaluationVo evaluationVo = new EconomicEvaluationVo();
            // 拷贝仪器信息
            BeanUtil.copyProperties(device, evaluationVo);

            // 计算采集的收益
            List<InfoDeviceDataCollection> dataCollectionList = dataCollectionRepository.findByDeviceCodeAndSerialNumber(device.getDeviceCode(), device.getSerialNumber());
            int totalCollectionTime = InfoDeviceDataCollectionUtil.getTotalCollectionTime(dataCollectionList);
            if (totalCollectionTime < 100) {
                evaluationVo.setTotalProfitMoney(0D);
            } else {
                evaluationVo.setTotalProfitMoney(((totalCollectionTime / 3600) + 1) * device.getProfitMoney());
            }
            LocalDateTime produceDate = device.getProduceDate();
            // 计算天数
            long days = TimestampUtils.getDurationTimeDay(produceDate, TimestampUtils.getCurrentTimeForDataBase());
            // 总技师费
            evaluationVo.setTotalTechnicianMonthlySalary(((double) days) / 30 * evaluationVo.getTechnicianMonthlySalary() / medicalDeviceList.size());
            // 总耗材费
            evaluationVo.setTotalConsumableCostMoney(((double) days) / 365 * evaluationVo.getConsumableCostMoney());
            // 总固定维修费
            evaluationVo.setTotalFixRepairCostMoney(((double) days) / 365 * evaluationVo.getFixRepairCostMoney());

            // 统计该仪器下的全部维修保养相关的经济指标
            List<InfoDeviceMaintenanceRecord> maintenanceRecordList = maintenanceRecordRepository
                    .findByDeviceCodeAndSerialNumber(device.getDeviceCode(), device.getSerialNumber());
            // 总维保费用
            evaluationVo.setHistoryMaintenanceCostSum(InfoMaintenanceRecordUtil.getHistoryMaintenanceCostSum(maintenanceRecordList));
            evaluationVo.setHistoryCostAccessoryNum(InfoMaintenanceRecordUtil.getHistoryCostAccessoryNum(maintenanceRecordList));
            evaluationVo.setHistoryCostRepairNum(InfoMaintenanceRecordUtil.getHistoryCostRepairNum(maintenanceRecordList));
            evaluationVo.setHistoryCostOtherNum(InfoMaintenanceRecordUtil.getHistoryCostOtherNum(maintenanceRecordList));


            resList.add(evaluationVo);
        }
        return CommonResult.success(resList);
    }
}








