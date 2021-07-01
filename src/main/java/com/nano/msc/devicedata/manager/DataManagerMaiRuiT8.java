package com.nano.msc.devicedata.manager;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.ethernet.DataMaiRuiT8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description: 迈瑞T8数据管理器
 * Usage:
 * 1. 解析APP上传的仪器数据并进行持久化
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 0:56
 */
@Component
public class DataManagerMaiRuiT8 implements DeviceDataManager<DataMaiRuiT8> {

    @Autowired
    private DeviceDataRepository<DataMaiRuiT8, Integer> dataRepository;

    @Override
    public DataMaiRuiT8 parseDeviceData(String deviceData) {
        try {
            // 解析并存储数据 JSON
            return dataRepository.save(JSON.parseObject(deviceData, DataMaiRuiT8.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Map<String, Object> getDeviceHistoryData(int collectionNumber, String serialNumber) {
        Map<String, Object> dataMap = new HashMap<>();
        List<DataMaiRuiT8> dataList = dataRepository.findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
        dataMap.put("Resp", dataList.stream().map(DataMaiRuiT8::getRespRespirationRate).collect(Collectors.toList()));
        dataMap.put("ECG", dataList.stream().map(DataMaiRuiT8::getEcgHeartRate).collect(Collectors.toList()));
        dataMap.put("SpO2", dataList.stream().map(DataMaiRuiT8::getSpo2PercentOxygenSaturation).collect(Collectors.toList()));
        dataMap.put("PR", dataList.stream().map(DataMaiRuiT8::getSpo2PulseRate).collect(Collectors.toList()));
        // dataMap.put("time", dataList.stream().map(DataMaiRuiT8::getGmtCreate).collect(Collectors.toList()));
        return dataMap;
    }


    /**
     * 获取某次采集采集了多少条数据
     *
     * @param collectionNumber 采集号
     * @param serialNumber 序列号
     * @return 采集了多少条数据
     */
    @Override
    public int getCollectedDataCounterInOneCollection(int collectionNumber, String serialNumber) {
        return dataRepository.findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber).size();
    }
}
