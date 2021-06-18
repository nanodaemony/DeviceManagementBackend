package com.nano.msc.collection.controller;

import com.nano.msc.collection.entity.InfoOperation;
import com.nano.msc.collection.entity.SuggestionAndBug;
import com.nano.msc.collection.service.InfoOperationService;
import com.nano.msc.collection.service.SuggestionAndBugService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 建议与Bug接口
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/24 15:03
 */
@Slf4j
@Api(tags = "建议与Bug接口", description = "SuggestionAdndBugController")
@RestController
@RequestMapping("/suggestion-and-bug")
public class SuggestionAdndBugController {


    @Autowired
    private SuggestionAndBugService suggestionAndBugService;


    @ApiOperation(value = "新增一条建议或Bug信息")
    @GetMapping("/add")
    public CommonResult listOperationInfo(@RequestParam(value = "info", defaultValue = "") String info,
                                       @RequestParam(value = "contact", defaultValue = "") String contact) {
        SuggestionAndBug suggestionAndBug = new SuggestionAndBug();
        suggestionAndBug.setInfo(info);
        suggestionAndBug.setContact(contact);
        return suggestionAndBugService.save(suggestionAndBug);
    }

}
