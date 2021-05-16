package com.nano.msc.collection.controller.collection;

import com.nano.msc.collection.param.ParamPad;
import com.nano.msc.collection.service.InfoDeviceDataCollectionService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 仪器数据采集控制器
 * Usage:
 * 1. 服务器是否在线接口
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 16:52
 */
@Deprecated
@Slf4j
@Api(tags = "InfoDeviceCollectionController", description = "DeviceCollection")
@RestController
@RequestMapping("/device-data-collection")
public class InfoDeviceCollectionController {



}