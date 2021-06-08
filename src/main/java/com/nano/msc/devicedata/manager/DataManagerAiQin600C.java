package com.nano.msc.devicedata.manager;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.serial.DataAiQin600B;
import com.nano.msc.devicedata.entity.serial.DataAiQin600C;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description: 爱琴EGOS600C数据管理器
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/31 21:57
 */
@Component
public class DataManagerAiQin600C implements DeviceDataManager<DataAiQin600C>{

    @Autowired
    private DeviceDataRepository<DataAiQin600C, Integer> dataRepository;

    /**
     * 解析仪器原始数据并返回解析得到的仪器对象
     * @param deviceData 原始数据
     * @return 解析后的仪器对象
     */
    @Override
    public DataAiQin600C parseDeviceData(String deviceData) {
        try {
            // 解析并存储数据
            return dataRepository.save(JSON.parseObject(deviceData, DataAiQin600C.class));
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
        List<DataAiQin600C> dataList = dataRepository.findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
        dataMap.put("TOI1", dataList.stream().map(DataAiQin600C::getToi1).collect(Collectors.toList()));
        dataMap.put("THI1", dataList.stream().map(DataAiQin600C::getThi1).collect(Collectors.toList()));
        // dataMap.put("time", dataList.stream().map(DataAiQin600C::getGmtCreate).collect(Collectors.toList()));
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
    public int getDataCollectionCounterInOneCollection(int collectionNumber, String serialNumber) {
        return dataRepository.findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber).size();
    }
}
