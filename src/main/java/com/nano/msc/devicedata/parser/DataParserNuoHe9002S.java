package com.nano.msc.devicedata.parser;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.ethernet.DataNuoHe9002s;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description: 诺和NW9002S数据解析
 * Usage:
 * 1. 解析APP上传的仪器数据并进行持久化
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 0:56
 */
@Component
public class DataParserNuoHe9002S implements DeviceDataParser<DataNuoHe9002s>{

    @Autowired
    private DeviceDataRepository<DataNuoHe9002s, Integer> dataRepository;

    @Override
    public DataNuoHe9002s parseDeviceData(String deviceData) {
        try {
            // 解析并存储数据
            return dataRepository.save(JSON.parseObject(deviceData, DataNuoHe9002s.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
