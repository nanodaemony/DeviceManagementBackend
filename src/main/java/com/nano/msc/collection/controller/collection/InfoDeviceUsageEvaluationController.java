package com.nano.msc.collection.controller.collection;

import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.collection.service.InfoDeviceUsageEvaluationService;
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
 * Description: 完成采集后仪器评价控制器
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 21:45
 */
@Slf4j
@Api(tags = "DeviceUsageEvaluationController", description = "DeviceUsageEvaluationController")
@RestController
@RequestMapping("/info-device-usage-evaluation")
public class InfoDeviceUsageEvaluationController {


    @Autowired
    private InfoDeviceUsageEvaluationService afterCollectionEvaluationService;

    /**
     * 新增条术后仪器评价信息
     *
     * @param afterCollectionEvaluation 术后评价信息表
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增一条仪器使用评价信息")
    public CommonResult addAfterCollectionEvaluation(@Valid @RequestBody InfoDeviceUsageEvaluation afterCollectionEvaluation) {
        return afterCollectionEvaluationService.saveDeviceUsageEvaluationTable(afterCollectionEvaluation);
    }





}
