package com.nano.msc.collection.service.impl;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoDeviceMaintenanceRecord;
import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoDeviceMaintenanceRecordRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.EconomicEvaluationService;
import com.nano.msc.collection.utils.DeviceFollowUpEvaluationUtil;
import com.nano.msc.collection.utils.InfoDeviceDataCollectionUtil;
import com.nano.msc.collection.utils.InfoDeviceUsageEvaluationUtil;
import com.nano.msc.collection.utils.InfoMaintenanceRecordUtil;
import com.nano.msc.collection.vo.EconomicEvaluationVo;
import com.nano.msc.collection.vo.FollowUpEvaluationVo;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;

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
public class EconomicEvaluationServiceImpl implements EconomicEvaluationService {


    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    @Autowired
    private InfoDeviceDataCollectionRepository dataCollectionRepository;

    @Autowired
    private InfoDeviceMaintenanceRecordRepository maintenanceRecordRepository;

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

            // 查询该仪器下的全部维修保养记录
            List<InfoDeviceMaintenanceRecord> dataCollectionList = maintenanceRecordRepository
                    .findByDeviceCodeAndSerialNumber(device.getDeviceCode(), device.getSerialNumber());
            // 总维保费用
            evaluationVo.setHistoryMaintenanceCostSum(InfoMaintenanceRecordUtil.getHistoryMaintenanceCostSum(dataCollectionList));
            evaluationVo.setHistoryCostAccessoryNum(InfoMaintenanceRecordUtil.getHistoryCostAccessoryNum(dataCollectionList));
            evaluationVo.setHistoryCostRepairNum(InfoMaintenanceRecordUtil.getHistoryCostRepairNum(dataCollectionList));
            evaluationVo.setHistoryCostOtherNum(InfoMaintenanceRecordUtil.getHistoryCostOtherNum(dataCollectionList));

            resList.add(evaluationVo);
        }
        return CommonResult.success(resList);
    }
}
