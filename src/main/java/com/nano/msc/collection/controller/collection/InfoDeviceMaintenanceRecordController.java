package com.nano.msc.collection.controller.collection;

import com.nano.msc.collection.entity.InfoDeviceMaintenanceRecord;
import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.collection.service.InfoDeviceMaintenanceRecordService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 仪器维修记录Controller
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/30 15:59
 */
@Slf4j
@Api(tags = "仪器维修记录接口", description = "InfoDeviceMaintenanceRecordController")
@RestController
@RequestMapping("/info-device-maintenance-record")
public class InfoDeviceMaintenanceRecordController {

    @Autowired
    private InfoDeviceMaintenanceRecordService maintenanceRecordService;

    /**
     * 新增仪器维修记录信息
     *
     * @param deviceMaintenanceRecord  维修记录表
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增一条仪器维修记录信息")
    public CommonResult addAfterCollectionEvaluation(@Valid @RequestBody InfoDeviceMaintenanceRecord deviceMaintenanceRecord) {
        log.info(deviceMaintenanceRecord.toString());
        return maintenanceRecordService.save(deviceMaintenanceRecord);
    }

}
