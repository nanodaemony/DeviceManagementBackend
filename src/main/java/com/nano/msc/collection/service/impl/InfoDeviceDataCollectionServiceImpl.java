package com.nano.msc.collection.service.impl;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.enums.CollectionStatusEnum;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.InfoDeviceDataCollectionService;
import com.nano.msc.common.service.BaseServiceImpl;
import com.nano.msc.common.utils.TimestampUtils;
import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.system.log.service.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.extern.slf4j.Slf4j;

/**
 * Description: 仪器采集流程控制服务实现类
 * Usage:
 * @see #getDeviceDataCollectionListByStatusAll(int, int) 根据状态获取当前数据采集信息列表(全部状态)
 * @see #getDeviceDataCollectionListByStatusWaiting(int, int) 根据状态获取当前数据采集信息列表(等待状态)
 * @see #getDeviceDataCollectionListByStatusCollecting(int, int) 根据状态获取当前数据采集信息列表(采集状态)
 * @see #getDeviceDataCollectionListByStatusFinish(int, int) 根据状态获取当前数据采集信息列表(完成状态)
 * @see #getDeviceDataCollectionListByStatusAbandon(int, int) 根据状态获取当前数据采集信息列表(丢弃状态)
 * @see #getCollectionUsedDeviceInfo(int) 根据手术场次号获取使用仪器的信息
 * @see #getSystemTotalDataCollectionNumber() 获取系统总采集场次数
 * @see #getDeviceDataCollectionInfoByCollectionNumber(int) 通过采集场次号获取仪器数据采集详细信息
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:40
 */
@Slf4j
@Service
public class InfoDeviceDataCollectionServiceImpl extends BaseServiceImpl<InfoDeviceDataCollection, Integer> implements InfoDeviceDataCollectionService {


    @Autowired
    private InfoDeviceDataCollectionRepository deviceDataCollectionRepository;

    @Autowired
    private SystemLogService logService;

    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    /**
     * 根据状态获取当前数据采集信息列表(全部状态)
     * @return 采集信息
     */
    @Override
    public CommonResult<Page<InfoDeviceDataCollection>> getDeviceDataCollectionListByStatusAll(int page, int size) {
        return CommonResult.success(deviceDataCollectionRepository.findAllByOrderByCollectionNumberDesc(PageRequest.of(page, size)));
    }

    /**
     * 根据状态获取当前数据采集信息列表(等待状态)
     * @return 采集信息
     */
    @Override
    public CommonResult<Page<InfoDeviceDataCollection>> getDeviceDataCollectionListByStatusWaiting(int page, int size) {
        return CommonResult.success(deviceDataCollectionRepository
                        .findByCollectionStatusOrderByCollectionNumberDesc(CollectionStatusEnum.WAITING.getCode(), PageRequest.of(page, size)));
    }

    /**
     * 根据状态获取当前数据采集信息列表(采集状态)
     * @return 采集信息
     */
    @Override
    public CommonResult<Page<InfoDeviceDataCollection>> getDeviceDataCollectionListByStatusCollecting(int page, int size) {
        return CommonResult.success(deviceDataCollectionRepository
                .findByCollectionStatusOrderByCollectionNumberDesc(CollectionStatusEnum.COLLECTING.getCode(), PageRequest.of(page, size)));
    }

    /**
     * 根据状态获取当前数据采集信息列表(完成状态)
     * @return 采集信息
     */
    @Override
    public CommonResult<Page<InfoDeviceDataCollection>> getDeviceDataCollectionListByStatusFinish(int page, int size) {
        return CommonResult.success(deviceDataCollectionRepository
                .findByCollectionStatusOrderByCollectionNumberDesc(CollectionStatusEnum.FINISHED.getCode(), PageRequest.of(page, size)));
    }

    /**
     * 根据状态获取当前数据采集信息列表(丢弃状态)
     * @return 采集信息
     */
    @Override
    public CommonResult<Page<InfoDeviceDataCollection>> getDeviceDataCollectionListByStatusAbandon(int page, int size) {
        return CommonResult.success(deviceDataCollectionRepository
                .findByCollectionStatusOrderByCollectionNumberDesc(CollectionStatusEnum.ABANDON.getCode(), PageRequest.of(page, size)));
    }


    /**
     * 根据手术场次号获取使用仪器的信息
     *
     * @param collectionNumber 手术场次号
     * @return 仪器信息
     */
    @Override
    public CommonResult getCollectionUsedDeviceInfo(int collectionNumber) {
        // TODO:
        return null;
    }


    /**
     * 获取系统总采集场次数
     * @return 总场次数
     */
    @Override
    public CommonResult<Integer> getSystemTotalDataCollectionNumber() {
        return CommonResult.success((int)deviceDataCollectionRepository.count());
    }

    /**
     * 通过采集场次号获取仪器数据采集详细信息
     * @param collectionNumber 采集场次号
     * @return 数据采集详细信息
     */
    @Override
    public CommonResult<InfoDeviceDataCollection> getDeviceDataCollectionInfoByCollectionNumber(int collectionNumber) {
        return CommonResult.success(deviceDataCollectionRepository.findByCollectionNumber(collectionNumber));
    }


    @Override
    protected JpaRepository<InfoDeviceDataCollection, Integer> initRepository() {
        return deviceDataCollectionRepository;
    }
}
