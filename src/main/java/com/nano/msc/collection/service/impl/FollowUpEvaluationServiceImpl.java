package com.nano.msc.collection.service.impl;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoDeviceUsageEvaluationRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.FollowUpEvaluationService;
import com.nano.msc.collection.utils.DeviceFollowUpEvaluationUtil;
import com.nano.msc.collection.utils.InfoDeviceDataCollectionUtil;
import com.nano.msc.collection.utils.InfoDeviceUsageEvaluationUtil;
import com.nano.msc.collection.vo.FollowUpEvaluationVo;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;

/**
 * Description: 追踪评价服务实现类
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/8 20:07
 */
@Service
public class FollowUpEvaluationServiceImpl implements FollowUpEvaluationService {

    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    @Autowired
    private InfoDeviceDataCollectionRepository dataCollectionRepository;

    @Autowired
    private InfoDeviceUsageEvaluationRepository usageEvaluationRepository;

    /**
     * 获取全部仪器的追踪评价信息列表(按DeviceCode与SerialNumber统计)
     *
     * @return 详细信息
     */
    @Override
    public CommonResult getDeviceFollowUpEvaluationCoverDeviceAllByDeviceCodeAndSerialNumber() {
        // 查询全部医疗仪器信息
        List<InfoMedicalDevice> medicalDeviceList = medicalDeviceRepository.findAll();
        List<FollowUpEvaluationVo> resList = new ArrayList<>(medicalDeviceList.size());
        for (InfoMedicalDevice device : medicalDeviceList) {
            FollowUpEvaluationVo evaluationVo = new FollowUpEvaluationVo();
            // 拷贝仪器信息
            BeanUtil.copyProperties(device, evaluationVo);

            // 查询该仪器下的全部数据采集信息
            List<InfoDeviceDataCollection> dataCollectionList = dataCollectionRepository
                    .findByDeviceCodeAndSerialNumber(device.getDeviceCode(), device.getSerialNumber());
            // 设置该类型仪器总采集场次数
            evaluationVo.setTotalDataCollectionCounter(dataCollectionList.size());
            // 设置该类型仪器总采集时长
            evaluationVo.setTotalDataCollectionTime(InfoDeviceDataCollectionUtil.getTotalCollectionTime(dataCollectionList));
            // 设置过去15 30 90天内的开机情况
            evaluationVo.setUsedDaysInPast15Days(DeviceFollowUpEvaluationUtil.getUsedDaysInPastDays(dataCollectionList, 15));
            evaluationVo.setUsedDaysInPast30Days(DeviceFollowUpEvaluationUtil.getUsedDaysInPastDays(dataCollectionList, 30));
            evaluationVo.setUsedDaysInPast90Days(DeviceFollowUpEvaluationUtil.getUsedDaysInPastDays(dataCollectionList, 90));

            // 查询这个仪器的全部使用评价列表
            List<InfoDeviceUsageEvaluation> usageEvaluationList = usageEvaluationRepository
                    .findByDeviceCodeAndSerialNumber(device.getDeviceCode(), device.getSerialNumber());
            // 设置总使用评价记录数
            evaluationVo.setTotalUsageEvaluationCounter(usageEvaluationList.size());
            evaluationVo.setAverageUsageExperienceLevel(InfoDeviceUsageEvaluationUtil.getAverageUsageEvaluationExperienceLevel(usageEvaluationList));
            evaluationVo.setAverageUsageReliabilityLevel(InfoDeviceUsageEvaluationUtil.getAverageUsageEvaluationReliabilityLevel(usageEvaluationList));
            evaluationVo.setRecordErrorCounter(InfoDeviceUsageEvaluationUtil.getTotalRecordErrorCounter(usageEvaluationList));
            resList.add(evaluationVo);
        }
        return CommonResult.success(resList);
    }


}
