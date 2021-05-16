package com.nano.msc.collection.controller.collection;

import com.nano.msc.collection.service.InfoMedicalDeviceService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

import io.swagger.annotations.Api;
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
@Api(tags = "InfoDeviceController", description = "InfoMedicalDevice")
@RestController
@RequestMapping("/info-medical-device")
public class InfoMedicalDeviceController {

    @Autowired
    private InfoMedicalDeviceService medicalDeviceService;

    @GetMapping("/list")
    public CommonResult getDeviceInfo(@Min(value = 0, message = "页数不能小于1") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @Min(value = 1, message = "数据个数不能小于1") @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return medicalDeviceService.list(page, size);
    }


}
