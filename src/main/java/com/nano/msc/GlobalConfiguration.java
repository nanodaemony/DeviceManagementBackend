package com.nano.msc;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.enums.CollectionStatusEnum;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description: 系统完成初始化后运行的配置类
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/2 17:29
 */
@Component
@Order(3)
public class GlobalConfiguration implements ApplicationRunner {

    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    @Autowired
    private InfoDeviceDataCollectionRepository dataCollectionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 刷新缓存内容
        refreshCacheContent();
    }


    /**
     * 刷新缓存内容
     */
    public void refreshCacheContent() {
        refreshCollectorSet();
        refreshDeviceCodeSet();
        refreshCollectionSet();
    }

    /**
     * 刷新采集器集合
     */
    private void refreshCollectorSet() {
        // 构造采集器合集
        List<InfoMedicalDevice> medicalDeviceList = medicalDeviceRepository.findAll();
        for (InfoMedicalDevice device : medicalDeviceList) {
            GlobalContext.dataCollectorSet.add(device.getCollectorUniqueId());
        }
    }

    /**
     * 刷新仪器号集合
     */
    private void refreshDeviceCodeSet() {
        int[] codes = {30, 31, 32, 33, 34, 35, 36,
                42, 43, 44, 45, 46, 47, 48,
                71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82};
        for (int c : codes) {
            GlobalContext.deviceCodeSet.add(c);
        }
    }

    /**
     * 刷新正在采集的信息列表
     */
    private void refreshCollectionSet() {
        for (String uniqueId : GlobalContext.dataCollectorSet) {
            // 说明是串口采集器
            if (uniqueId.startsWith("DC")) {
                List<InfoDeviceDataCollection> dataCollectionList = dataCollectionRepository
                        .findByCollectorUniqueIdAndCollectionStatus(uniqueId, CollectionStatusEnum.COLLECTING.getCode());
                for (InfoDeviceDataCollection collection : dataCollectionList) {
                    GlobalContext.serialDataCollectionMap.put(uniqueId, collection);
                }
            }
        }
    }
}
