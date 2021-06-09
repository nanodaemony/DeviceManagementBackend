package com.nano.msc.collection.utils;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.common.utils.TimestampUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Description: 仪器追踪评价工具类
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/8 20:57
 */
public class DeviceFollowUpEvaluationUtil {


    /**
     * 获取过去指定天数内开机情况
     *
     * @param collectionList 采集信息列表
     * @return 开机情况的Map
     */
    public static Map<LocalDate, Boolean> getUsedDaysInPastDays(List<InfoDeviceDataCollection> collectionList, int days) {
        Map<LocalDate, Boolean> resMap = new TreeMap<>();
        for (int i = 0; i < days; i++) {
            // 初始化结果Map
            resMap.put(TimestampUtils.getHistoryDayZeroLocalDateTimeBeforeNow(i).toLocalDate(), false);
        }
        for (InfoDeviceDataCollection collection : collectionList) {
            // 采集开始时间
            LocalDateTime time = collection.getCollectionStartTime();
            // 获取该采集对应的0点时间
            LocalDate date = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), 0, 0, 0, 0).toLocalDate();
            for (LocalDate hisDate : resMap.keySet()) {
                if (hisDate.equals(date)) {
                    // 设置为true
                    resMap.put(hisDate, true);
                    break;
                }
            }
        }
        return resMap;
    }


}
