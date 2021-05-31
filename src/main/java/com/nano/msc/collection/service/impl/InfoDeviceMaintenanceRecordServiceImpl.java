package com.nano.msc.collection.service.impl;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoDeviceMaintenanceRecord;
import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.collection.repository.InfoDeviceMaintenanceRecordRepository;
import com.nano.msc.collection.service.InfoDeviceMaintenanceRecordService;
import com.nano.msc.common.service.BaseServiceImpl;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Description: 仪器维修记录表服务
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/30 15:55
 */
@Service
public class InfoDeviceMaintenanceRecordServiceImpl extends BaseServiceImpl<InfoDeviceMaintenanceRecord, Integer> implements InfoDeviceMaintenanceRecordService {

    @Autowired
    private InfoDeviceMaintenanceRecordRepository maintenanceRecordRepository;

    /**
     * 新增一个维修记录
     *
     * @param maintenanceRecord 维修记录表
     * @return 唯一序列号
     */
    @Override
    public CommonResult<String> addOneMaintenanceRecord(InfoDeviceMaintenanceRecord maintenanceRecord) {
        maintenanceRecord = maintenanceRecordRepository.save(maintenanceRecord);
        return CommonResult.success();
    }



    @Override
    protected JpaRepository<InfoDeviceMaintenanceRecord, Integer> initRepository() {
        return maintenanceRecordRepository;
    }
}
