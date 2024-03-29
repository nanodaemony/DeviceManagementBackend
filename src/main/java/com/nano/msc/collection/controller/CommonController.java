package com.nano.msc.collection.controller;

import com.nano.msc.common.vo.CommonResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 公共控制器
 * Usage:
 * @see #getServerStatus() 查询服务器信息,如果成功则表明网络良好
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/16 21:08
 */
@Slf4j
@Api(tags = "通用接口", description = "CommonController")
@RestController
@RequestMapping("/common")
public class CommonController {

    @GetMapping("/server-status")
    @ApiOperation(value = "查询服务器状态")
    public CommonResult getServerStatus() {
        return CommonResult.success();
    }


}
