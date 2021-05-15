package com.nano.msc.collection.controller.collection;

import com.nano.msc.collection.entity.InfoOperation;
import com.nano.msc.collection.service.InfoOperationService;
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
import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/24 15:03
 */
@Slf4j
@Api(tags = "InfoOperationController", description = "Operation")
@RestController
@RequestMapping("/info-operation")
public class InfoOperationController {


    @Autowired
    private InfoOperationService operationService;

    @PostMapping("/add")
    public CommonResult addOperationInfo(@Valid @RequestBody InfoOperation operation) {
        return operationService.save(operation);
    }

    @GetMapping("/list")
    public CommonResult listOperationInfo(@Min(value = 0, message = "页数不能小于1") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @Min(value = 1, message = "数据个数不能小于1") @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return operationService.list(page, size);
    }

}
