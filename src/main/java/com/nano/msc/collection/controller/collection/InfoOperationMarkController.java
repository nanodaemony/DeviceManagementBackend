package com.nano.msc.collection.controller.collection;

import com.nano.msc.collection.entity.InfoOperationMark;
import com.nano.msc.collection.service.InfoOperationMarkService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 手术标记事件的控制器
 * Usage:
 * 1. 手术标记事件增删改查
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 16:03
 */
@Slf4j
@Api(tags = "InfoOperationMarkController", description = "OperationMark")
@RestController
@RequestMapping("/operation-mark")
public class InfoOperationMarkController {


    @Autowired
    private InfoOperationMarkService operationMarkService;

    /**
     * 新增一个手术标记事件
     *
     * @param operationMark 手术标记事件
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增一个手术标记事件")
    public CommonResult addOperationMark(@Valid @RequestBody InfoOperationMark operationMark) {
        return operationMarkService.addMarkEvent(operationMark);
    }


    /**
     * 更新一个手术标记事件
     *
     * @param operationMark 手术标记事件
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新一个手术标记事件")
    public CommonResult updateOperationMark(@Valid @RequestBody InfoOperationMark operationMark) {
        return operationMarkService.updateMarkEvent(operationMark);
    }


    /**
     * 删除一个手术标记事件
     *
     * @param operationMark 手术标记事件
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除一个手术标记事件")
    public CommonResult deleteOperationMark(@Valid @RequestBody InfoOperationMark operationMark) {
        return operationMarkService.deleteEventByUniqueNumber(operationMark);
    }


}
