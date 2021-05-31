package com.nano.msc.collection.service.impl;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.enums.CollectionStatusEnum;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.DeviceManageService;
import com.nano.msc.collection.utils.InfoDeviceDataCollectionUtil;
import com.nano.msc.common.utils.TimestampUtils;
import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.system.log.service.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Description: 仪器管理相关接口
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/31 14:53
 */
@Service
public class DeviceManageServiceImpl implements DeviceManageService {

    @Autowired
    private InfoDeviceDataCollectionRepository deviceDataCollectionRepository;

    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    @Autowired
    private SystemLogService logger;

    /**
     * 获取仪器历史开机信息
     * @return 开机信息
     */
    @Override
    public CommonResult<Map<LocalDate, Integer>> getDeviceHistoryOpenNumberOfOneDay(int historyDays) {
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
     * 获取仪器过去一段时间内每一天的采集时长信息
     * @param historyDays 历史天数
     * @return 过去每天的采集时长
     */
    @Override
    public CommonResult getDeviceHistoryTotalCollectionTimeOfOneDay(int historyDays) {
        // 历史采集时长的Map，key日期，value是当天开机的仪器数
        Map<LocalDate, Long> collectionTimeMap = new TreeMap<>();
        // 获取(当天)已经完成的全部手术信息
        List<InfoDeviceDataCollection> operationOfCurrentDay = deviceDataCollectionRepository
                .findByCollectionStatusAndGmtCreateAfter(CollectionStatusEnum.FINISHED.getCode(), TimestampUtils.getCurrentDayZeroLocalDateTime());
        long collectionTimeToday = InfoDeviceDataCollectionUtil.getTotalCollectionTimeOfOneDay(operationOfCurrentDay);
        collectionTimeMap.put(TimestampUtils.getCurrentDayZeroLocalDateTime().toLocalDate(), collectionTimeToday);

        // 依次获取之前的每一天的数据
        for (int day = 0; day < historyDays - 1; day++) {
            // 获取前一天开始与结束的时间戳
            LocalDateTime after = TimestampUtils.getHistoryDayZeroLocalDateTimeBeforeNow(day + 1);
            LocalDateTime before = TimestampUtils.getHistoryDayZeroLocalDateTimeBeforeNow(day);
            // 获取历史一天的手术仪器信息及其所有的采集时长
            List<InfoDeviceDataCollection> operationList = deviceDataCollectionRepository.findByGmtCreateAfterAndGmtCreateBefore(after, before);
            long totalCollectionTime = InfoDeviceDataCollectionUtil.getTotalCollectionTimeOfOneDay(operationList);
            collectionTimeMap.put(after.toLocalDate(), totalCollectionTime);
        }
        return CommonResult.success(collectionTimeMap);
    }

}
