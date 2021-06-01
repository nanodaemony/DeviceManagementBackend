package com.nano.msc.collection.utils;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Description: 仪器卡片相关工具类
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/1 15:04
 */
public class DeviceCardUtil {

    /**
     * 计算所有采集场次的采集时长之和
     * @param dataCollectionList 采集信息列表
     * @return 时长之和
     */
    public static int getTotalCollectionTime(List<InfoDeviceDataCollection> dataCollectionList) {
        int durationTime = 0;
        // 计算所有场次的时间
        for (InfoDeviceDataCollection operation : dataCollectionList) {
            LocalDateTime startTime = operation.getCollectionStartTime();
            LocalDateTime endTime = operation.getCollectionFinishTime();
            // 持续添加时间
            if(startTime.isBefore(endTime)) {
                durationTime = durationTime + (int)Duration.between(startTime, endTime).getSeconds();
            }
        }
        return durationTime;
    }

}
