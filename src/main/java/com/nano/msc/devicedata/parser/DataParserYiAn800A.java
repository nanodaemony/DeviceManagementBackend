package com.nano.msc.devicedata.parser;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.ethernet.DataYiAn8700A;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description: 谊安8700A数据解析
 * Usage:
 * 1. 解析APP上传的仪器数据并进行持久化
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 0:56
 */
@Component
public class DataParserYiAn800A implements DeviceDataParser<DataYiAn8700A>{

    @Autowired
    private DeviceDataRepository<DataYiAn8700A, Integer> dataRepository;

    @Override
    public DataYiAn8700A parseDeviceData(String deviceData) {
        try {
            // 解析并存储数据
            return dataRepository.save(JSON.parseObject(deviceData, DataYiAn8700A.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
