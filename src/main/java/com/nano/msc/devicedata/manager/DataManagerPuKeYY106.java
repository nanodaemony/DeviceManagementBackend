package com.nano.msc.devicedata.manager;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.ethernet.DataPuKeYy106;
import com.nano.msc.devicedata.parser.DeviceDataParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class DataManagerPuKeYY106 implements DeviceDataManager<DataPuKeYy106> {

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

    @Override
    public Map<String, Object> getDeviceHistoryData(int collectionNumber, String serialNumber) {
        Map<String, Object> dataMap = new HashMap<>();
        List<DataPuKeYy106> dataList = dataRepository.findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
        dataMap.put("Ai", dataList.stream().map(DataPuKeYy106::getAi).collect(Collectors.toList()));
        dataMap.put("EMG", dataList.stream().map(DataPuKeYy106::getEmg).collect(Collectors.toList()));
        dataMap.put("SQI", dataList.stream().map(DataPuKeYy106::getSqi).collect(Collectors.toList()));
        dataMap.put("BSR", dataList.stream().map(DataPuKeYy106::getBsr).collect(Collectors.toList()));
        dataMap.put("time", dataList.stream().map(DataPuKeYy106::getGmtCreate).collect(Collectors.toList()));
        return dataMap;
    }
}
