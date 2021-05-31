package com.nano.msc.collection.service;

import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.common.service.BaseService;
import com.nano.msc.common.vo.CommonResult;

import java.util.List;

/**
 * Description: 采集完成后仪器评价信息服务
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:14
 */
public interface InfoDeviceUsageEvaluationService extends BaseService<InfoDeviceUsageEvaluation, Integer> {


    /**
     * 保存采集完成后的仪器评价信息表格
     *
     * @param evaluationTable 平板参数
     * @return 结果
     */
    CommonResult<String> saveDeviceUsageEvaluationTable(InfoDeviceUsageEvaluation evaluationTable);

    /**
     * 根据仪器号查询该仪器完成的评价信息
     *
     * @param deviceCode 仪器号
     * @return 评价信息列表
     */
    CommonResult<List<InfoDeviceUsageEvaluation>> getDeviceUsageEvaluationListByDeviceCode(int deviceCode, int page, int size);


    /**
     * 根据仪器号及序列号查询该仪器完成的评价信息
     *
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @return 评价信息列表
     */
    CommonResult<List<InfoDeviceUsageEvaluation>> getDeviceUsageEvaluationListByDeviceCodeAndSerialNumber(int deviceCode, String serialNumber, int page, int size);
}
