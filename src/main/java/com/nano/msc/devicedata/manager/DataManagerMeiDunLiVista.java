package com.nano.msc.devicedata.manager;

import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.serial.DataMeiDunLiVista;
import com.nano.msc.devicedata.utils.DataParseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Description: 美敦力EEG-VISTA数据解析
 * Usage:
 * 1. 解析采集器上传的数据并持久化
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 0:56
 */
@Component
public class DataManagerMeiDunLiVista implements DeviceDataManager<DataMeiDunLiVista> {

    @Autowired
    private DeviceDataRepository<DataMeiDunLiVista, Integer> dataRepository;

    /**
     * 解析仪器数据
     * @param deviceData 原始数据 collectionNumber#serialNumber#data
     */
    @Override
    public DataMeiDunLiVista parseDeviceData(String deviceData) {
        try {
            String[] values = deviceData.split("#");
            int collectionNumber = Integer.parseInt(values[0]);
            String serialNumber = values[1];
            String data = values[2];
            if (isValid(data)) {
                // 05/14/2021 10:14:46|      10|      27|Off     |None    |Off     |Off     |No      |     0.0|     8.1|    21b1|    97.7|    67.7|    47.8|    60.3|      11|00000000|     0.0|     0.0|    8000|     0.0|     0.0|     0.0|   100.0|      13|00000000|     0.0|     8.1|    21b1|    97.7|    67.7|    47.8|    60.3|       0|00000000|
                DataMeiDunLiVista dataMeiDunLiVista = new DataMeiDunLiVista();
                dataMeiDunLiVista.setCollectionNumber(collectionNumber);
                dataMeiDunLiVista.setSerialNumber(serialNumber);

                String[] temps = data.split("\\|");
                dataMeiDunLiVista.setDsc(DataParseUtil.parseDoubleValue(temps[1]));
                dataMeiDunLiVista.setPic(DataParseUtil.parseDoubleValue(temps[2]));

                dataMeiDunLiVista.setSr1(DataParseUtil.parseDoubleValue(temps[8]));
                dataMeiDunLiVista.setSef1(DataParseUtil.parseDoubleValue(temps[9]));
                dataMeiDunLiVista.setBisbit1(temps[10]);
                dataMeiDunLiVista.setBis1(DataParseUtil.parseDoubleValue(temps[11]));
                dataMeiDunLiVista.setTotpow1(DataParseUtil.parseDoubleValue(temps[12]));
                dataMeiDunLiVista.setEmglow1(DataParseUtil.parseDoubleValue(temps[13]));
                dataMeiDunLiVista.setSqi1(DataParseUtil.parseDoubleValue(temps[14]));
                dataMeiDunLiVista.setImpednce1(DataParseUtil.parseDoubleValue(temps[15]));
                dataMeiDunLiVista.setArtf21(temps[16]);

                dataMeiDunLiVista.setSr2(DataParseUtil.parseDoubleValue(temps[17]));
                dataMeiDunLiVista.setSef2(DataParseUtil.parseDoubleValue(temps[18]));
                dataMeiDunLiVista.setBisbit2(temps[19]);
                dataMeiDunLiVista.setBis2(DataParseUtil.parseDoubleValue(temps[20]));
                dataMeiDunLiVista.setTotpow2(DataParseUtil.parseDoubleValue(temps[21]));
                dataMeiDunLiVista.setEmglow2(DataParseUtil.parseDoubleValue(temps[22]));
                dataMeiDunLiVista.setSqi2(DataParseUtil.parseDoubleValue(temps[23]));
                dataMeiDunLiVista.setImpednce2(DataParseUtil.parseDoubleValue(temps[24]));
                dataMeiDunLiVista.setArtf22(temps[25]);

                dataMeiDunLiVista.setSr3(DataParseUtil.parseDoubleValue(temps[26]));
                dataMeiDunLiVista.setSef3(DataParseUtil.parseDoubleValue(temps[27]));
                dataMeiDunLiVista.setBisbit3(temps[28]);
                dataMeiDunLiVista.setBis3(DataParseUtil.parseDoubleValue(temps[29]));
                dataMeiDunLiVista.setTotpow3(DataParseUtil.parseDoubleValue(temps[30]));
                dataMeiDunLiVista.setEmglow3(DataParseUtil.parseDoubleValue(temps[31]));
                dataMeiDunLiVista.setSqi3(DataParseUtil.parseDoubleValue(temps[32]));
                dataMeiDunLiVista.setImpednce3(DataParseUtil.parseDoubleValue(temps[33]));
                dataMeiDunLiVista.setArtf23(temps[34]);

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
        // TODO:
        return null;
    }

    /**
     * 判断数据是否合法
     * @param data 数据
     * @return 是否合法
     */
    private boolean isValid(String data) {
        // 数据以|符号结尾
        return data.trim().endsWith("|") && data.split("\\|").length >= 34;
    }

    public static void main(String[] args) {
        String data = "05/14/2021 10:14:46|      10|      27|Off     |None    |Off     |Off     |No      |     0.0|     8.1|    21b1|    97.7|    67.7|    47.8|    60.3|      11|00000000|     0.0|     0.0|    8000|     0.0|     0.0|     0.0|   100.0|      13|00000000|     0.0|     8.1|    21b1|    97.7|    67.7|    47.8|    60.3|       0|00000000|";
        String[] temps = data.split("\\|");
        for (int i = 0; i < temps.length; i++) {
            System.out.println(i + "\t" + temps[i].trim());
        }
    }

}
