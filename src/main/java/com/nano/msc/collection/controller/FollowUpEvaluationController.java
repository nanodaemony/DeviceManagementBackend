package com.nano.msc.collection.controller;

import com.nano.msc.collection.service.FollowUpEvaluationService;
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
 * Description: 仪器追踪评价控制器
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/9 14:28
 */
@Slf4j
@Api(tags = "追踪评价接口", description = "FollowUpEvaluationController")
@RestController
@RequestMapping("/follow-up-evaluation")
public class FollowUpEvaluationController {

    @Autowired
    private FollowUpEvaluationService followUpEvaluationService;

    /**
     * 获取全部仪器的追踪评价信息(按DeviceCode与SerialNumber统计)
     *
     * @return 详细信息
     */
    @ApiOperation("获取全部仪器的追踪评价信息(按DeviceCode与SerialNumber统计)")
    @GetMapping("/cover-all-by-device-code-and-serial-number")
    public CommonResult getDeviceFollowUpEvaluationCoverDeviceAllByDeviceCodeAndSerialNumber() {
        return followUpEvaluationService.getDeviceFollowUpEvaluationCoverDeviceAllByDeviceCodeAndSerialNumber();
    }







}
