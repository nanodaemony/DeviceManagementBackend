package com.nano.msc.devicedata.controller;

import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.devicedata.param.ParamDeviceDataPad;
import com.nano.msc.devicedata.service.DeviceDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 仪器数据控制器
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 23:27
 */
@Slf4j
@Api(tags = "医疗数据接口", description = "DeviceData")
@RestController
@RequestMapping("/device-data")
public class DeviceDataController {

    @Autowired
    private DeviceDataService deviceDataService;

    /**
     * 解析由平板上传的以太网仪器的数据并存储
     */
    @PostMapping("/parse-ethernet-device-data-from-pad")
    @ApiOperation(value = "新增Pad上传的仪器数据")
    public CommonResult<String> parseEthernetDeviceDataAndSaveFromPad(@RequestBody ParamDeviceDataPad paramDeviceDataPad) {
        return deviceDataService.parseEthernetDeviceDataAndSaveFromPad(paramDeviceDataPad);
    }


    /**
     * 获取仪器历史数据(用于平台展示)
     */
    @PostMapping("/get-history-data-for-platform/{collectionNumber}/{deviceCode}/{serialNumber}")
    @ApiOperation(value = "获取仪器历史数据(用于平台展示)")
    public CommonResult getHistoryDataForPlatform(@PathVariable Integer collectionNumber,
                                                          @PathVariable Integer deviceCode,
                                                          @PathVariable String serialNumber) {
        return deviceDataService.getHistoryDeviceData(collectionNumber, deviceCode, serialNumber, 0, 1000);
    }



}
