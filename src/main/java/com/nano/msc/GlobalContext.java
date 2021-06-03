package com.nano.msc;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoMedicalDevice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Data;

/**
 * Description: 全局静态资源
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/1 12:06
 */
@Data
public class GlobalContext {

    /**
     * 数据采集器Map表
     */
    public static Set<String> dataCollectorSet = new HashSet<>();

    public static Set<Integer> deviceCodeSet = new HashSet<>();

    public static Map<String, InfoDeviceDataCollection> serialDataCollectionMap = new HashMap<>();

    public static Map<String, InfoMedicalDevice> medicalDeviceMap = new HashMap<>();


    /**
     * 开始串口类数据采集
     *
     * @param uniqueId 唯一ID
     * @param collection 采集信息
     */
    public static void startSerialDeviceDataCollection(String uniqueId, InfoDeviceDataCollection collection) {
        serialDataCollectionMap.put(uniqueId, collection);
    }

    /**
     * 完成串口类数据采集
     * @param uniqueId 采集器ID
     */
    public static void finishSerialDeviceDataCollection(String uniqueId){
        serialDataCollectionMap.remove(uniqueId);
    }

}
