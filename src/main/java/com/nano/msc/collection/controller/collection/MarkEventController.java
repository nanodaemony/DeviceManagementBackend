package com.nano.msc.collection.controller.collection;

import com.nano.msc.collection.entity.MarkEvent;
import com.nano.msc.collection.param.ParamPad;
import com.nano.msc.collection.service.MarkEventService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 标记事件查询控制器
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 22:56
 */
@Slf4j
@Api(tags = "MarkEventController", description = "MarkEvent")
@RestController
@RequestMapping("/mark-event")
public class MarkEventController {

    @Autowired
    private MarkEventService markEventService;

    /**
     * 获取常用手术标记事件列表
     */
    @GetMapping("/get-often-use-mark-event-list")
    @ApiOperation(value = "获取常用手术标记事件列表")
    public CommonResult<List<MarkEvent>> getOftenUseMarkEventList() {
        return markEventService.getOftenUseMarkEventList();
    }


    /**
     * 根据关键词搜索匹配的事件列表
     */
    @PostMapping("/search-mark-event-list")
    @ApiOperation(value = "根据关键词搜索匹配的标记事件列表")
    public CommonResult<List<MarkEvent>> searchMarkEventList(@RequestBody ParamPad paramPad) {
        return markEventService.searchMarkEventList(paramPad);
    }


    /**
     * 新增自定义的标记事件
     */
    @PostMapping("/add-customise-mark-event")
    @ApiOperation(value = "新增自定义的标记事件")
    public CommonResult<String> searchMarkEventList(@RequestBody MarkEvent markEvent) {
        return markEventService.addCustomizeMarkEvent(markEvent);
    }



}
