package com.nano.msc.collection.service.impl;

import com.alibaba.fastjson.JSON;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.entity.SuggestionAndBug;
import com.nano.msc.collection.enums.DeviceTypeEnum;
import com.nano.msc.collection.enums.MedicalDeviceEnum;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.repository.SuggestionAndBugRepository;
import com.nano.msc.collection.service.InfoMedicalDeviceService;
import com.nano.msc.collection.service.SuggestionAndBugService;
import com.nano.msc.common.service.BaseServiceImpl;
import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.system.log.service.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Description: 建议与Bug服务实现类
 * Usage:
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:42
 */
@Service
public class SuggestionAndBugImpl extends BaseServiceImpl<SuggestionAndBug, Integer> implements SuggestionAndBugService {

    @Autowired
    private SuggestionAndBugRepository suggestionAndBugRepository;

    @Autowired
    private SystemLogService logger;



    /**
     * 初始化仓库
     */
    @Override
    protected JpaRepository<SuggestionAndBug, Integer> initRepository() {
        return suggestionAndBugRepository;
    }


}
