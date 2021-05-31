package com.nano.msc.collection.service.impl;

import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.collection.repository.InfoDeviceUsageEvaluationRepository;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.service.InfoDeviceUsageEvaluationService;
import com.nano.msc.common.service.BaseServiceImpl;
import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.system.log.service.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Description: 仪器使用评价服务类
 * Usage:
 * @see #saveDeviceUsageEvaluationTable(InfoDeviceUsageEvaluation) 存储由APP上传而来的仪器使用评价表
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
     * 保存采集完成后的仪器评价信息表格
     *
     * @param evaluationTable 平板参数
     * @return 结果
     */
    @Override
    public CommonResult<String> saveDeviceUsageEvaluationTable(InfoDeviceUsageEvaluation evaluationTable) {
        try {
            if (deviceCollectionRepository.findByCollectionNumber(evaluationTable.getCollectionNumber()) == null) {
                return CommonResult.failed("没有该采集号记录." + evaluationTable.toString());
            }
            // 持久化存储信息并返回UniqueNumber.
            evaluationTable = deviceUsageEvaluationRepository.save(evaluationTable);
            logService.info("成功收到采集后仪器评价信息:" + evaluationTable.toString());
            return CommonResult.success(evaluationTable.getUniqueNumber());
        } catch (Exception e) {
            return CommonResult.failed("开始采集失败,上传信息解析失败.:" + evaluationTable.toString());
        }
    }

    /**
     * 根据仪器号查询该仪器完成的评价信息(根据仪器号)
     *
     * @param deviceCode 仪器号
     * @return 评价信息列表
     */
    @Override
    public CommonResult<List<InfoDeviceUsageEvaluation>> getDeviceUsageEvaluationListByDeviceCode(int deviceCode, int page, int size) {
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
