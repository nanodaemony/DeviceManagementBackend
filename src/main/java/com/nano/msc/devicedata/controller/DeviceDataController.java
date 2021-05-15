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
@Api(tags = "DeviceDataController", description = "DeviceData")
@RestController
@RequestMapping("/device-data")
public class DeviceDataController {

    @Autowired
    private DeviceDataService deviceDataService;

    /**
     * 新增自定义的标记事件
     */
    @PostMapping("/add-device-data-pad")
    @ApiOperation(value = "新增Pad上传的仪器数据")
    public CommonResult<String> addDeviceDataAdd(@RequestBody ParamDeviceDataPad paramDeviceDataPad) {
        return deviceDataService.parseDataAndSaveFromPad(paramDeviceDataPad);
    }


    /**
     * 新增自定义的标记事件
     */
    @PostMapping("/add-device-data-serial")
    @ApiOperation(value = "新增串口采集器上传的仪器数据")
    public CommonResult<String> addDeviceDataSerial(String paramDeviceDataSerial) {
        return deviceDataService.parseDataAndSaveFromSerial(paramDeviceDataSerial);
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
