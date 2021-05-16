package com.nano.msc.collection.service.impl;

import com.alibaba.fastjson.JSON;
import com.nano.msc.collection.utils.CollectionNumberCacheUtil;
import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.enums.CollectionStatusEnum;
import com.nano.msc.collection.enums.MedicalDeviceEnum;
import com.nano.msc.collection.param.ParamPad;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.InfoDeviceDataCollectionService;
import com.nano.msc.common.service.BaseServiceImpl;
import com.nano.msc.common.utils.TimestampUtils;
import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.system.log.service.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.transaction.Transactional;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 仪器采集流程控制服务实现类
 * Usage:

 * @see #getDeviceHistoryOpenTimes(int) 获取指定天数内的仪器开机信息
 * @see #getOperationUsedDeviceInfo(int) 根据手术场次号获取使用仪器的信息
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
     * 获取指定天数内的仪器开机信息
     *
     * @return 开机信息
     */
    @Override
    public CommonResult getDeviceHistoryOpenTimes(int historyDays) {
        // 存放历史开机数的Map,key是历史日期,0是今天,1是昨天,value是当天开机的仪器数
        Map<LocalDate, Integer> deviceOpenNumberMap = new TreeMap<>();

        // 获取当天的信息
        List<InfoDeviceDataCollection> operationDevice = deviceDataCollectionRepository.findByGmtCreateAfter(TimestampUtils.getCurrentDayZeroLocalDateTime());
        int todayCount = (int) operationDevice.stream().map(InfoDeviceDataCollection::getMedicalDeviceId).distinct().count();
        deviceOpenNumberMap.put(TimestampUtils.getCurrentDayZeroLocalDateTime().toLocalDate(), todayCount);

        // 获取历史的信息
        for (int day = 0; day < historyDays - 1; day++) {
            // 获取前一天开始与结束的时间戳
            LocalDateTime after = TimestampUtils.getHistoryDayZeroLocalDateTimeBeforeNow(day + 1);
            LocalDateTime before = TimestampUtils.getHistoryDayZeroLocalDateTimeBeforeNow(day);
            // 获取历史一天的手术仪器信息
            List<InfoDeviceDataCollection> operationDevices = deviceDataCollectionRepository.findByGmtCreateAfterAndGmtCreateBefore(after, before);
            // 这里需要去重，因为有的仪器可能一天采集多次，所以只安装仪器信息ID进行统计
            int count = (int) operationDevices.stream().map(InfoDeviceDataCollection::getMedicalDeviceId).distinct().count();
            deviceOpenNumberMap.put(after.toLocalDate(), count);
        }
        return CommonResult.success(deviceOpenNumberMap);
    }

    /**
     * 根据手术场次号获取使用仪器的信息
     *
     * @param collectionNumber 手术场次号
     * @return 仪器信息
     */
    @Override
    public CommonResult getOperationUsedDeviceInfo(int collectionNumber) {
        return null;
    }


    @Override
    protected JpaRepository<InfoDeviceDataCollection, Integer> initRepository() {
        return deviceDataCollectionRepository;
    }
}
