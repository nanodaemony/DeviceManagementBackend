package com.nano.msc.collection.controller.collection;

import com.nano.msc.collection.param.ParamPad;
import com.nano.msc.collection.service.InfoDeviceDataCollectionService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 仪器数据采集控制器
 * Usage:
 * 1. 服务器是否在线接口
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 16:52
 */
@Slf4j
@Api(tags = "InfoDeviceCollectionController", description = "DeviceCollection")
@RestController
@RequestMapping("/device-collection")
public class InfoDeviceCollectionController {

    @Autowired
    private InfoDeviceDataCollectionService deviceCollectionService;

    /**
     * 查询服务器是否在线
     */
    @GetMapping("/network")
    @ApiOperation(value = "查询服务器是否在线")
    public CommonResult queryNetworkStatus() {
        log.info("查询服务器状态.");
        return CommonResult.success();
    }

    /**
     * 上传仪器信息FromPad
     *
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/post-collection-info-pad")
    @ApiOperation(value = "上传仪器信息FromPad")
    public CommonResult postMedicalDeviceInfoFromPad(@Valid @RequestBody ParamPad paramPad) {
        return deviceCollectionService.saveMedicalDeviceInfoFromPad(paramPad);
    }

    /**
     * 仪器开始采集FromPad
     *
     * @param paramPad 手术标记事件
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/device-start-collection-pad")
    @ApiOperation(value = "仪器开始采集FromPad")
    public CommonResult deviceStartCollectionPad(@Valid @RequestBody ParamPad paramPad) {
        return deviceCollectionService.startDeviceDataCollectionPad(paramPad);
    }

    /**
     * 仪器完成采集FromPad
     *
     * @param paramPad 手术标记事件
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/device-finish-collection-pad")
    @ApiOperation(value = "仪器完成采集FromPad")
    public CommonResult deviceFinishCollectionPad(@Valid @RequestBody ParamPad paramPad) {
        return deviceCollectionService.finishDeviceDataCollectionPad(paramPad);
    }

    /**
     * 仪器放弃采集FromPad
     *
     * @param paramPad 手术标记事件
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/device-abandon-collection-pad")
    @ApiOperation(value = "仪器放弃采集FromPad")
    public CommonResult deviceAbandonCollectionPad(@Valid @RequestBody ParamPad paramPad) {
        return deviceCollectionService.abandonDeviceDataCollectionPad(paramPad);
    }


}
