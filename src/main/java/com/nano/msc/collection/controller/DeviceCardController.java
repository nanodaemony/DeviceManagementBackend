package com.nano.msc.collection.controller;

import com.nano.msc.collection.service.DeviceCardService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 仪器卡片控制器
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/1 15:23
 */
@Slf4j
@Api(tags = "仪器卡片接口", description = "DeviceCardController")
@RestController
@RequestMapping("/device-card")
public class DeviceCardController {

    @Autowired
    private DeviceCardService deviceCardService;


    /**
     * 获取某型号仪器的仪器卡片展示信息(某仪器型号)
     * @param deviceCode 仪器号
     * @return 仪器信息
     */
    @ApiOperation("获取仪器卡片展示信息(整体)")
    @GetMapping("/info-device-model")
    public CommonResult getDeviceCardInfoForOneDeviceModel(@RequestParam(value = "deviceCode", defaultValue = "30") int deviceCode) {
        return deviceCardService.getDeviceCardInfoForOneDeviceModel(deviceCode);
    }


    /**
     * 获取某型号仪器的仪器卡片展示信息(某仪器型号下一个具体仪器)
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @return 仪器信息
     */
    @ApiOperation("获取仪器卡片展示信息(具体一个仪器)")
    @GetMapping("/info-device-detail")
    public CommonResult getDeviceCardInfoForOneDeviceDetail(@RequestParam(value = "deviceCode", defaultValue = "30") int deviceCode,
                                          @RequestParam(value = "deviceSerialNumber", defaultValue = "11111") String serialNumber) {
        return deviceCardService.getDeviceCardInfoForOneDeviceDetail(deviceCode, serialNumber);
    }

}
