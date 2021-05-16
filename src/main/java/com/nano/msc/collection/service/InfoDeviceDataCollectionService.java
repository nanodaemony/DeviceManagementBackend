package com.nano.msc.collection.service;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.param.ParamPad;
import com.nano.msc.common.service.BaseService;
import com.nano.msc.common.vo.CommonResult;

/**
 * Description: 仪器数据采集服务
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:16
 */
public interface InfoDeviceDataCollectionService extends BaseService<InfoDeviceDataCollection, Integer> {



    /**
     * 获取仪器历史开机信息
     * @return 开机信息
     */
    CommonResult getDeviceHistoryOpenTimes(int historyDays);


    /**
     * 获取手术使用的仪器信息
     *
     * @param collectionNumber 手术场次号
     * @return 仪器信息
     */
    CommonResult getOperationUsedDeviceInfo(int collectionNumber);
}
