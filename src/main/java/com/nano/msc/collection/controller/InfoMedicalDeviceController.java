package com.nano.msc.collection.controller;

import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.service.InfoMedicalDeviceService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 医疗仪器信息控制器
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/24 0:55
 */
@Slf4j
@Api(tags = "医疗仪器接口", description = "InfoMedicalDeviceController")
@RestController
@RequestMapping("/info-medical-device")
public class InfoMedicalDeviceController {

    @Autowired
    private InfoMedicalDeviceService medicalDeviceService;

    /**
     * 新增医疗仪器信息
     *
     * @param medicalDevice 术后评价信息表
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增一条仪器医疗仪器信息")
    public CommonResult addMedicalDeviceInfo(@Valid @RequestBody InfoMedicalDevice medicalDevice) {
        return medicalDeviceService.addMedicalDeviceInfo(medicalDevice);
    }

    /**
     * 更新医疗仪器信息
     *
     * @param medicalDevice 术后评价信息表
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新一条仪器医疗仪器信息")
    public CommonResult updateMedicalDeviceInfo(@Valid @RequestBody InfoMedicalDevice medicalDevice) {
        return medicalDeviceService.updateMedicalDeviceInfo(medicalDevice);
    }

    /**
     * 获取医疗仪器信息列表(根据deviceCode)
     */
    @GetMapping("/get-medical-device-info-by-device-code")
    @ApiOperation(value = "获取医疗仪器信息列表(通过DeviceCode)")
    public CommonResult getMedicalDeviceInfoListByDeviceCode(@RequestParam(value = "deviceCode", defaultValue = "30") int deviceCode) {
        return medicalDeviceService.getMedicalDeviceInfoListByDeviceCode(deviceCode);
    }


    /**
     * 获取医疗仪器信息列表(全部)
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取医疗仪器信息列表(全部)")
    public CommonResult getMedicalDeviceInfoList(@Min(value = 0, message = "页数不能小于1") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @Min(value = 1, message = "数据个数不能小于1") @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return medicalDeviceService.list(page, size);
    }




    /**
     * 获取接入仪器的全部仪器个数(总的)
     */
    @GetMapping("/access-in-system-counter-total")
    @ApiOperation(value = "获取接入仪器的全部仪器个数(总的)")
    public CommonResult<Integer> getMedicalDeviceAccessInSystemCounterTotal() {
        return medicalDeviceService.getMedicalDeviceAccessInSystemCounterTotal();
    }

    /**
     * 获取接入仪器的仪器个数(按仪器类别)
     */
    @GetMapping("/access-in-system-counter-by-type")
    @ApiOperation(value = "获取接入仪器的仪器个数(按仪器类别)")
    public CommonResult<Map<String, Integer>> getMedicalDeviceAccessInSystemCounterByType() {
        return medicalDeviceService.getMedicalDeviceAccessInSystemCounterByType();
    }

    /**
     * 查询某仪器拥有的序列号列表
     * @param deviceCode 仪器号
     * @return 该仪器号下面对应的仪器序列号列表
     */
    @ApiOperation("查询某仪器拥有的序列号列表")
    @GetMapping("/device-serial-number-list")
    public CommonResult getSerialNumberListByDeviceCode(@RequestParam(value = "deviceCode", defaultValue = "30") Integer deviceCode) {
        return medicalDeviceService.getSerialNumberListByDeviceCode(deviceCode);
    }




}
