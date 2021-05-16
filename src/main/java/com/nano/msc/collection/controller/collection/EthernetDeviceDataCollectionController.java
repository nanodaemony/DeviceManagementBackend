package com.nano.msc.collection.controller.collection;

import com.nano.msc.collection.param.ParamPad;
import com.nano.msc.collection.service.EthernetDeviceDataCollectionService;
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
 * Description: 以太网类仪器数据采集控制器
 * Usage:
 *
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/16 21:33
 */
@Slf4j
@Api(tags = "EthernetDeviceDataCollectionController", description = "EthernetDeviceDataCollectionController")
@RestController
@RequestMapping("/ethernet-device-data-collection")
public class EthernetDeviceDataCollectionController {

    /**
     * 网口类仪器数据采集服务类
     */
    @Autowired
    private EthernetDeviceDataCollectionService ethernetDeviceDataCollectionService;

    /**
     * 上传仪器信息FromPad
     *
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/ethernet-medical-device-info-pad")
    @ApiOperation(value = "上传仪器信息FromPad")
    public CommonResult postMedicalDeviceInfoFromPad(@Valid @RequestBody ParamPad paramPad) {
        return ethernetDeviceDataCollectionService.saveEthernetMedicalDeviceInfoFromPad(paramPad);
    }

    /**
     * 仪器开始采集FromPad
     *
     * @param paramPad 手术标记事件
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/ethernet-device-start-collection-pad")
    @ApiOperation(value = "仪器开始采集FromPad")
    public CommonResult deviceStartCollectionPad(@Valid @RequestBody ParamPad paramPad) {
        return ethernetDeviceDataCollectionService.startEthernetDeviceDataCollectionPad(paramPad);
    }

    /**
     * 仪器完成采集FromPad
     *
     * @param paramPad 手术标记事件
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/ethernet-device-finish-collection-pad")
    @ApiOperation(value = "仪器完成采集FromPad")
    public CommonResult deviceFinishCollectionPad(@Valid @RequestBody ParamPad paramPad) {
        return ethernetDeviceDataCollectionService.finishEthernetDeviceDataCollectionPad(paramPad);
    }

    /**
     * 仪器放弃采集FromPad
     *
     * @param paramPad 手术标记事件
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/ethernet-device-abandon-collection-pad")
    @ApiOperation(value = "仪器放弃采集FromPad")
    public CommonResult deviceAbandonCollectionPad(@Valid @RequestBody ParamPad paramPad) {
        return ethernetDeviceDataCollectionService.abandonEthernetDeviceDataCollectionPad(paramPad);
    }


}
