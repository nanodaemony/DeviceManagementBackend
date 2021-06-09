package com.nano.msc.collection.controller.collection;

import com.nano.msc.collection.service.EconomicEvaluationService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 经济性评价控制器
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/9 14:28
 */
@Slf4j
@Api(tags = "经济性评价接口", description = "EconomicEvaluationController")
@RestController
@RequestMapping("/economic-evaluation")
public class EconomicEvaluationController {

    @Autowired
    private EconomicEvaluationService economicEvaluationService;

    /**
     * 获取全部仪器的经济性评价信息(按DeviceCode与SerialNumber统计)
     *
     * @return 详细信息
     */
    @ApiOperation("获取全部仪器的经济性评价信息(按DeviceCode与SerialNumber统计)")
    @GetMapping("/cover-all-by-device-code-and-serial-number")
    public CommonResult getDeviceEconomicEvaluationCoverDeviceAllByDeviceCodeAndSerialNumber() {
        return economicEvaluationService.getDeviceEconomicEvaluationCoverDeviceAllByDeviceCodeAndSerialNumber();
    }






}
