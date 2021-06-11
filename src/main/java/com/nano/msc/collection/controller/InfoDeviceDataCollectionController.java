package com.nano.msc.collection.controller;

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
 * Description: 仪器数据采集控制器
 * Usage:
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 16:52
 */
@Slf4j
@Api(tags = "仪器数据采集接口", description = "DeviceDataCollection")
@RestController
@RequestMapping("/info-device-data-collection")
public class InfoDeviceDataCollectionController {


    @Autowired
    private InfoDeviceDataCollectionService deviceDataCollectionService;

    /**
     * 获取系统中有的总采集场次数
     *
     * @return 手术信息
     */
    @ApiOperation("获取系统总数据采集场次数")
    @GetMapping("/system-total-data-collection-number")
    public CommonResult getSystemTotalDataCollectionNumber() {
        return deviceDataCollectionService.getSystemTotalDataCollectionNumber();
    }

    /**
     * 数据采集信息分页查询：整体按照时间顺序降序排列
     *
     * @param page 页数
     * @param size 个数
     * @return 结果
     */
    @ApiOperation("分页查询数据采集信息(全部状态)")
    @GetMapping("/list-all-status")
    public CommonResult getDeviceDataCollectionListByStatusAll(@Min(value = 0, message = "页数不能小于1") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                             @Min(value = 1, message = "数据个数不能小于1") @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return deviceDataCollectionService.getDeviceDataCollectionListByStatusAll(page, size);
    }

    /**
     * 数据采集信息分页查询：处于等待状态
     *
     * @param page 页数
     * @param size 个数
     * @return 结果
     */
    @ApiOperation("分页查询数据采集信息(Waiting状态)")
    @GetMapping("/list-waiting-status")
    public CommonResult getDeviceDataCollectionListByStatusWaiting(@Min(value = 0, message = "页数不能小于1") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                             @Min(value = 1, message = "数据个数不能小于1") @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return deviceDataCollectionService.getDeviceDataCollectionListByStatusWaiting(page, size);
    }


    /**
     * 数据采集信息分页查询：处于正在采集状态
     *
     * @param page 页数
     * @param size 个数
     * @return 结果
     */
    @ApiOperation("分页查询数据采集信息(Collecting状态)")
    @GetMapping("/list-collecting-status")
    public CommonResult getDeviceDataCollectionListByStatusCollecting(@Min(value = 0, message = "页数不能小于1") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                   @Min(value = 1, message = "数据个数不能小于1") @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return deviceDataCollectionService.getDeviceDataCollectionListByStatusCollecting(page, size);
    }

    /**
     * 数据采集信息分页查询：处于完成采集状态
     *
     * @param page 页数
     * @param size 个数
     * @return 结果
     */
    @ApiOperation("分页查询数据采集信息(Finish状态)")
    @GetMapping("/list-finish-status")
    public CommonResult getDeviceDataCollectionListByStatusFinish(@Min(value = 0, message = "页数不能小于1") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                      @Min(value = 1, message = "数据个数不能小于1") @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return deviceDataCollectionService.getDeviceDataCollectionListByStatusFinish(page, size);
    }

    /**
     * 数据采集信息分页查询：处于放弃采集状态
     *
     * @param page 页数
     * @param size 个数
     * @return 结果
     */
    @ApiOperation("分页查询数据采集信息(Abandon状态)")
    @GetMapping("/list-abandon-status")
    public CommonResult getDeviceDataCollectionListByStatusAbandon(@Min(value = 0, message = "页数不能小于1") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                  @Min(value = 1, message = "数据个数不能小于1") @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return deviceDataCollectionService.getDeviceDataCollectionListByStatusAbandon(page, size);
    }

    /**
     * 通过采集场次号获取仪器数据采集详细信息
     *
     * @param collectionNumber 采集场次号
     * @return 数据采集详细信息
     */
    @ApiOperation("获取某次数据采集详细信息")
    @GetMapping("/detail-info")
    public CommonResult getDeviceDataCollectionInfo(@RequestParam(value = "collectionNumber") @Min(value = 1, message = "查询采集场次号不能小于1")  int collectionNumber) {
        return deviceDataCollectionService.getDeviceDataCollectionDetailInfoByCollectionNumber(collectionNumber);
    }



}
