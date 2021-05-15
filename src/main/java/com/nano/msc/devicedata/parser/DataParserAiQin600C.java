package com.nano.msc.devicedata.parser;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.serial.DataAiQin600C;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description: 爱琴600C数据解析
 * Usage:
 * 1. 解析APP上传的仪器数据并进行持久化
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 0:56
 */
@Component
public class DataParserAiQin600C implements DeviceDataParser<DataAiQin600C>{

    @Autowired
    private DeviceDataRepository<DataAiQin600C, Integer> dataRepository;

    @Override
    public DataAiQin600C parseDeviceData(String deviceData) {
        try {
            // 解析并存储数据
            return dataRepository.save(JSON.parseObject(deviceData, DataAiQin600C.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
