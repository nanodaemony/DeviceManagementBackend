package com.nano.msc.serial.schedule;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.enums.CollectionStatusEnum;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.common.utils.TimestampUtils;
import com.nano.msc.serial.SerialDeviceEntity;
import com.nano.msc.serial.SerialStaticInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Description: 串口仪器数据采集相关的定时任务
 *
 *
 * @version: 1.0
 * @author: nano
 * @date: 2020/10/18 16:57
 */
@Configuration
@EnableScheduling
@Slf4j
public class SerialDeviceDataCollectionScheduleTask {

    /**
     * 采集完成时间间隔(ms)
     */
    private static final int COLLECTION_FINISH_TIME_LENGTH = 600 * 1000;

    @Autowired
    private InfoDeviceDataCollectionRepository dataCollectionRepository;


    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    /**
     * 检查采集器心跳是否有定时更新(单位是ms)
     * 1. 到一定时间如果采集器没有心跳,认为采集停止;
     */
    @Scheduled(fixedRate = 20000)
    private void checkSerialCollectionStatusTask() {

        // 查询系统中全部的采集器UniqueId集合
        List<InfoMedicalDevice> medicalDeviceList = medicalDeviceRepository.findAll();
        Set<String> collectorUniqueIdSet = medicalDeviceList.stream()
                .filter(device -> device.getCollectorUniqueId() != null && device.getCollectorUniqueId().length() > 0)
                .map(InfoMedicalDevice::getCollectorUniqueId).collect(Collectors.toSet());

        for (String uniqueId : collectorUniqueIdSet) {
            // 查找这个采集器全部处于采集中的记录
            List<InfoDeviceDataCollection> collectionList = dataCollectionRepository
                    .findByCollectorUniqueIdAndCollectionStatus(uniqueId, CollectionStatusEnum.COLLECTING.getCode());
            // 遍历这些信息
            for (InfoDeviceDataCollection collection : collectionList) {
                // 600000
                if ((System.currentTimeMillis() - collection.getSerialCollectorLastHeartMessageTime() > COLLECTION_FINISH_TIME_LENGTH)
                || (System.currentTimeMillis() - collection.getSerialCollectorLastDeviceDataMessageTime() > COLLECTION_FINISH_TIME_LENGTH)) {
                    log.info("超过10分钟无心跳信息或仪器数据信息,当前采集完成." + uniqueId);
                    collection.setCollectionStatus(CollectionStatusEnum.FINISHED.getCode());
                    collection.setCollectionFinishTime(TimestampUtils.parseTimeStampToLocalDateTime(System.currentTimeMillis() - COLLECTION_FINISH_TIME_LENGTH));
                    // 持久化到服务器
                    dataCollectionRepository.save(collection);

                    // TODO: 这里可以做相关采集的统计信息

                    // 将采集设置为null
                    SerialStaticInfo.collectionMap.remove(uniqueId);
                }
            }
        }
    }



}
