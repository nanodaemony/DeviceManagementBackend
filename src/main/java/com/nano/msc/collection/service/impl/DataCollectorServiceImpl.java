package com.nano.msc.collection.service.impl;

import com.nano.msc.collection.entity.DataCollector;
import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.repository.DataCollectorRepository;
import com.nano.msc.collection.service.DataCollectorService;
import com.nano.msc.common.service.BaseServiceImpl;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/23 15:46
 */
@Service
public class DataCollectorServiceImpl extends BaseServiceImpl<DataCollector, Integer> implements DataCollectorService {

    @Autowired
    private DataCollectorRepository dataCollectorRepository;




    /**
     * 初始化仓库
     */
    @Override
    protected JpaRepository<DataCollector, Integer> initRepository() {
        return dataCollectorRepository;
    }

    @Override
    public CommonResult<String> registerDataCollector() {
        return null;
    }
}
