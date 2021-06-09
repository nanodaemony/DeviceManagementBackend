package com.nano.msc.collection.service.impl;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoDeviceMaintenanceRecord;
import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoDeviceMaintenanceRecordRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.MaintenanceEvaluationService;
import com.nano.msc.collection.utils.DeviceFollowUpEvaluationUtil;
import com.nano.msc.collection.utils.InfoDeviceDataCollectionUtil;
import com.nano.msc.collection.utils.InfoDeviceUsageEvaluationUtil;
import com.nano.msc.collection.utils.InfoMaintenanceRecordUtil;
import com.nano.msc.collection.utils.MaintenanceEvaluationUtil;
import com.nano.msc.collection.vo.MaintenanceEvaluationVo;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;

/**
 * Description: 维保评价服务实现类
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/9 15:10
 */
@Service
public class MaintenanceEvaluationServiceImpl implements MaintenanceEvaluationService {

    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    @Autowired
    private InfoDeviceDataCollectionRepository dataCollectionRepository;

    @Autowired
    private InfoDeviceMaintenanceRecordRepository maintenanceRecordRepository;

    /**
     * 获取仪器维保评价信息(根据DeviceCode与SerialNumber)
     */
    @Override
    public CommonResult getMaintenanceEvaluationByDeviceCodeAndSerialNumber() {
        // 查询全部医疗仪器信息
        List<InfoMedicalDevice> medicalDeviceList = medicalDeviceRepository.findAll();
        List<MaintenanceEvaluationVo> resList = new ArrayList<>(medicalDeviceList.size());
        for (InfoMedicalDevice device : medicalDeviceList) {
            MaintenanceEvaluationVo evaluationVo = new MaintenanceEvaluationVo();
            // 拷贝仪器信息
            BeanUtil.copyProperties(device, evaluationVo);
            // 查询该仪器的全部维保记录
            List<InfoDeviceMaintenanceRecord> maintenanceRecordList = maintenanceRecordRepository
                    .findByDeviceCodeAndSerialNumber(device.getDeviceCode(), device.getSerialNumber());
            evaluationVo.setMaintenanceRecordCounter(maintenanceRecordList.size());
            // 计算平均维修天数
            evaluationVo.setAverageRepairTime(MaintenanceEvaluationUtil.getAverageRepairTime(maintenanceRecordList));
            // 统计维修方式
            MaintenanceEvaluationUtil.getMaintenanceModeCounterRepairCounter(maintenanceRecordList, evaluationVo);
            // 统计维修人员
            MaintenanceEvaluationUtil.getMaintenancePeopleCounter(maintenanceRecordList, evaluationVo);
            // 统计故障原因
            MaintenanceEvaluationUtil.getErrorReasonCounter(maintenanceRecordList, evaluationVo);
            // 统计是否在保质期内
            MaintenanceEvaluationUtil.getWithinShelfLifeCounter(maintenanceRecordList, evaluationVo);
            // 统计是否更换配件
            MaintenanceEvaluationUtil.getReplaceAccessoryCounter(maintenanceRecordList, evaluationVo);
            // 统计故障是否解决
            MaintenanceEvaluationUtil.getErrorOvercomeCounter(maintenanceRecordList, evaluationVo);
            // 计算历史费用
            evaluationVo.setHistoryCostAccessoryNum(InfoMaintenanceRecordUtil.getHistoryCostAccessoryNum(maintenanceRecordList));
            evaluationVo.setHistoryCostRepairNum(InfoMaintenanceRecordUtil.getHistoryCostRepairNum(maintenanceRecordList));
            evaluationVo.setHistoryCostOtherNum(InfoMaintenanceRecordUtil.getHistoryCostOtherNum(maintenanceRecordList));
            // 计算满意度平均值
            evaluationVo.setAverageMaintenanceResponseTimeSatisfactionLevel(InfoMaintenanceRecordUtil.getAverageMaintenanceResponseTimeSatisfactionLevel(maintenanceRecordList));
            evaluationVo.setAverageMaintenancePriceSatisfactionLevel(InfoMaintenanceRecordUtil.getAverageMaintenancePriceSatisfactionLevel(maintenanceRecordList));
            evaluationVo.setAverageMaintenanceServiceAttitudeSatisfactionLevel(InfoMaintenanceRecordUtil.getAverageMaintenanceServiceAttitudeSatisfactionLevel(maintenanceRecordList));
            evaluationVo.setAverageMaintenanceOverallProcessSatisfactionLevel(InfoMaintenanceRecordUtil.getAverageMaintenanceOverallProcessSatisfactionLevel(maintenanceRecordList));
            // 设置维保建议条数
            evaluationVo.setMaintenanceImprovementSuggestionsCounter(MaintenanceEvaluationUtil.getMaintenanceImprovementSuggestionsCounter(maintenanceRecordList));

            resList.add(evaluationVo);
        }
        return CommonResult.success(resList);
    }
}
