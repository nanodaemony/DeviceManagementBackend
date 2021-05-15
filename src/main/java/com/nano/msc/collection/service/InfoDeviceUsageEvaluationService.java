package com.nano.msc.collection.service;

import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.common.service.BaseService;
import com.nano.msc.common.vo.CommonResult;

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

}
