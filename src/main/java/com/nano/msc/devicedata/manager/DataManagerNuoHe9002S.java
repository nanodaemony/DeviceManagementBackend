package com.nano.msc.devicedata.manager;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.ethernet.DataNuoHe9002s;
import com.nano.msc.devicedata.parser.DeviceDataParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class DataManagerNuoHe9002S implements DeviceDataManager<DataNuoHe9002s> {

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

    @Override
    public Map<String, Object> getDeviceHistoryData(int collectionNumber, String serialNumber) {
        Map<String, Object> dataMap = new HashMap<>();
        List<DataNuoHe9002s> dataList = dataRepository.findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
        dataMap.put("BS", dataList.stream().map(DataNuoHe9002s::getBs).collect(Collectors.toList()));
        dataMap.put("EMG", dataList.stream().map(DataNuoHe9002s::getEmg).collect(Collectors.toList()));
        dataMap.put("SQI", dataList.stream().map(DataNuoHe9002s::getSqi).collect(Collectors.toList()));
        dataMap.put("CSI", dataList.stream().map(DataNuoHe9002s::getCsi).collect(Collectors.toList()));
        dataMap.put("time", dataList.stream().map(DataNuoHe9002s::getGmtCreate).collect(Collectors.toList()));
        return dataMap;
    }
}
