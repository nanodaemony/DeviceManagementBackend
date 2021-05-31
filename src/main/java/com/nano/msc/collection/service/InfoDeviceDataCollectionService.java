package com.nano.msc.collection.service;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.param.ParamPad;
import com.nano.msc.common.service.BaseService;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Description: 仪器数据采集服务
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:16
 */
public interface InfoDeviceDataCollectionService extends BaseService<InfoDeviceDataCollection, Integer> {

    /**
     * 根据状态获取当前数据采集信息列表(全部状态)
     * @return 采集信息
     */
    CommonResult<Page<InfoDeviceDataCollection>> getDeviceDataCollectionListByStatusAll(int page, int size);


    /**
     * 根据状态获取当前数据采集信息列表(等待状态)
     * @return 采集信息
     */
    CommonResult<Page<InfoDeviceDataCollection>> getDeviceDataCollectionListByStatusWaiting(int page, int size);

    /**
     * 根据状态获取当前数据采集信息列表(采集状态)
     * @return 采集信息
     */
    CommonResult<Page<InfoDeviceDataCollection>> getDeviceDataCollectionListByStatusCollecting(int page, int size);

    /**
     * 根据状态获取当前数据采集信息列表(完成状态)
     * @return 采集信息
     */
    CommonResult<Page<InfoDeviceDataCollection>> getDeviceDataCollectionListByStatusFinish(int page, int size);

    /**
     * 根据状态获取当前数据采集信息列表(丢弃状态)
     * @return 采集信息
     */
    CommonResult<Page<InfoDeviceDataCollection>> getDeviceDataCollectionListByStatusAbandon(int page, int size);


    /**
     * 获取采集使用的仪器信息
     *
     * @param collectionNumber 手术场次号
     * @return 仪器信息
     */
    CommonResult getCollectionUsedDeviceInfo(int collectionNumber);

    /**
     * 获取系统总采集场次数
     * @return 总场次数
     */
    CommonResult<Integer> getSystemTotalDataCollectionNumber();

    /**
     * 通过采集场次号获取仪器数据采集详细信息
     *
     * @param collectionNumber 采集场次号
     * @return 数据采集详细信息
     */
    CommonResult<InfoDeviceDataCollection> getDeviceDataCollectionInfoByCollectionNumber(int collectionNumber);
}
