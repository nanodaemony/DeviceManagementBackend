package com.nano.msc.devicedata.manager;

import com.alibaba.fastjson.JSON;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.serial.DataAiQin600A;
import com.nano.msc.devicedata.entity.serial.DataMeiDunLiVista;
import com.nano.msc.devicedata.utils.DataParseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Description: 爱琴600A数据管理器
 * Usage:
 * 1. 解析APP上传的仪器数据并进行持久化
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 0:56
 */
@Component
@Slf4j
public class DataManagerAiQin600A implements DeviceDataManager<DataAiQin600A> {

    @Autowired
    private DeviceDataRepository<DataAiQin600A, Integer> dataRepository;

    /**
     * 解析仪器原始数据并返回解析得到的仪器对象
     * @param deviceData 原始数据
     * @return 解析后的仪器对象
     */
    @Override
    public DataAiQin600A parseDeviceData(String deviceData) {
        try {
            String[] values = deviceData.split("#");
            int collectionNumber = Integer.parseInt(values[0]);
            String serialNumber = values[1];
            String data = values[2];
            data = data.replace(" ", "").trim();
            data = data.toUpperCase();
            if (isValid(data)) {
                // 05/14/2021 10:14:46|      10|      27|Off     |None    |Off     |Off     |No      |     0.0|     8.1|    21b1|    97.7|    67.7|    47.8|    60.3|      11|00000000|     0.0|     0.0|    8000|     0.0|     0.0|     0.0|   100.0|      13|00000000|     0.0|     8.1|    21b1|    97.7|    67.7|    47.8|    60.3|       0|00000000|
                DataAiQin600A dataAiQin600A = new DataAiQin600A();
                dataAiQin600A.setCollectionNumber(collectionNumber);
                dataAiQin600A.setSerialNumber(serialNumber);
                // 正常仪器数据
                // EF EF EF 65 23 18 00 00 00 0A 31 35 30 32 38 37 00 01 20 00 30 30 11 30 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 42 7B 9F 68 3D DC 2A 30 7F C0 00 00 7F C0 00 00 7F C0 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 32 FE FE FE
                // EF EF EF
                // 65：长度
                // 23：数据类型消息
                // 18 00 00 00：帧号
                // 0A：设备型号：4个通道
                // 31 35 30 32 38 37 00 01 20 00：10字节，设备SN码
                // 30 30 11 30：探头质量 依次表示4个通道的质量
                // 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00：通道1数据
                // 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00：通道2数据
                // 42 7B 9F 68 3D DC 2A 30 7F C0 00 00 7F C0 00 00 7F C0 00 00：通道3数据
                // 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00：通道4数据
                // 2E：校验和
                // FE FE FE

                // 将空格去掉(经过代码后可能受到的是没有空格的数据)
                // EFEFEF
                // 65
                // 23
                // 18000000
                // 0A
                // 31353032383700012000
                // 30301130 (探头质量:依次表示4个通道的质量)
                // 0000000000000000000000000000000000000000
                // 0000000000000000000000000000000000000000
                // 427B9F683DDC2A307FC000007FC000007FC00000
                // 0000000000000000000000000000000000000000
                // 32
                // FEFEFE
                data = data.replace(" ", "").trim();
                // 得到四个通道的数据
                // 0000000000000000000000000000000000000000
                // 0000000000000000000000000000000000000000
                // 427B9F683DDC2A307FC000007FC000007FC00000
                // 0000000000000000000000000000000000000000
                String channelData1 = data.substring(48, 88);
                String channelData2 = data.substring(88, 128);
                String channelData3 = data.substring(128, 168);
                String channelData4 = data.substring(168, 208);
                // 分别解析四个通道的数据
                parseOneChannelData(channelData1, dataAiQin600A, 1);
                parseOneChannelData(channelData2, dataAiQin600A, 2);
                parseOneChannelData(channelData3, dataAiQin600A, 3);
                parseOneChannelData(channelData4, dataAiQin600A, 4);
                // 解析探头质量(4个通道共8字节)
                String probeStatus = data.substring(40, 48);
                dataAiQin600A.setProbeStatus1(probeStatus.substring(0, 2));
                dataAiQin600A.setProbeStatus2(probeStatus.substring(2, 4));
                dataAiQin600A.setProbeStatus3(probeStatus.substring(4, 6));
                dataAiQin600A.setProbeStatus4(probeStatus.substring(6));
                // 解析并存储数据
                return dataRepository.save(dataAiQin600A);

            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析一个通道的数据
     *
     * @param channelData 通道数据
     * @param dataAiQin600A 数据
     * @param channelNumber 通道索引数
     * 解析后的数据
     */
    private static void parseOneChannelData(String channelData, DataAiQin600A dataAiQin600A, int channelNumber) {
        // 说明这个通道没有数据直接返回
        if ("0000000000000000000000000000000000000000".equals(channelData)) {
            return;
        }
        switch (channelNumber) {
            case 1:
                double toi1 = DataParseUtil.getDoubleValueByHexString(channelData.substring(0, 8));
                double thi1 = DataParseUtil.getDoubleValueByHexString(channelData.substring(8, 16));
                double chb1 = DataParseUtil.getDoubleValueByHexString(channelData.substring(16, 24));
                double chbo21 = DataParseUtil.getDoubleValueByHexString(channelData.substring(24, 32));
                double cthb1 = DataParseUtil.getDoubleValueByHexString(channelData.substring(32));
                dataAiQin600A.setToi1(toi1);
                dataAiQin600A.setThi1(thi1);
                dataAiQin600A.setChb1(chb1);
                dataAiQin600A.setChbo21(chbo21);
                dataAiQin600A.setCthb1(cthb1);
                break;
            case 2:
                double toi2 = DataParseUtil.getDoubleValueByHexString(channelData.substring(0, 8));
                double thi2 = DataParseUtil.getDoubleValueByHexString(channelData.substring(8, 16));
                double chb2 = DataParseUtil.getDoubleValueByHexString(channelData.substring(16, 24));
                double chbo22 = DataParseUtil.getDoubleValueByHexString(channelData.substring(24, 32));
                double cthb2 = DataParseUtil.getDoubleValueByHexString(channelData.substring(32));
                dataAiQin600A.setToi2(toi2);
                dataAiQin600A.setThi2(thi2);
                dataAiQin600A.setChb2(chb2);
                dataAiQin600A.setChbo22(chbo22);
                dataAiQin600A.setCthb2(cthb2);
                break;
            case 3:
                double toi3 = DataParseUtil.getDoubleValueByHexString(channelData.substring(0, 8));
                double thi3 = DataParseUtil.getDoubleValueByHexString(channelData.substring(8, 16));
                double chb3 = DataParseUtil.getDoubleValueByHexString(channelData.substring(16, 24));
                double chbo23 = DataParseUtil.getDoubleValueByHexString(channelData.substring(24, 32));
                double cthb3 = DataParseUtil.getDoubleValueByHexString(channelData.substring(32));
                dataAiQin600A.setToi3(toi3);
                dataAiQin600A.setThi3(thi3);
                dataAiQin600A.setChb3(chb3);
                dataAiQin600A.setChbo23(chbo23);
                dataAiQin600A.setCthb3(cthb3);
                break;
            case 4:
                double toi4 = DataParseUtil.getDoubleValueByHexString(channelData.substring(0, 8));
                double thi4 = DataParseUtil.getDoubleValueByHexString(channelData.substring(8, 16));
                double chb4 = DataParseUtil.getDoubleValueByHexString(channelData.substring(16, 24));
                double chbo24 = DataParseUtil.getDoubleValueByHexString(channelData.substring(24, 32));
                double cthb4 = DataParseUtil.getDoubleValueByHexString(channelData.substring(32));
                dataAiQin600A.setToi4(toi4);
                dataAiQin600A.setThi4(thi4);
                dataAiQin600A.setChb4(chb4);
                dataAiQin600A.setChbo24(chbo24);
                dataAiQin600A.setCthb4(cthb4);
                break;
            default:
        }
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
        List<DataAiQin600A> dataList = dataRepository.findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
        dataMap.put("TOI1", dataList.stream().map(DataAiQin600A::getToi1).collect(Collectors.toList()));
        dataMap.put("TOI2", dataList.stream().map(DataAiQin600A::getToi2).collect(Collectors.toList()));
        dataMap.put("TOI3", dataList.stream().map(DataAiQin600A::getToi3).collect(Collectors.toList()));
        dataMap.put("TOI4", dataList.stream().map(DataAiQin600A::getToi4).collect(Collectors.toList()));
        dataMap.put("THI1", dataList.stream().map(DataAiQin600A::getThi1).collect(Collectors.toList()));
        dataMap.put("THI2", dataList.stream().map(DataAiQin600A::getThi2).collect(Collectors.toList()));
        dataMap.put("THI3", dataList.stream().map(DataAiQin600A::getThi3).collect(Collectors.toList()));
        dataMap.put("THI4", dataList.stream().map(DataAiQin600A::getThi4).collect(Collectors.toList()));
        return dataMap;
    }

    /**
     * 判断数据是否合法
     * @param data 数据
     * @return 是否合法
     */
    private boolean isValid(String data) {
        // 数据以|符号结尾
        return data.startsWith("EFEF") || data.startsWith("efef");
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
