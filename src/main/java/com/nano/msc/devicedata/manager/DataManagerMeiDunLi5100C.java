package com.nano.msc.devicedata.manager;

import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.serial.DataMeiDunLi5100C;
import com.nano.msc.devicedata.entity.serial.DataMeiDunLiVista;
import com.nano.msc.devicedata.utils.DataParseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description: 美敦力5100C数据管理器
 * Usage:
 * 1. 解析采集器上传的数据并持久化
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 0:56
 */
@Component
public class DataManagerMeiDunLi5100C implements DeviceDataManager<DataMeiDunLi5100C> {

    @Autowired
    private DeviceDataRepository<DataMeiDunLi5100C, Integer> dataRepository;

    /**
     * 解析仪器数据
     * @param deviceData 原始数据 collectionNumber#serialNumber#data
     */
    @Override
    public DataMeiDunLi5100C parseDeviceData(String deviceData) {
        try {
            String[] values = deviceData.split("#");
            int collectionNumber = Integer.parseInt(values[0]);
            String serialNumber = values[1];
            String data = values[2];
            if (isValid(data)) {
                // 05/14/2021 10:14:46|      10|      27|Off     |None    |Off     |Off     |No      |     0.0|     8.1|    21b1|    97.7|    67.7|    47.8|    60.3|      11|00000000|     0.0|     0.0|    8000|     0.0|     0.0|     0.0|   100.0|      13|00000000|     0.0|     8.1|    21b1|    97.7|    67.7|    47.8|    60.3|       0|00000000|
                DataMeiDunLi5100C dataMeiDunLiVista = new DataMeiDunLi5100C();
                dataMeiDunLiVista.setCollectionNumber(collectionNumber);
                dataMeiDunLiVista.setSerialNumber(serialNumber);
                // 存储原始数据
                dataMeiDunLiVista.setOriginData(data);

                // 解析并存储数据
                return dataRepository.save(dataMeiDunLiVista);

            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> getDeviceHistoryData(int collectionNumber, String serialNumber) {
        Map<String, Object> dataMap = new HashMap<>();
        List<DataMeiDunLi5100C> dataList = dataRepository.findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
        // dataMap.put("bis1", dataList.stream().map(DataMeiDunLiVista::getBis1).collect(Collectors.toList()));
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

    /**
     * 判断数据是否合法
     * @param data 数据
     * @return 是否合法
     */
    private boolean isValid(String data) {
        return data.trim().length() > 10;
    }

    public static void main(String[] args) {


    }

}
