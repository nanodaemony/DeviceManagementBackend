package com.nano.msc.collection.service;

import com.nano.msc.common.vo.CommonResult;

/**
 * Description: 数据采集器服务
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/23 15:46
 */
public interface DataCollectorService {

    /**
     * 注册数据采集器
     * @return 是否成功
     */
    CommonResult<String> registerDataCollector();


}
