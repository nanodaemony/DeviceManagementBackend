package com.nano.msc.collection.utils;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Description: 采集场次号的本地缓存,不管是串口还是网口类设备采集都需要一个采集号
 * 作用:
 *     1. 仪器数据解析时,查询正在进行的采集场次号,如果有则解析,否则忽略.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2020/10/19 20:38
 */
public class CollectionNumberCacheUtil {

    /**
     * 存放正在进行的采集场次号
     */
    public static Set<Integer> collectionNumberSet = new HashSet<>();

    /**
     * 新增采集场次号
     * @param collectionNumber 采集场次号
     */
    public static void addNewCollectionNumber(Integer collectionNumber) {
        collectionNumberSet.add(collectionNumber);
    }

    /**
     * 是否包含某场次号
     * @param collectionNumber 场次号
     * @return 是否包含
     */
    public static boolean contains(Integer collectionNumber) {
        return collectionNumberSet.contains(collectionNumber);
    }

    /**
     * 移除某采集场次号
     * @param collectionNumber 采集场次号
     */
    public static void removeCollectionNumber(Integer collectionNumber) {
        collectionNumberSet.remove(collectionNumber);
    }

    /**
     * 当前正在采集的场次数
     * @return 存储
     */
    public static Integer currentCollectionNumber() {
        return collectionNumberSet.size();
    }
}
