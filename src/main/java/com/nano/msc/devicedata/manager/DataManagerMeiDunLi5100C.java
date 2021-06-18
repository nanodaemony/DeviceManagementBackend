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
            // 去掉数据中的多于空格
            data = data.replace("    ", " ").replace("   ", " ").replace("  ", " ");
            if (isValid(data)) {
                // 7.2.6.0/1/1  17.06.21  07:43:22  L  83  0  4  0  0  0  40  -4.4616   -5.2667   0  R  0  0  3  0  0  0  40  0.0000    0.0000    0  S1  0  0  11  0  0  0  40  0.0000    0.0000    0  S2  0  0  11  0  0  0  40  0.0000    0.0000    0  AA1825015154-0  A21903125888-0  0  0
                DataMeiDunLi5100C dataMeiDunLi5100C = new DataMeiDunLi5100C();
                dataMeiDunLi5100C.setCollectionNumber(collectionNumber);
                dataMeiDunLi5100C.setSerialNumber(serialNumber);
                String[] nums = data.split(" ");
                dataMeiDunLi5100C.setOxygenSaturationL(DataParseUtil.parseDoubleValue(nums[4]));
                dataMeiDunLi5100C.setEventL(DataParseUtil.parseDoubleValue(nums[5]));
                dataMeiDunLi5100C.setStatusL(DataParseUtil.parseDoubleValue(nums[6]));
                dataMeiDunLi5100C.setBaseL(DataParseUtil.parseDoubleValue(nums[7]));
                dataMeiDunLi5100C.setAucL(DataParseUtil.parseDoubleValue(nums[8]));
                dataMeiDunLi5100C.setUalL(DataParseUtil.parseDoubleValue(nums[9]));
                dataMeiDunLi5100C.setLalL(DataParseUtil.parseDoubleValue(nums[10]));
                dataMeiDunLi5100C.setAL(DataParseUtil.parseDoubleValue(nums[11]));
                dataMeiDunLi5100C.setBL(DataParseUtil.parseDoubleValue(nums[12]));
                dataMeiDunLi5100C.setCL(DataParseUtil.parseDoubleValue(nums[13]));

                dataMeiDunLi5100C.setOxygenSaturationR(DataParseUtil.parseDoubleValue(nums[15]));
                dataMeiDunLi5100C.setEventR(DataParseUtil.parseDoubleValue(nums[16]));
                dataMeiDunLi5100C.setStatusR(DataParseUtil.parseDoubleValue(nums[17]));
                dataMeiDunLi5100C.setBaseR(DataParseUtil.parseDoubleValue(nums[18]));
                dataMeiDunLi5100C.setAucR(DataParseUtil.parseDoubleValue(nums[19]));
                dataMeiDunLi5100C.setUalR(DataParseUtil.parseDoubleValue(nums[20]));
                dataMeiDunLi5100C.setLalR(DataParseUtil.parseDoubleValue(nums[21]));
                dataMeiDunLi5100C.setAR(DataParseUtil.parseDoubleValue(nums[22]));
                dataMeiDunLi5100C.setBR(DataParseUtil.parseDoubleValue(nums[23]));
                dataMeiDunLi5100C.setCR(DataParseUtil.parseDoubleValue(nums[24]));

                dataMeiDunLi5100C.setOxygenSaturationS1(DataParseUtil.parseDoubleValue(nums[26]));
                dataMeiDunLi5100C.setEventS1(DataParseUtil.parseDoubleValue(nums[27]));
                dataMeiDunLi5100C.setStatusS1(DataParseUtil.parseDoubleValue(nums[28]));
                dataMeiDunLi5100C.setBaseS1(DataParseUtil.parseDoubleValue(nums[29]));
                dataMeiDunLi5100C.setAucS1(DataParseUtil.parseDoubleValue(nums[30]));
                dataMeiDunLi5100C.setUalS1(DataParseUtil.parseDoubleValue(nums[31]));
                dataMeiDunLi5100C.setLalS1(DataParseUtil.parseDoubleValue(nums[32]));
                dataMeiDunLi5100C.setAS1(DataParseUtil.parseDoubleValue(nums[33]));
                dataMeiDunLi5100C.setBS1(DataParseUtil.parseDoubleValue(nums[34]));
                dataMeiDunLi5100C.setCS1(DataParseUtil.parseDoubleValue(nums[35]));

                dataMeiDunLi5100C.setOxygenSaturationS2(DataParseUtil.parseDoubleValue(nums[37]));
                dataMeiDunLi5100C.setEventS2(DataParseUtil.parseDoubleValue(nums[38]));
                dataMeiDunLi5100C.setStatusS2(DataParseUtil.parseDoubleValue(nums[39]));
                dataMeiDunLi5100C.setBaseS2(DataParseUtil.parseDoubleValue(nums[40]));
                dataMeiDunLi5100C.setAucS2(DataParseUtil.parseDoubleValue(nums[41]));
                dataMeiDunLi5100C.setUalS2(DataParseUtil.parseDoubleValue(nums[42]));
                dataMeiDunLi5100C.setLalS2(DataParseUtil.parseDoubleValue(nums[43]));
                dataMeiDunLi5100C.setAS2(DataParseUtil.parseDoubleValue(nums[44]));
                dataMeiDunLi5100C.setBS2(DataParseUtil.parseDoubleValue(nums[45]));
                dataMeiDunLi5100C.setCS2(DataParseUtil.parseDoubleValue(nums[46]));

                // 解析并存储数据
                return dataRepository.save(dataMeiDunLi5100C);

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
        dataMap.put("L", dataList.stream().map(DataMeiDunLi5100C::getOxygenSaturationL).collect(Collectors.toList()));
        dataMap.put("R", dataList.stream().map(DataMeiDunLi5100C::getOxygenSaturationR).collect(Collectors.toList()));
        dataMap.put("S1", dataList.stream().map(DataMeiDunLi5100C::getOxygenSaturationS1).collect(Collectors.toList()));
        dataMap.put("S2", dataList.stream().map(DataMeiDunLi5100C::getOxygenSaturationS2).collect(Collectors.toList()));
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
        return data.trim().length() > 10 && data.split(" ").length > 49;
    }

    public static void main(String[] args) {
    }

}
