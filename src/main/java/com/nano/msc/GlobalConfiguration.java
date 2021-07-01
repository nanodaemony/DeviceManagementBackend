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
    }

    /**
     * 刷新采集器集合
     */
    private void refreshCollectorSet() {
        // 查询系统中有的采集器列表
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
                71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 100, 101, 102, 103};
        for (int c : codes) {
            GlobalContext.deviceCodeSet.add(c);
        }
    }

}
