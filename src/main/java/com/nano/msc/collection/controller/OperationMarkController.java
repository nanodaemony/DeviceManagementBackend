package com.nano.msc.collection.controller;

import com.nano.msc.collection.entity.InfoOperationMark;
import com.nano.msc.collection.entity.MarkEvent;
import com.nano.msc.collection.param.ParamPad;
import com.nano.msc.collection.service.InfoOperationMarkService;
import com.nano.msc.collection.service.MarkEventService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 手术标记事件的控制器
 * Usage:
 * @see #addOperationMark(InfoOperationMark) 新增一个手术中产生的标记事件
 * @see #updateOperationMark(InfoOperationMark) 更新一个手术中产生的标记事件
 * @see #deleteOperationMark(InfoOperationMark) 删除一个手术中产生的标记事件
 *
 * @see #getOftenUseMarkEventList() 获取常用手术标记事件列表
 * @see #searchMarkEventList(ParamPad) 根据关键词搜索匹配的事件列表
 * @see #addCustimizeMarkEvent(MarkEvent) 新增自定义的标记事件
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 16:03
 */
@Slf4j
@Api(tags = "手术标记接口(暂时不用)", description = "OperationMarkController")
@RestController
@RequestMapping("/operation-mark")
public class OperationMarkController {

    /**
     * 自带标记事件的服务
     */
    @Autowired
    private MarkEventService markEventService;

    /**
     * 手术标记事件的服务
     */
    @Autowired
    private InfoOperationMarkService operationMarkService;


    /*********************** 下面是与InfoOperationMark相关的接口,也就是访问手术中产生标记事件的接口 **********************/

    /**
     * 新增一个手术标记事件
     *
     * @param operationMark 手术标记事件
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/operation-mark-add")
    @ApiOperation(value = "新增一个手术中产生的标记事件")
    public CommonResult addOperationMark(@Valid @RequestBody InfoOperationMark operationMark) {
        return operationMarkService.addMarkEvent(operationMark);
    }


    /**
     * 更新一个手术标记事件
     *
     * @param operationMark 手术标记事件
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/operation-mark-update")
    @ApiOperation(value = "更新一个手术中产生的标记事件")
    public CommonResult updateOperationMark(@Valid @RequestBody InfoOperationMark operationMark) {
        return operationMarkService.updateMarkEvent(operationMark);
    }


    /**
     * 删除一个手术标记事件
     *
     * @param operationMark 手术标记事件
     * @return 返回唯一UniqueNumber
     */
    @PostMapping("/operation-mark-delete")
    @ApiOperation(value = "删除一个手术中产生的标记事件")
    public CommonResult deleteOperationMark(@Valid @RequestBody InfoOperationMark operationMark) {
        return operationMarkService.deleteEventByUniqueNumber(operationMark);
    }


    /*********************** 下面是与MarkEvent相关的接口,也就是访问自带标记事件的接口 ********************************/

    /**
     * 获取常用手术标记事件列表
     */
    @GetMapping("/mark-event-get-often-use-list")
    @ApiOperation(value = "获取常用手术标记事件列表")
    public CommonResult<List<MarkEvent>> getOftenUseMarkEventList() {
        return markEventService.getOftenUseMarkEventList();
    }

    /**
     * 根据关键词搜索匹配的事件列表
     */
    @PostMapping("/mark-event-search-match-list-by-keyword")
    @ApiOperation(value = "根据关键词搜索匹配的标记事件列表")
    public CommonResult<List<MarkEvent>> searchMarkEventList(@RequestBody ParamPad paramPad) {
        return markEventService.searchMarkEventList(paramPad);
    }


    /**
     * 新增自定义的标记事件
     */
    @PostMapping("/mark-event-add-customise-mark-event")
    @ApiOperation(value = "新增自定义的标记事件")
    public CommonResult<String> addCustimizeMarkEvent(@RequestBody MarkEvent markEvent) {
        return markEventService.addCustomizeMarkEvent(markEvent);
    }

}
