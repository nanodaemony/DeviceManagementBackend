package com.nano.msc.collection.service.impl;

import com.alibaba.fastjson.JSON;
import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.collection.enums.EvaluationLevelEnum;
import com.nano.msc.collection.repository.InfoDeviceUsageEvaluationRepository;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.service.InfoDeviceUsageEvaluationService;
import com.nano.msc.common.service.BaseServiceImpl;
import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.system.log.service.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Description: 仪器使用评价服务类
 * Usage:
 * @see #addDefaultDeviceUsageEvaluationInfo(Integer, Integer, String, String) 新增默认的使用评价信息
 * @see #updateDeviceUsageEvaluationInfoFromMobile(InfoDeviceUsageEvaluation) 存储由APP上传而来的仪器使用评价表
 * @see #getDeviceUsageEvaluationListByDeviceCode(int, int, int) 根据仪器号查询该仪器完成的评价信息(根据仪器号)
 * @see #getDeviceUsageEvaluationListByDeviceCodeAndSerialNumber(int, String, int, int) 根据仪器号及序列号查询该仪器完成的评价信息(根据仪器号与序列号)
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:38
 */
@Slf4j
@Service
public class InfoDeviceUsageEvaluationServiceImpl extends BaseServiceImpl<InfoDeviceUsageEvaluation, Integer> implements InfoDeviceUsageEvaluationService {

    @Autowired
    private InfoDeviceUsageEvaluationRepository deviceUsageEvaluationRepository;

    @Autowired
    private InfoDeviceDataCollectionRepository deviceCollectionRepository;

    @Autowired
    private SystemLogService logger;

    /**
     * 新增默认的仪器使用评价
     *
     * @param collectionNumber 采集场次号
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @param deviceDepartment 使用科室
     */
    @Override
    public void addDefaultDeviceUsageEvaluationInfo(Integer collectionNumber, Integer deviceCode, String serialNumber, String deviceDepartment) {

        // 如果该次采集已经存在评价信息则不再生成了
        if (deviceUsageEvaluationRepository.existsByCollectionNumber(collectionNumber)) {
            logger.info("当前采集信息已有评价信息,无需生成默认评价信息: " + collectionNumber);
            return;
        }
        InfoDeviceUsageEvaluation evaluation = new InfoDeviceUsageEvaluation();
        // 基本信息
        evaluation.setDeviceCode(deviceCode);
        evaluation.setSerialNumber(serialNumber);
        evaluation.setUniqueNumber(System.currentTimeMillis() + "");
        evaluation.setCollectionNumber(collectionNumber);
        // 填写的信息与默认信息
        evaluation.setDeviceUseDurationTime(0);
        evaluation.setDeviceDepartment(deviceDepartment);
        evaluation.setExperienceLevel(EvaluationLevelEnum.VERY_GOOD.getLevel());
        evaluation.setReliabilityLevel(EvaluationLevelEnum.VERY_GOOD.getLevel());
        evaluation.setKnownError("");
        evaluation.setOtherError("");
        evaluation.setHasError(false);
        evaluation.setRemarkInfo("无");
        evaluation.setRecordName("系统管理员");
        // 存储默认使用评价信息
        deviceUsageEvaluationRepository.save(evaluation);
    }

    /**
     * 更新采集完成后的仪器评价信息表格
     *
     * @param evaluationInfo 评价信息
     * @return 结果
     */
    @Override
    public CommonResult<String> updateDeviceUsageEvaluationInfoFromMobile(InfoDeviceUsageEvaluation evaluationInfo) {
        // 说明采集场次号不合法
        if (evaluationInfo.getCollectionNumber() == null || evaluationInfo.getCollectionNumber() == 0) {
            deviceUsageEvaluationRepository.save(evaluationInfo);
            logger.info("新增仪器评价信息:" + evaluationInfo);
            return CommonResult.success(JSON.toJSONString(evaluationInfo));
        }
        // 查询历史评价信息
        InfoDeviceUsageEvaluation historyEvaluationInfo = deviceUsageEvaluationRepository.findByCollectionNumber(evaluationInfo.getCollectionNumber());
        if (historyEvaluationInfo == null) {
            logger.error("更新仪器评价信息失败,不存在默认评价信息." + evaluationInfo);
            return CommonResult.failed("更新仪器评价信息失败,不存在默认评价信息.");
        }
        // 更新历史评价信息的各种信息
        historyEvaluationInfo.setDeviceUseDurationTime(evaluationInfo.getDeviceUseDurationTime());
        historyEvaluationInfo.setDeviceDepartment(evaluationInfo.getDeviceDepartment());
        historyEvaluationInfo.setExperienceLevel(evaluationInfo.getExperienceLevel());
        historyEvaluationInfo.setReliabilityLevel(evaluationInfo.getReliabilityLevel());
        historyEvaluationInfo.setHasError(evaluationInfo.getHasError());
        historyEvaluationInfo.setKnownError(evaluationInfo.getKnownError());
        historyEvaluationInfo.setOtherError(evaluationInfo.getOtherError());
        historyEvaluationInfo.setRemarkInfo(evaluationInfo.getRemarkInfo());
        historyEvaluationInfo.setRecordName(evaluationInfo.getRecordName());

        // 更新数据
        historyEvaluationInfo = deviceUsageEvaluationRepository.save(historyEvaluationInfo);
        logger.info("成功更新仪器评价信息:" + evaluationInfo);
        return CommonResult.success(JSON.toJSONString(historyEvaluationInfo));
    }


    /**
     * 通过采集场次号获取评价信息
     *
     * @param collectionNumber 采集场次号
     * @return 评价信息
     */
    @Override
    public CommonResult<InfoDeviceUsageEvaluation> getDeviceUsageEvaluationInfoByCollectionNumber(int collectionNumber) {
        return CommonResult.success(deviceUsageEvaluationRepository.findByCollectionNumber(collectionNumber));
    }


    /**
     * 根据仪器号查询该仪器完成的评价信息(根据仪器号)
     *
     * @param deviceCode 仪器号
     * @return 评价信息列表
     */
    @Override
    public CommonResult<Page<InfoDeviceUsageEvaluation>> getDeviceUsageEvaluationListByDeviceCode(int deviceCode, int page, int size) {
        return CommonResult.success(deviceUsageEvaluationRepository.findByDeviceCode(deviceCode, PageRequest.of(page, size)));
    }


    /**
     * 根据仪器号及序列号查询该仪器完成的评价信息(根据仪器号与序列号)
     *
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @return 评价信息列表
     */
    @Override
    public CommonResult<List<InfoDeviceUsageEvaluation>> getDeviceUsageEvaluationListByDeviceCodeAndSerialNumber(int deviceCode, String serialNumber, int page, int size) {
        return CommonResult.success(deviceUsageEvaluationRepository.findByDeviceCodeAndSerialNumber(deviceCode, serialNumber, PageRequest.of(page, size)));
    }


    @Override
    protected JpaRepository<InfoDeviceUsageEvaluation, Integer> initRepository() {
        return deviceUsageEvaluationRepository;
    }


}
