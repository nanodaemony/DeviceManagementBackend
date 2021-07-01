package com.nano.msc.schedule;

import com.nano.msc.GlobalConfiguration;
import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.enums.CollectionStatusEnum;
import com.nano.msc.collection.enums.InterfaceTypeEnum;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.InfoDeviceUsageEvaluationService;
import com.nano.msc.common.utils.TimestampUtils;
import com.nano.msc.system.log.service.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Description: 定时任务
 *
 *
 * @version: 1.0
 * @author: nano
 * @date: 2020/10/18 16:57
 */
@Configuration
@EnableScheduling
@Slf4j
public class ManageScheduleTask {

    /**
     * 仪器采集完成时间间隔(ms)
     */
    private static final int ETHERNET_COLLECTION_FINISH_TIME_LENGTH = 10 * 60;
    private static final int SERIAL_COLLECTION_FINISH_TIME_LENGTH = 10 * 60;

    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    @Autowired
    private InfoDeviceDataCollectionRepository dataCollectionRepository;

    @Autowired
    private InfoDeviceUsageEvaluationService usageEvaluationService;

    @Autowired
    private SystemLogService logger;



    /**
     * 检查网口类仪器数据采集状态(单位是ms)
     */
    @Scheduled(fixedRate = 60 * 1000)
    private void checkEthernetDeviceDataCollectionStatus() {

        log.info("检查网口类仪器采集情况...");
        // 查询全部以太网类型的仪器
        List<InfoMedicalDevice> medicalDeviceList = medicalDeviceRepository.findByInterfaceType(InterfaceTypeEnum.ETHERNET.getCode());
        // 得到全部以太网仪器的DeviceCode
        Set<Integer> deviceCodeSet = medicalDeviceList.stream().map(InfoMedicalDevice::getDeviceCode).collect(Collectors.toSet());
        for (Integer deviceCode : deviceCodeSet) {
            // 查询这个仪器号全部等待采集与正在采集的信息
            List<InfoDeviceDataCollection> collectionList = dataCollectionRepository
                    .findByDeviceCodeAndCollectionStatus(deviceCode, CollectionStatusEnum.COLLECTING.getCode());
            collectionList.addAll(dataCollectionRepository
                    .findByDeviceCodeAndCollectionStatus(deviceCode, CollectionStatusEnum.WAITING.getCode()));
            
            for (InfoDeviceDataCollection collection : collectionList) {
                // 下面进行筛选,最后接收仪器数据时间大于20分钟则认定采集完成
                if (TimestampUtils.getDurationTimeSecond(TimestampUtils.getCurrentTimeForDataBase(), collection.getLastReceiveDeviceDataTime()) > ETHERNET_COLLECTION_FINISH_TIME_LENGTH) {
                    logger.info("网口仪器超过10分钟没有数据信息,当前采集完成." + collection.getCollectionNumber());
                    // 设置上传接收数据时间为结束采集时间
                    collection.setCollectionFinishTime((collection.getLastReceiveDeviceDataTime()));
                    collection.setCollectionStatus(CollectionStatusEnum.FINISHED.getCode());
                    dataCollectionRepository.save(collection);
               }
            }
        }
    }


    /**
     * 检查串口采集器心跳是否有定时更新(单位是ms)
     * 1. 到一定时间如果采集器没有心跳,认为采集停止;
     */
    @Scheduled(fixedRate = 20 * 1000)
    private void checkSerialCollectionStatusTask() {

        log.info("检查串类仪器采集情况...");
        // 查询全部串口类仪器
        List<InfoMedicalDevice> medicalDeviceList = medicalDeviceRepository.findByInterfaceType(InterfaceTypeEnum.SERIAL.getCode());
        // 获取全部串口类仪器的采集器的唯一ID号
        Set<String> collectorUniqueIdSet = medicalDeviceList.stream()
                .filter(device -> device.getCollectorUniqueId() != null && device.getCollectorUniqueId().length() > 0)
                .map(InfoMedicalDevice::getCollectorUniqueId).collect(Collectors.toSet());

        for (String uniqueId : collectorUniqueIdSet) {
            log.info("当前采集器ID:" + uniqueId);
            // 查找这个采集器全部处于采集中的记录
            List<InfoDeviceDataCollection> collectionList = dataCollectionRepository
                    .findByCollectorUniqueIdAndCollectionStatus(uniqueId, CollectionStatusEnum.COLLECTING.getCode());
            // 遍历这些信息
            for (InfoDeviceDataCollection collection : collectionList) {
                log.info("当前采集:" + collection.toString());
                log.info("距离上次接收数据时间:" + TimestampUtils.getDurationTimeSecond(TimestampUtils.getCurrentTimeForDataBase(), collection.getLastReceiveDeviceDataTime()));
                // 600000
                if ((TimestampUtils.getDurationTimeSecond(TimestampUtils.getCurrentTimeForDataBase(), collection.getLastReceiveDeviceDataTime()) > SERIAL_COLLECTION_FINISH_TIME_LENGTH)
                        || (TimestampUtils.getDurationTimeSecond(TimestampUtils.getCurrentTimeForDataBase(), collection.getLastReceiveDeviceDataTime()) > SERIAL_COLLECTION_FINISH_TIME_LENGTH)) {
                    logger.info("超过10分钟无心跳信息或仪器数据信息,当前采集完成." + uniqueId);
                    collection.setCollectionStatus(CollectionStatusEnum.FINISHED.getCode());
                    // 设置完成时间
                    collection.setCollectionFinishTime(collection.getLastReceiveDeviceDataTime());
                    // 持久化到服务器
                    dataCollectionRepository.save(collection);
                }
            }
        }
    }

    @Autowired
    private GlobalConfiguration configuration;

    /**
     * 刷新缓存内容
     */
    @Scheduled(fixedRate = 20000)
    private void refreshCacheContent() {
        configuration.refreshCacheContent();
    }


}
