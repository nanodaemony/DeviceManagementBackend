package com.nano.msc.collection.utils;


import com.nano.msc.collection.entity.InfoDeviceDataCollection;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Description: 手术使用仪器的工具类
 *
 * @version: 1.0
 * @author: nano
 * @date: 2020/12/29 18:28
 */
public class InfoDeviceDataCollectionUtil {


//    /**
//     * 获取某个仪器总的采集时长(用于仪器卡片展示)
//     *
//     * @param operationDeviceList 该仪器采集的历史场次信息
//     * @return 总采集时长
//     */
//    public static long getDeviceTotalCollectionTime(List<InfoDeviceDataCollection> operationDeviceList) {
//        long totalOperationTime = 0;
//        for (InfoDeviceDataCollection operationDevice : operationDeviceList) {
//            if (operationDevice.getDataNumber() != null) {
//                totalOperationTime = totalOperationTime + operationDevice.getOperationDurationTime();
//            }
//        }
//        return totalOperationTime;
//    }
//
//    /**
//     * 获取某个仪器平均掉线率(用于仪器卡片展示)
//     *
//     * @param operationDeviceList 该仪器采集的历史场次信息
//     * @return 仪器平均掉线率
//     */
//    public static double getDeviceAverageDropRate(List<InfoDeviceDataCollection> operationDeviceList) {
//
//        // 如果为空则不能当除数,单独返回
//        if(operationDeviceList.size() == 0) {
//            return 0D;
//        }
//        double totalDropRate = 0;
//        for (InfoDeviceDataCollection operationDevice : operationDeviceList) {
//            if (operationDevice.getDataNumber() != null) {
//                totalDropRate = totalDropRate + operationDevice.getCollectionDropRate();
//            }
//        }
//        // 返回平均掉线率
//        return totalDropRate / operationDeviceList.size();
//    }


    /**
     * 计算采集掉线率
     *
     * @param dataList 数据列表
     * @param pauseTime 暂停时间
     * @return 掉线率
     */
    public static Double getDeviceDropRate(List<Object> dataList, Integer pauseTime, Long collectionDurationTime) {
        // 根据仪器的理论间隔时间计算数据个数
        int dataLastTime = dataList.size() * pauseTime / 1000;
        // 计算掉线率
        double dropRate = (double)dataLastTime / (double) collectionDurationTime;
        // 保留4位小数
        DecimalFormat df = new DecimalFormat("#.0000");
        // 格式化返回
        return Double.parseDouble(df.format(dropRate));
    }


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
}
