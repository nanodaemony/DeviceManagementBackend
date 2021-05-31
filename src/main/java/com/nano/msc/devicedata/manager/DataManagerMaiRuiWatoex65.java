package com.nano.msc.devicedata.manager;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.ethernet.DataMaiRuiWatoex65;
import com.nano.msc.devicedata.parser.DeviceDataParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description: 迈瑞Watoex65数据管理器
 * Usage:
 * 1. 解析APP上传的仪器数据并进行持久化
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 0:56
 */
@Component
public class DataManagerMaiRuiWatoex65 implements DeviceDataManager<DataMaiRuiWatoex65> {

    @Autowired
    private DeviceDataRepository<DataMaiRuiWatoex65, Integer> dataRepository;

    @Override
    public DataMaiRuiWatoex65 parseDeviceData(String deviceData) {
        try {
            // 解析并存储数据
            return dataRepository.save(JSON.parseObject(deviceData, DataMaiRuiWatoex65.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> getDeviceHistoryData(int collectionNumber, String serialNumber) {
        Map<String, Object> dataMap = new HashMap<>();
        List<DataMaiRuiWatoex65> dataList = dataRepository.findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
        dataMap.put("Ppeak", dataList.stream().map(DataMaiRuiWatoex65::getPPeak).collect(Collectors.toList()));
        dataMap.put("Pmean", dataList.stream().map(DataMaiRuiWatoex65::getPMean).collect(Collectors.toList()));
        dataMap.put("PEEP", dataList.stream().map(DataMaiRuiWatoex65::getPeep).collect(Collectors.toList()));
        dataMap.put("MV", dataList.stream().map(DataMaiRuiWatoex65::getMv).collect(Collectors.toList()));
        dataMap.put("I:E", dataList.stream().map(DataMaiRuiWatoex65::getIe).collect(Collectors.toList()));
        dataMap.put("Rate", dataList.stream().map(DataMaiRuiWatoex65::getRate).collect(Collectors.toList()));
        dataMap.put("time", dataList.stream().map(DataMaiRuiWatoex65::getGmtCreate).collect(Collectors.toList()));
        return dataMap;
    }
}
