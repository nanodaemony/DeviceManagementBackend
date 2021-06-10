package com.nano.msc.collection.controller.collection;

import com.nano.msc.collection.entity.InfoDeviceMaintenanceRecord;
import com.nano.msc.collection.service.InfoDeviceMaintenanceRecordService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    /**
     * 通过仪器号与序列号查询维修记录
     *
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @return 维修记录
     */
    @ApiOperation("获取维修记录信息(根据DeviceCode与SerialNumber)")
    @GetMapping("/get-maintenance-record-by-device-code-and-serial-number")
    public CommonResult getMaintenanceRecordByDeviceCodeAndSerialNumber(@RequestParam(value = "deviceCode", defaultValue = "30") int deviceCode,
                                                            @RequestParam(value = "serialNumber", defaultValue = "11111") String serialNumber) {
        return maintenanceRecordService.getMaintenanceRecordByDeviceCodeAndSerialNumberOrderByTimeErrorOccurDesc(deviceCode, serialNumber);
    }

}
