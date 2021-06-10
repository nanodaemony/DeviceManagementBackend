package com.nano.msc.collection.service;

import com.nano.msc.collection.entity.InfoDeviceMaintenanceRecord;
import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.common.service.BaseService;
import com.nano.msc.common.vo.CommonResult;

import java.util.List;

/**
 * Description: 仪器维修记录表服务
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:14
 */
public interface InfoDeviceMaintenanceRecordService extends BaseService<InfoDeviceMaintenanceRecord, Integer> {

    /**
     * 新增一个维修记录
     *
     * @param maintenanceRecord 维修记录表
     * @return 唯一序列号
     */
    CommonResult<String> addOneMaintenanceRecord(InfoDeviceMaintenanceRecord maintenanceRecord);

    /**
     * 通过仪器号与序列号查询维修记录(按故障时间降序)
     *
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @return 维修记录
     */
    CommonResult getMaintenanceRecordByDeviceCodeAndSerialNumberOrderByTimeErrorOccurDesc(int deviceCode, String serialNumber);
}
