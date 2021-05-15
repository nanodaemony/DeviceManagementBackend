package com.nano.msc.devicedata.parser;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.serial.DataAiQin600A;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description: 爱琴600A数据解析
 * Usage:
 * 1. 解析APP上传的仪器数据并进行持久化
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 0:56
 */
@Component
public class DataParserAiQin600A implements DeviceDataParser<DataAiQin600A>{

    @Autowired
    private DeviceDataRepository<DataAiQin600A, Integer> dataRepository;

    @Override
    public DataAiQin600A parseDeviceData(String deviceData) {
        try {
            // 解析并存储数据
            return dataRepository.save(JSON.parseObject(deviceData, DataAiQin600A.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
