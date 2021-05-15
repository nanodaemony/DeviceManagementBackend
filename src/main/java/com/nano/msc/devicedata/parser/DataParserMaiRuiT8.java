package com.nano.msc.devicedata.parser;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.ethernet.DataMaiRuiT8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description: 迈瑞T8数据解析
 * Usage:
 * 1. 解析APP上传的仪器数据并进行持久化
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 0:56
 */
@Component
public class DataParserMaiRuiT8 implements DeviceDataParser<DataMaiRuiT8>{

    @Autowired
    private DeviceDataRepository<DataMaiRuiT8, Integer> dataRepository;

    @Override
    public DataMaiRuiT8 parseDeviceData(String deviceData) {
        try {
            // 解析并存储数据
            return dataRepository.save(JSON.parseObject(deviceData, DataMaiRuiT8.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
