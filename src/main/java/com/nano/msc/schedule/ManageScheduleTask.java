package com.nano.msc.schedule;

import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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
     * 手术信息
     */
    @Autowired
    private InfoDeviceDataCollectionRepository deviceCollectionRepository;


    /**
     * 固定时间间隔检查一次当前等待中与正在进行的手术数据采集是否超时(单位是ms)
     */
    @Scheduled(fixedRate = 60000)
    private void checkOperationLastTimeout() {
        // 查询当前正在等待或正在进行的手术列表
        // TODO:
    }


}
