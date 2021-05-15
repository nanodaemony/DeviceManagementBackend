package com.nano.msc.devicedata.parser;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.ethernet.DataBaoLaiTeA8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description: 宝莱特A8数据解析
 * Usage:
 * 1. 解析APP上传的仪器数据并进行持久化
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 0:56
 */
@Component
public class DataParserBaoLaiTeA8 implements DeviceDataParser<DataBaoLaiTeA8>{

    @Autowired
    private DeviceDataRepository<DataBaoLaiTeA8, Integer> dataRepository;

    @Override
    public DataBaoLaiTeA8 parseDeviceData(String deviceData) {
        try {
            // 解析并存储数据
            return dataRepository.save(JSON.parseObject(deviceData, DataBaoLaiTeA8.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
