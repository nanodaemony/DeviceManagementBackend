package com.nano.msc.collection.controller.collection;

import com.nano.msc.collection.service.DeviceManageService;
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
 * Description: 仪器管理相关接口
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/31 15:27
 */
@Slf4j
@Api(tags = "仪器管理接口", description = "DeviceManageController")
@RestController
@RequestMapping("/device-manage")
public class DeviceManageController {

    @Autowired
    private DeviceManageService deviceManageService;


    /**
     * 获取历史开机的仪器数量
     *
     * @param days 历史天数
     * @return 开机信息
     */
    @ApiOperation(value = "获取过去一段时间内每一天开机仪器数量(全部仪器)", notes = "参数为历史天数,如7,15,30")
    @GetMapping("/history-device-open-counter-all-of-one-day")
    public CommonResult getDeviceHistoryOpenNumberOfOneDay(@RequestParam(value = "days", defaultValue = "7") int days) {
        return deviceManageService.getDeviceHistoryOpenNumberOfOneDay(days);
    }


    /**
     * 获取仪器过去一段时间内每一天的采集时长信息
     *
     * @param days 历史天数
     * @return 过去每天的采集时长
     */
    @ApiOperation("获取过去一段时间内仪器每一天的采集时长")
    @GetMapping("/history-total-collection-time-of-one-day")
    public CommonResult getDeviceHistoryTotalCollectionTimeOfOneDay(@RequestParam(value = "days", defaultValue = "7")
                                                 @Min(value = 1, message = "查询历史日期不能小于1")  int days) {
        return deviceManageService.getDeviceHistoryTotalCollectionTimeOfOneDay(days);
    }


    /**
     * 获取历史开机的仪器数量
     *
     * @param days 历史天数
     * @return 开机信息
     */
    @ApiOperation(value = "获取过去一段时间内每一天采集场次", notes = "参数为历史天数,如7,15,30")
    @GetMapping("/history-total-collection-counter-of-one-day")
    public CommonResult getDeviceHistoryCollectionNumberOfOneDay(@RequestParam(value = "days", defaultValue = "7") int days) {
        return deviceManageService.getDeviceHistoryCollectionCounterOfOneDay(days);
    }



}
