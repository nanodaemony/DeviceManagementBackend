package com.nano.msc.devicedata.manager;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.serial.DataAiQin600A;
import com.nano.msc.devicedata.entity.serial.DataAiQin600B;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description:
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/31 21:57
 */
@Component
public class DataManagerAiQin600B implements DeviceDataManager<DataAiQin600B>{

    @Autowired
    private DeviceDataRepository<DataAiQin600B, Integer> dataRepository;

    /**
     * 解析仪器原始数据并返回解析得到的仪器对象
     * @param deviceData 原始数据
     * @return 解析后的仪器对象
     */
    @Override
    public DataAiQin600B parseDeviceData(String deviceData) {
        try {
            // 解析并存储数据
            return dataRepository.save(JSON.parseObject(deviceData, DataAiQin600B.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询历史数据
     * @param collectionNumber 采集场次号
     * @param serialNumber 仪器序列号
     * @return 历史数据
     */
    @Override
    public Map<String, Object> getDeviceHistoryData(int collectionNumber, String serialNumber) {
        Map<String, Object> dataMap = new HashMap<>();
        List<DataAiQin600B> dataList = dataRepository.findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
        dataMap.put("TOI1", dataList.stream().map(DataAiQin600B::getToi1).collect(Collectors.toList()));
        dataMap.put("TOI2", dataList.stream().map(DataAiQin600B::getToi2).collect(Collectors.toList()));
        dataMap.put("THI1", dataList.stream().map(DataAiQin600B::getThi1).collect(Collectors.toList()));
        dataMap.put("THI2", dataList.stream().map(DataAiQin600B::getThi2).collect(Collectors.toList()));
        dataMap.put("time", dataList.stream().map(DataAiQin600B::getGmtCreate).collect(Collectors.toList()));
        return dataMap;
    }
}
