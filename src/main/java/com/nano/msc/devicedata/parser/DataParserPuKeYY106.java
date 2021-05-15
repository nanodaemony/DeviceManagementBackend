package com.nano.msc.devicedata.parser;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.ethernet.DataPuKeYy106;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description: 普可YY106数据解析
 * Usage:
 * 1. 解析APP上传的仪器数据并进行持久化
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 0:56
 */
@Component
public class DataParserPuKeYY106 implements DeviceDataParser<DataPuKeYy106>{

    @Autowired
    private DeviceDataRepository<DataPuKeYy106, Integer> dataRepository;

    @Override
    public DataPuKeYy106 parseDeviceData(String deviceData) {
        try {
            // 解析并存储数据
            return dataRepository.save(JSON.parseObject(deviceData, DataPuKeYy106.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
