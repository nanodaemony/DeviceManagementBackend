package com.nano.msc.system.log.controller;

import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.system.log.entity.SystemLog;
import com.nano.msc.system.log.enums.SystemLogEnum;
import com.nano.msc.system.log.service.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.constraints.Min;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 系统日志控制器
 * @author nano
 */
@RestController
@RequestMapping(value = "/system/log")
@CrossOrigin
@Api(tags = "日志接口", description = "SystemLogController")
public class SystemLogController {

    @Autowired
    private SystemLogService logService;


    @GetMapping("/list/info/log")
    @ApiOperation(value = "获取Info日志")
    public CommonResult listInfoLog(@Min(value = 0, message = "页数不能小于1") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @Min(value = 1, message = "数据个数不能小于1") @RequestParam(value = "size", defaultValue = "30") Integer size) {
        return CommonResult.success(logService.listCurrentDayAndLogLevel(page, size, SystemLogEnum.INFO.getCode()));
    }

    @GetMapping("/list/error/log")
    @ApiOperation(value = "获取Error日志")
    public CommonResult listErrorLog(@Min(value = 0, message = "页数不能小于1") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @Min(value = 1, message = "数据个数不能小于1") @RequestParam(value = "size", defaultValue = "30") Integer size) {
        return CommonResult.success(logService.listCurrentDayAndLogLevel(page, size, SystemLogEnum.ERROR.getCode()));
    }



}
