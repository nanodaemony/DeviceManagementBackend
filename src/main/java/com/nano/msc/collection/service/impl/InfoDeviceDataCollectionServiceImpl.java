package com.nano.msc.collection.service.impl;

import com.alibaba.fastjson.JSON;
import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.collection.enums.CollectionStatusEnum;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoDeviceUsageEvaluationRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.InfoDeviceDataCollectionService;
import com.nano.msc.collection.vo.DeviceDataCollectionDetailInfoVo;
import com.nano.msc.common.service.BaseServiceImpl;
import com.nano.msc.common.utils.TimestampUtils;
import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.devicedata.context.DeviceDataContext;
import com.nano.msc.devicedata.context.DeviceDataHandler;
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

import javax.annotation.PostConstruct;

import cn.hutool.core.bean.BeanUtil;
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
 * @see #getDeviceDataCollectionDetailInfoVo(int) 通过采集场次号获取详细采集信息
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

    @Autowired
    private InfoDeviceUsageEvaluationRepository usageEvaluationRepository;

    @Autowired
    private DeviceDataContext deviceDataContext;

    /**
     * 数据处理器的Map
     */
    private Map<Integer, DeviceDataHandler> dataHandlerMap;

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
     * 通过采集场次号获取某次采集的详细采集信息
     * 注意: 对于正在采集的场次没有评价信息
     *
     * @param collectionNumber 采集场次号
     * @return 信息信息
     */
    @Override
    public CommonResult<String> getDeviceDataCollectionDetailInfoVo(int collectionNumber) {
        // 查询信息
        InfoDeviceDataCollection collection = deviceDataCollectionRepository.findByCollectionNumber(collectionNumber);
        if (collection == null) {
            return CommonResult.failed("采集场次号不存在:" + collectionNumber);
        }
        DeviceDataCollectionDetailInfoVo vo = new DeviceDataCollectionDetailInfoVo();
        // 拷贝属性
        BeanUtil.copyProperties(collection, vo);

        // 查找该次采集的评价记录
        InfoDeviceUsageEvaluation usageEvaluation = usageEvaluationRepository.findByCollectionNumber(collectionNumber);
        BeanUtil.copyProperties(usageEvaluation, vo);

        // 设置采集的数据条数
        int collectionCounter = dataHandlerMap.get(collection.getDeviceCode())
                .getDataManager().getDeviceHistoryData(collectionNumber, collection.getSerialNumber()).size();
        vo.setCollectedDataCounter(collectionCounter);
        return CommonResult.success(JSON.toJSONString(vo));
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


    @Override
    protected JpaRepository<InfoDeviceDataCollection, Integer> initRepository() {
        return deviceDataCollectionRepository;
    }


    /**
     * 容器构造时初始化(这里通过上下文获取仪器解析器的Map信息)
     */
    @PostConstruct
    private void init() {
        this.dataHandlerMap = deviceDataContext.getDataHandlerMap();
    }

}
