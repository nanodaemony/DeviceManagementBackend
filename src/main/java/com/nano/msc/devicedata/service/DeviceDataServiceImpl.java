package com.nano.msc.devicedata.service;

import com.alibaba.fastjson.JSON;
import com.nano.msc.GlobalContext;
import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.utils.CollectionNumberCacheUtil;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.enums.MedicalDeviceEnum;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.common.enums.ExceptionEnum;
import com.nano.msc.common.utils.TimestampUtils;
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

    @Autowired
    private InfoDeviceDataCollectionRepository dataCollectionRepository;

    public static Map<Integer, Integer> receiveCounterMap = new HashMap<>();

    /**
     * 解析仪器数据并保存到数据库
     * 同时用WebSocket推送到前端进行实时展示.
     *
     * @param data 仪器数据
     * @return 保存到数据库
     */
    @Override
    public CommonResult<String> parseEthernetDeviceDataAndSaveFromPad(ParamDeviceDataPad data) {

        if (data == null || data.getDeviceCode() == null || data.getDeviceData() == null) {
            return CommonResult.failed(ExceptionEnum.DATA_FORMAT_ERROR.getMessage());
        }
        if (!dataHandlerMap.containsKey(data.getDeviceCode())) {
            return CommonResult.failed("数据解析与存储时仪器号不存在:" + data.toString());
        }

        if (!CollectionNumberCacheUtil.contains(data.getCollectionNumber())) {
            return CommonResult.failed("当前采集号不存在.");
        }

        Object result = dataHandlerMap.get(data.getDeviceCode()).getDataManager().parseDeviceData(data.getDeviceData());
        if (result == null) {
            return CommonResult.failed("数据解析与存储失败:" + data.toString());
        }
        int collectionNumber = data.getCollectionNumber();
        // 获取接收的数据条数
        int cnt = receiveCounterMap.getOrDefault(data.getCollectionNumber(), 0);
        // 说明到了60条数据,此时更新一次接收时间
        if (cnt > 0 && cnt % 60 == 0) {
            InfoDeviceDataCollection collection = dataCollectionRepository.findByCollectionNumber(collectionNumber);
            if (collection != null) {
                // 更新接收仪器数据的时间
                collection.setLastReceiveDeviceDataTime(TimestampUtils.getCurrentTimeForDataBase());
                dataCollectionRepository.save(collection);
            }
        }
        receiveCounterMap.put(collectionNumber, cnt + 1);

        // 仪器数据实时推送到前端
        RealTimeDeviceDataServer
                .sendDeviceRealTimeDataToClient(data.getCollectionNumber(), data.getDeviceCode(), JSON.toJSONString(result));

        return CommonResult.success();
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
        log.info("查询仪器历史数据...");
        if (!GlobalContext.deviceCodeSet.contains(deviceCode)) {
            return CommonResult.failed("仪器号不存在:" + deviceCode);
        }
        InfoMedicalDevice medicalDevice = medicalDeviceRepository.findByDeviceCodeAndSerialNumber(deviceCode, serialNumber);
        if (medicalDevice == null) {
            return CommonResult.failed("仪器信息不存在." + deviceCode + ", " + serialNumber);
        }
        // 默认全部查询
        return CommonResult.success(dataHandlerMap.get(deviceCode).getDataManager().getDeviceHistoryData(collectionNumber, serialNumber));
    }


    /**
     * 容器构造时初始化(这里通过上下文获取仪器解析器的Map信息)
     */
    @PostConstruct
    private void init() {
        this.dataHandlerMap = deviceDataContext.getDataHandlerMap();
    }

}
