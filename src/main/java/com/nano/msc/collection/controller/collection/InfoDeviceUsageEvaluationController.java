package com.nano.msc.collection.controller.collection;

import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.collection.service.InfoDeviceUsageEvaluationService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

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
@Api(tags = "仪器使用评价接口", description = "InfoDeviceUsageEvaluationController")
@RestController
@RequestMapping("/info-device-usage-evaluation")
public class InfoDeviceUsageEvaluationController {


    @Autowired
    private InfoDeviceUsageEvaluationService deviceUsageEvaluationService;


    /**
     * 查询仪器使用评价信息(根据采集场次号号)
     *
     * @param collectionNumber 仪器号
     * @return 评价信息列表
     */
    @ApiOperation("查询仪器使用评价信息(根据采集场次号)")
    @GetMapping("/get-device-usage-evaluation-by-collection-number")
    public CommonResult getDeviceUsageEvaluationInfoByCollectionNumber (
            @RequestParam(value = "collectionNumber", defaultValue = "1") Integer collectionNumber) {
        return deviceUsageEvaluationService.getDeviceUsageEvaluationInfoByCollectionNumber(collectionNumber);
    }


    /**
     * 新增条术后仪器评价信息
     *
     * @param afterCollectionEvaluation 术后评价信息表
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增一条仪器使用评价信息")
    public CommonResult addAfterCollectionEvaluation(@Valid @RequestBody InfoDeviceUsageEvaluation afterCollectionEvaluation) {
        return deviceUsageEvaluationService.addDeviceUsageEvaluationTableByMobile(afterCollectionEvaluation);
    }


    /**
     * 查询仪器使用评价信息(根据仪器号)
     *
     * @param deviceCode 仪器号
     * @return 评价信息列表
     */
    @ApiOperation("查询仪器使用评价信息(根据仪器号)")
    @GetMapping("/get-device-usage-evaluation-list-by-devicecode")
    public CommonResult getDeviceUsageEvaluationListByDeviceCode (
            @RequestParam(value = "deviceCode", defaultValue = "30") Integer deviceCode,
            @Min(value = 0, message = "页数不能小于1") @RequestParam(value = "page", defaultValue = "0") Integer page,
            @Min(value = 1, message = "数据个数不能小于1") @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return deviceUsageEvaluationService.getDeviceUsageEvaluationListByDeviceCode(deviceCode, page, size);
    }


    /**
     * 根据仪器号及序列号查询该仪器完成的评价信息
     *
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @return 评价信息列表
     */
    @ApiOperation("查询仪器使用评价信息(根据仪器号及序列号)")
    @GetMapping("/get-device-usage-evaluation-list-by-devicecode-serialnumber")
    public CommonResult getDeviceUsageEvaluationList (
            @RequestParam(value = "deviceCode", defaultValue = "30") Integer deviceCode,
            @RequestParam(value = "serialNumber") String serialNumber,
            @Min(value = 0, message = "页数不能小于1") @RequestParam(value = "page", defaultValue = "0") Integer page,
            @Min(value = 1, message = "数据个数不能小于1") @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return deviceUsageEvaluationService.getDeviceUsageEvaluationListByDeviceCodeAndSerialNumber(deviceCode, serialNumber, page, size);
    }







}
