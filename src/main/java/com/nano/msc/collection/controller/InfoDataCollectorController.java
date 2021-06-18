package com.nano.msc.collection.controller;

import com.nano.msc.GlobalContext;
import com.nano.msc.collection.service.InfoDeviceDataCollectionService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 数据采集器控制器
 * Usage:
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 16:52
 */
@Slf4j
@Api(tags = "数据采集器接口", description = "InfoDataCollectorController")
@RestController
@RequestMapping("/info-data-collector")
public class InfoDataCollectorController {

    /**
     * 查询全部采集器信息
     */
    @ApiOperation("查询全部采集器信息")
    @GetMapping("/get-all-data-collector-info")
    public CommonResult getAllDataCollectorInfo() {
        return CommonResult.success(GlobalContext.dataCollectorSet);

    }




}
