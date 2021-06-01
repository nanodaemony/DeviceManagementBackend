package com.nano.msc.devicedata.manager;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.ethernet.DataLiBangEliteV8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description: 理邦EliteV8数据管理器
 * Usage:
 * 1. 解析APP上传的仪器数据并进行持久化
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 0:56
 */
@Component
public class DataManagerLiBangEliteV8 implements DeviceDataManager<DataLiBangEliteV8> {

    @Autowired
    private DeviceDataRepository<DataLiBangEliteV8, Integer> dataRepository;

    @Override
    public DataLiBangEliteV8 parseDeviceData(String deviceData) {
        try {
            // 解析并存储数据
            return dataRepository.save(JSON.parseObject(deviceData, DataLiBangEliteV8.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> getDeviceHistoryData(int collectionNumber, String serialNumber) {
        Map<String, Object> dataMap = new HashMap<>();
        List<DataLiBangEliteV8> dataList =  dataRepository.findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
        dataMap.put("HR", dataList.stream().map(DataLiBangEliteV8::getHr).collect(Collectors.toList()));
        dataMap.put("SpO2", dataList.stream().map(DataLiBangEliteV8::getSpo2).collect(Collectors.toList()));
        dataMap.put("RR", dataList.stream().map(DataLiBangEliteV8::getRr).collect(Collectors.toList()));
        dataMap.put("T1", dataList.stream().map(DataLiBangEliteV8::getTemp1).collect(Collectors.toList()));
        dataMap.put("PR", dataList.stream().map(DataLiBangEliteV8::getPr).collect(Collectors.toList()));
        dataMap.put("CVP", dataList.stream().map(DataLiBangEliteV8::getCvpMap).collect(Collectors.toList()));
        dataMap.put("LAP", dataList.stream().map(DataLiBangEliteV8::getLapMap).collect(Collectors.toList()));
        return dataMap;
    }
}
