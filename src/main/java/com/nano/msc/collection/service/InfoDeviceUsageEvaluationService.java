package com.nano.msc.collection.service;

import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.common.service.BaseService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.data.domain.Page;

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
     * 通过采集场次号获取评价信息
     *
     * @param collectionNumber 采集场次号
     * @return 评价信息
     */
    CommonResult<InfoDeviceUsageEvaluation> getDeviceUsageEvaluationInfoByCollectionNumber(int collectionNumber);

    /**
     * 保存采集完成后的仪器评价信息表格
     *
     * @param evaluationTable 平板参数
     * @return 结果
     */
    CommonResult<String> addDeviceUsageEvaluationTableByMobile(InfoDeviceUsageEvaluation evaluationTable);

    /**
     * 根据仪器号查询该仪器完成的评价信息
     *
     * @param deviceCode 仪器号
     * @return 评价信息列表
     */
    CommonResult<Page<InfoDeviceUsageEvaluation>> getDeviceUsageEvaluationListByDeviceCode(int deviceCode, int page, int size);


    /**
     * 根据仪器号及序列号查询该仪器完成的评价信息
     *
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @return 评价信息列表
     */
    CommonResult<List<InfoDeviceUsageEvaluation>> getDeviceUsageEvaluationListByDeviceCodeAndSerialNumber(int deviceCode, String serialNumber, int page, int size);

    /**
     * 新增默认的仪器使用评价
     *
     * @param collectionNumber 采集场次号
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @param deviceDepartment 使用科室
     */
    void addDefaultUsageEvaluation(Integer collectionNumber, Integer deviceCode, String serialNumber, String deviceDepartment);
}
