package com.nano.msc.devicedata.service;

import com.alibaba.fastjson.JSON;
import com.nano.msc.collection.utils.CollectionNumberCacheUtil;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.enums.MedicalDeviceEnum;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.common.enums.ExceptionEnum;
import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.devicedata.context.DeviceDataContext;
import com.nano.msc.devicedata.context.DeviceDataHandler;
import com.nano.msc.devicedata.entity.serial.DataAiQin600A;
import com.nano.msc.devicedata.entity.serial.DataAiQin600B;
import com.nano.msc.devicedata.entity.serial.DataAiQin600C;
import com.nano.msc.devicedata.entity.ethernet.DataBaoLaiTeA8;
import com.nano.msc.devicedata.entity.ethernet.DataLiBangEliteV8;
import com.nano.msc.devicedata.entity.ethernet.DataMaiRuiT8;
import com.nano.msc.devicedata.entity.ethernet.DataMaiRuiWatoex65;
import com.nano.msc.devicedata.entity.ethernet.DataNuoHe9002s;
import com.nano.msc.devicedata.entity.ethernet.DataPuKeYy106;
import com.nano.msc.devicedata.entity.ethernet.DataYiAn8700A;
import com.nano.msc.devicedata.param.ParamDeviceDataPad;
import com.nano.msc.websocket.RealTimeDeviceDataServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

/**
 * Description: 仪器数据服务实现类
 * Usage:
 * 1. 仪器数据增删改查
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 23:11
 */
@Slf4j
@Service
public class DeviceDataServiceImpl implements DeviceDataService {

    @Autowired
    private DeviceDataContext deviceDataContext;

    /**
     * 数据处理器的Map
     */
    private Map<Integer, DeviceDataHandler> dataHandlerMap;


    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;


    /**
     * 解析仪器数据并保存到数据库
     * 同时用WebSocket推送到前端进行实时展示.
     *
     * @param data 仪器数据
     * @return 保存到数据库
     */
    @Override
    public CommonResult<String> parseDataAndSaveFromPad(ParamDeviceDataPad data) {

        if (data == null || data.getDeviceCode() == null || data.getDeviceData() == null) {
            return CommonResult.failed(ExceptionEnum.DATA_FORMAT_ERROR.getMessage());
        }
        if (!dataHandlerMap.containsKey(data.getDeviceCode())) {
            return CommonResult.failed("数据解析与存储时仪器号不存在:" + data.toString());
        }

        if (!CollectionNumberCacheUtil.contains(data.getCollectionNumber())) {
            return CommonResult.failed("当前采集号不存在.");
        }

        Object result = dataHandlerMap.get(data.getDeviceCode()).getDataParser().parseDeviceData(data.getDeviceData());
        if (result == null) {
            return CommonResult.failed("数据解析与存储失败:" + data.toString());
        }

        // 仪器数据实时推送到前端
        RealTimeDeviceDataServer
                .sendDeviceRealTimeDataToClient(data.getCollectionNumber(), data.getDeviceCode(), JSON.toJSONString(result));

        return CommonResult.success();
    }

    /**
     * 解析仪器数据并保存(处理来自串口设备的数据)
     * 这里根据一定的协议进行数据解析之后再解析存储.
     *
     * @param data 仪器数据
     * @return 是否成功
     */
    @Override
    public CommonResult<String> parseDataAndSaveFromSerial(String data) {
        return null;
    }

    /**
     * 获取仪器历史数据
     *
     * @param collectionNumber 采集场次号
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @param page 页数
     * @param size 大小
     * @return 数据列表
     */
    @Override
    public CommonResult getHistoryDeviceData(int collectionNumber, int deviceCode, String serialNumber, Integer page, Integer size) {

        InfoMedicalDevice medicalDevice = medicalDeviceRepository.findByDeviceCodeAndSerialNumber(deviceCode, serialNumber);
        if (medicalDevice == null) {
            return CommonResult.failed("仪器信息不存在." + deviceCode + ", " + serialNumber);
        }
        // 返回数据的Map对象
        Map<String, Object> dataMap = new HashMap<>();
        // 获取仪器历史数据 分页查询(默认1000条数据)
        if (deviceCode == MedicalDeviceEnum.NUO_HE_NW9002S.getDeviceCode()) {
            List<DataNuoHe9002s> dataList = dataHandlerMap.get(deviceCode).getDataRepository().findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
            dataMap.put("BS", dataList.stream().map(DataNuoHe9002s::getBs).collect(Collectors.toList()));
            dataMap.put("EMG", dataList.stream().map(DataNuoHe9002s::getEmg).collect(Collectors.toList()));
            dataMap.put("SQI", dataList.stream().map(DataNuoHe9002s::getSqi).collect(Collectors.toList()));
            dataMap.put("CSI", dataList.stream().map(DataNuoHe9002s::getCsi).collect(Collectors.toList()));
            dataMap.put("time", dataList.stream().map(DataNuoHe9002s::getGmtCreate).collect(Collectors.toList()));
            return CommonResult.success(dataMap);
        } else if (deviceCode == MedicalDeviceEnum.PU_KE_YY106.getDeviceCode()) {
            List<DataPuKeYy106> dataList = dataHandlerMap.get(deviceCode).getDataRepository().findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
            dataMap.put("Ai", dataList.stream().map(DataPuKeYy106::getAi).collect(Collectors.toList()));
            dataMap.put("EMG", dataList.stream().map(DataPuKeYy106::getEmg).collect(Collectors.toList()));
            dataMap.put("SQI", dataList.stream().map(DataPuKeYy106::getSqi).collect(Collectors.toList()));
            dataMap.put("BSR", dataList.stream().map(DataPuKeYy106::getBsr).collect(Collectors.toList()));
            dataMap.put("time", dataList.stream().map(DataPuKeYy106::getGmtCreate).collect(Collectors.toList()));
            return CommonResult.success(dataMap);
        } else if (deviceCode == MedicalDeviceEnum.BAO_LAI_TE_A8.getDeviceCode()) {
            List<DataBaoLaiTeA8> dataList = dataHandlerMap.get(deviceCode).getDataRepository().findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
            // 这里宝莱特显示的参数其实更多
            dataMap.put("HR", dataList.stream().map(DataBaoLaiTeA8::getHr).collect(Collectors.toList()));
            dataMap.put("SpO2", dataList.stream().map(DataBaoLaiTeA8::getSpo2).collect(Collectors.toList()));
            dataMap.put("PR", dataList.stream().map(DataBaoLaiTeA8::getPr).collect(Collectors.toList()));
            dataMap.put("Temp", dataList.stream().map(DataBaoLaiTeA8::getTemperature1).collect(Collectors.toList()));
            dataMap.put("time", dataList.stream().map(DataBaoLaiTeA8::getGmtCreate).collect(Collectors.toList()));
            return CommonResult.success(dataMap);
        } else if (deviceCode == MedicalDeviceEnum.YI_AN_8700A.getDeviceCode()) {
            List<DataYiAn8700A> dataList = dataHandlerMap.get(deviceCode).getDataRepository().findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
            dataMap.put("PEAK", dataList.stream().map(DataYiAn8700A::getPeak).collect(Collectors.toList()));
            dataMap.put("MEAN", dataList.stream().map(DataYiAn8700A::getPmean).collect(Collectors.toList()));
            dataMap.put("PEEP", dataList.stream().map(DataYiAn8700A::getPeep).collect(Collectors.toList()));
            dataMap.put("MV", dataList.stream().map(DataYiAn8700A::getMv).collect(Collectors.toList()));
            dataMap.put("Vte", dataList.stream().map(DataYiAn8700A::getVte).collect(Collectors.toList()));
            dataMap.put("Freq", dataList.stream().map(DataYiAn8700A::getFreq).collect(Collectors.toList()));
            dataMap.put("time", dataList.stream().map(DataYiAn8700A::getGmtCreate).collect(Collectors.toList()));
            return CommonResult.success(dataMap);
        } else if (deviceCode == MedicalDeviceEnum.MAI_RUI_T8.getDeviceCode()) {
            List<DataMaiRuiT8> dataList = dataHandlerMap.get(deviceCode).getDataRepository().findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
            dataMap.put("Resp", dataList.stream().map(DataMaiRuiT8::getRespRespirationRate).collect(Collectors.toList()));
            dataMap.put("ECG", dataList.stream().map(DataMaiRuiT8::getEcgHeartRate).collect(Collectors.toList()));
            dataMap.put("SpO2", dataList.stream().map(DataMaiRuiT8::getSpo2PercentOxygenSaturation).collect(Collectors.toList()));
            dataMap.put("PR", dataList.stream().map(DataMaiRuiT8::getSpo2PulseRate).collect(Collectors.toList()));
            dataMap.put("time", dataList.stream().map(DataMaiRuiT8::getGmtCreate).collect(Collectors.toList()));
            return CommonResult.success(dataMap);
        } else if (deviceCode == MedicalDeviceEnum.MAI_RUI_WATOEX_65.getDeviceCode()) {
            List<DataMaiRuiWatoex65> dataList = dataHandlerMap.get(deviceCode).getDataRepository().findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
            dataMap.put("Ppeak", dataList.stream().map(DataMaiRuiWatoex65::getPPeak).collect(Collectors.toList()));
            dataMap.put("Pmean", dataList.stream().map(DataMaiRuiWatoex65::getPMean).collect(Collectors.toList()));
            dataMap.put("PEEP", dataList.stream().map(DataMaiRuiWatoex65::getPeep).collect(Collectors.toList()));
            dataMap.put("MV", dataList.stream().map(DataMaiRuiWatoex65::getMv).collect(Collectors.toList()));
            dataMap.put("I:E", dataList.stream().map(DataMaiRuiWatoex65::getIe).collect(Collectors.toList()));
            dataMap.put("Rate", dataList.stream().map(DataMaiRuiWatoex65::getRate).collect(Collectors.toList()));
            dataMap.put("time", dataList.stream().map(DataMaiRuiWatoex65::getGmtCreate).collect(Collectors.toList()));
            return CommonResult.success(dataMap);
        } else if (deviceCode == MedicalDeviceEnum.LI_BANG_ELITE_V8.getDeviceCode()) {
            List<DataLiBangEliteV8> dataList =  dataHandlerMap.get(deviceCode).getDataRepository().findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
            dataMap.put("HR", dataList.stream().map(DataLiBangEliteV8::getHr).collect(Collectors.toList()));
            dataMap.put("SpO2", dataList.stream().map(DataLiBangEliteV8::getSpo2).collect(Collectors.toList()));
            dataMap.put("RR", dataList.stream().map(DataLiBangEliteV8::getRr).collect(Collectors.toList()));
            dataMap.put("T1", dataList.stream().map(DataLiBangEliteV8::getTemp1).collect(Collectors.toList()));
            dataMap.put("PR", dataList.stream().map(DataLiBangEliteV8::getPr).collect(Collectors.toList()));
            dataMap.put("CVP", dataList.stream().map(DataLiBangEliteV8::getCvpMap).collect(Collectors.toList()));
            dataMap.put("LAP", dataList.stream().map(DataLiBangEliteV8::getLapMap).collect(Collectors.toList()));
            return CommonResult.success(dataMap);
        } else if (deviceCode == MedicalDeviceEnum.AI_QIN_EGOS600A.getDeviceCode()) {
            List<DataAiQin600A> dataList = dataHandlerMap.get(deviceCode).getDataRepository().findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
            dataMap.put("TOI1", dataList.stream().map(DataAiQin600A::getToi1).collect(Collectors.toList()));
            dataMap.put("TOI2", dataList.stream().map(DataAiQin600A::getToi2).collect(Collectors.toList()));
            dataMap.put("TOI3", dataList.stream().map(DataAiQin600A::getToi3).collect(Collectors.toList()));
            dataMap.put("TOI4", dataList.stream().map(DataAiQin600A::getToi4).collect(Collectors.toList()));
            dataMap.put("THI1", dataList.stream().map(DataAiQin600A::getThi1).collect(Collectors.toList()));
            dataMap.put("THI2", dataList.stream().map(DataAiQin600A::getThi2).collect(Collectors.toList()));
            dataMap.put("THI3", dataList.stream().map(DataAiQin600A::getThi3).collect(Collectors.toList()));
            dataMap.put("THI4", dataList.stream().map(DataAiQin600A::getThi4).collect(Collectors.toList()));
            dataMap.put("time", dataList.stream().map(DataAiQin600A::getGmtCreate).collect(Collectors.toList()));
            return CommonResult.success(dataMap);
        } else if (deviceCode == MedicalDeviceEnum.AI_QIN_EGOS600B.getDeviceCode()) {
            List<DataAiQin600B> dataList = dataHandlerMap.get(deviceCode).getDataRepository().findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
            dataMap.put("TOI1", dataList.stream().map(DataAiQin600B::getToi1).collect(Collectors.toList()));
            dataMap.put("TOI2", dataList.stream().map(DataAiQin600B::getToi2).collect(Collectors.toList()));
            dataMap.put("THI1", dataList.stream().map(DataAiQin600B::getThi1).collect(Collectors.toList()));
            dataMap.put("THI2", dataList.stream().map(DataAiQin600B::getThi2).collect(Collectors.toList()));
            dataMap.put("time", dataList.stream().map(DataAiQin600B::getGmtCreate).collect(Collectors.toList()));
            return CommonResult.success(dataMap);
        } else if (deviceCode == MedicalDeviceEnum.AI_QIN_EGOS600C.getDeviceCode()) {
            List<DataAiQin600C> dataList = dataHandlerMap.get(deviceCode).getDataRepository().findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
            dataMap.put("TOI1", dataList.stream().map(DataAiQin600C::getToi1).collect(Collectors.toList()));
            dataMap.put("THI1", dataList.stream().map(DataAiQin600C::getThi1).collect(Collectors.toList()));
            dataMap.put("time", dataList.stream().map(DataAiQin600C::getGmtCreate).collect(Collectors.toList()));
            return CommonResult.success(dataMap);
        }

        return CommonResult.failed("无效的deviceCode:" + deviceCode);
    }


    /**
     * 容器构造时初始化(这里通过上下文获取仪器解析器的Map信息)
     */
    @PostConstruct
    private void init() {
        this.dataHandlerMap = deviceDataContext.getDataHandlerMap();
    }

}
