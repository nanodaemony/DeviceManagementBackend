package com.nano.msc.collection.controller;

import com.nano.msc.collection.service.MaintenanceEvaluationService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 维保评价控制器
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/9 16:13
 */
@Slf4j
@Api(tags = "维保评价接口", description = "MaintenanceEvaluationController")
@RestController
@RequestMapping("/maintenance-evaluation")
public class MaintenanceEvaluationController {

    @Autowired
    private MaintenanceEvaluationService maintenanceEvaluationService;


    /**
     * 获取全部仪器的维保记录信息(按DeviceCode与SerialNumber统计)
     *
     * @return 详细信息
     */
    @ApiOperation("获取全部仪器的维保记录信息(按DeviceCode与SerialNumber统计)")
    @GetMapping("/cover-all-by-device-code-and-serial-number")
    public CommonResult getDeviceFollowUpEvaluationCoverDeviceAllByDeviceCodeAndSerialNumber() {
        return maintenanceEvaluationService.getMaintenanceEvaluationByDeviceCodeAndSerialNumber();
    }

}
