package com.nano.msc.collection.service.impl;

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
 * @see #addDeviceUsageEvaluationTableByMobile(InfoDeviceUsageEvaluation) 存储由APP上传而来的仪器使用评价表
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
    private SystemLogService logService;

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
     * 保存采集完成后的仪器评价信息表格
     *
     * @param evaluationTable 平板参数
     * @return 结果
     */
    @Override
    public CommonResult<String> addDeviceUsageEvaluationTableByMobile(InfoDeviceUsageEvaluation evaluationTable) {
        try {
            if (deviceCollectionRepository.findByCollectionNumber(evaluationTable.getCollectionNumber()) == null) {
                return CommonResult.failed("没有该采集号记录." + evaluationTable.toString());
            }
            evaluationTable = deviceUsageEvaluationRepository.save(evaluationTable);
            // 持久化存储信息并返回UniqueNumber.
            logService.info("成功收到采集后仪器评价信息:" + evaluationTable.toString());
            return CommonResult.success(evaluationTable.getUniqueNumber());
        } catch (Exception e) {
            logService.info("解析评价信息失败：");
            e.printStackTrace();
            return CommonResult.failed("解析评价信息失败." + evaluationTable.toString());
        }
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

    /**
     * 新增默认的仪器使用评价
     *
     * @param collectionNumber 采集场次号
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @param deviceDepartment 使用科室
     * @return 是否成功
     */
    @Override
    public void addDefaultUsageEvaluation(Integer collectionNumber, Integer deviceCode, String serialNumber, String deviceDepartment) {

        // 如果已经存在
        if (deviceUsageEvaluationRepository.existsByCollectionNumber(collectionNumber)) {
            logService.info("当前采集信息已有评价信息,不在生成默认评价信息: " + collectionNumber);
            return;
        }
        InfoDeviceUsageEvaluation evaluation = new InfoDeviceUsageEvaluation();
        evaluation.setUniqueNumber(System.currentTimeMillis() + "");
        evaluation.setDeviceCode(deviceCode);
        evaluation.setSerialNumber(serialNumber);
        evaluation.setCollectionNumber(collectionNumber);
        evaluation.setDeviceDepartment(deviceDepartment);
        // 默认好评
        evaluation.setExperienceLevel(EvaluationLevelEnum.VERY_GOOD.getLevel());
        evaluation.setReliabilityLevel(EvaluationLevelEnum.VERY_GOOD.getLevel());
        evaluation.setKnownError("");
        evaluation.setOtherError("");
        evaluation.setHasError(false);
        evaluation.setRemarkInfo("无");
        evaluation.setRecordName("系统管理员");
        deviceUsageEvaluationRepository.save(evaluation);
    }


    @Override
    protected JpaRepository<InfoDeviceUsageEvaluation, Integer> initRepository() {
        return deviceUsageEvaluationRepository;
    }


}
