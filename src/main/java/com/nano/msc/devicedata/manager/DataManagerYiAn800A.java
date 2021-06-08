package com.nano.msc.devicedata.manager;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.ethernet.DataYiAn8700A;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class DataManagerYiAn800A implements DeviceDataManager<DataYiAn8700A> {

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

    @Override
    public Map<String, Object> getDeviceHistoryData(int collectionNumber, String serialNumber) {
        Map<String, Object> dataMap = new HashMap<>();
        List<DataYiAn8700A> dataList = dataRepository.findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
        dataMap.put("PEAK", dataList.stream().map(DataYiAn8700A::getPeak).collect(Collectors.toList()));
        dataMap.put("MEAN", dataList.stream().map(DataYiAn8700A::getPmean).collect(Collectors.toList()));
        dataMap.put("PEEP", dataList.stream().map(DataYiAn8700A::getPeep).collect(Collectors.toList()));
        dataMap.put("MV", dataList.stream().map(DataYiAn8700A::getMv).collect(Collectors.toList()));
        dataMap.put("Vte", dataList.stream().map(DataYiAn8700A::getVte).collect(Collectors.toList()));
        dataMap.put("Freq", dataList.stream().map(DataYiAn8700A::getFreq).collect(Collectors.toList()));
        // dataMap.put("time", dataList.stream().map(DataYiAn8700A::getGmtCreate).collect(Collectors.toList()));
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
