package com.nano.msc.devicedata.manager;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.ethernet.DataBaoLaiTeA8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description: 宝莱特A8数据管理器
 * Usage:
 * 1. 解析APP上传的仪器数据并进行持久化
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 0:56
 */
@Component
public class DataManagerBaoLaiTeA8 implements DeviceDataManager<DataBaoLaiTeA8> {

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

    @Override
    public Map<String, Object> getDeviceHistoryData(int collectionNumber, String serialNumber) {
        Map<String, Object> dataMap = new HashMap<>();
        List<DataBaoLaiTeA8> dataList = dataRepository.findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
        // 这里宝莱特显示的参数其实更多
        dataMap.put("HR", dataList.stream().map(DataBaoLaiTeA8::getHr).collect(Collectors.toList()));
        dataMap.put("SpO2", dataList.stream().map(DataBaoLaiTeA8::getSpo2).collect(Collectors.toList()));
        dataMap.put("PR", dataList.stream().map(DataBaoLaiTeA8::getPr).collect(Collectors.toList()));
        dataMap.put("Temp", dataList.stream().map(DataBaoLaiTeA8::getTemperature1).collect(Collectors.toList()));
        // dataMap.put("time", dataList.stream().map(DataBaoLaiTeA8::getGmtCreate).collect(Collectors.toList()));
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
