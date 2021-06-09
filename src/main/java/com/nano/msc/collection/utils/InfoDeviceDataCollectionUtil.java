package com.nano.msc.collection.utils;


import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.common.utils.TimestampUtils;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Description: 手术使用仪器的工具类
 *
 * @version: 1.0
 * @author: nano
 * @date: 2020/12/29 18:28
 */
public class InfoDeviceDataCollectionUtil {


    /**
     * 获取某一天的总采集时间
     *
     * @param collectionList 一天的手术信息列表
     * @return 总采集时间
     */
    public static long getTotalCollectionTimeOfOneDay(List<InfoDeviceDataCollection> collectionList) {
        long durationTime = 0;
        // 计算所有场次的时间
        for (InfoDeviceDataCollection operation : collectionList) {
            LocalDateTime startTime = operation.getCollectionStartTime();
            LocalDateTime endTime = operation.getCollectionFinishTime();
            // 持续添加时间
            if(startTime.isBefore(endTime)) {
                durationTime = durationTime + Duration.between(startTime, endTime).getSeconds();
            }
        }
        return durationTime;
    }


    /**
     * 获取一批采集信息的总采集时长
     *
     * @param collectionList 采集信息列表
     * @return 总采集时长
     */
    public static int getTotalCollectionTime(List<InfoDeviceDataCollection> collectionList) {
        int res = 0;
        for (InfoDeviceDataCollection collection : collectionList) {

            int duration = (int)TimestampUtils.getDurationTime(collection.getCollectionStartTime(), collection.getCollectionFinishTime());
            if (duration > 0) {
                res = res + duration;
            }
        }
        return res;
    }





}
