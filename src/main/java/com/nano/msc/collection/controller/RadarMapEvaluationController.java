package com.nano.msc.collection.controller;

import com.nano.msc.collection.service.RadarMapEvaluationService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 雷达图评价控制器
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/9 14:28
 */
@Slf4j
@Api(tags = "雷达图评价接口", description = "RadarMapEvaluationController")
@RestController
@RequestMapping("/radar-map-evaluation")
public class RadarMapEvaluationController {

    @Autowired
    private RadarMapEvaluationService radarMapEvaluationService;

    /**
     * 雷达图评价结果
     *
     * @return 详细信息
     */
    @ApiOperation("获取雷达图评价结果")
    @GetMapping("/get-device-radar-evaluation-map-by-device-type")
    public CommonResult getDeviceRadarMapEvaluationMap() {
        return radarMapEvaluationService.getDeviceRadarMapEvaluationMap();
    }



}
