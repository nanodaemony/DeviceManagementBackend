package com.nano.msc.collection.service.impl;

import com.alibaba.fastjson.JSON;
import com.nano.msc.collection.utils.CollectionNumberCacheUtil;
import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.enums.CollectionStatusEnum;
import com.nano.msc.collection.enums.MedicalDeviceEnum;
import com.nano.msc.collection.param.ParamPad;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.InfoDeviceDataCollectionService;
import com.nano.msc.common.service.BaseServiceImpl;
import com.nano.msc.common.utils.TimestampUtils;
import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.system.log.service.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.transaction.Transactional;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Description: 仪器采集流程控制服务实现类
 * Usage:
 * @see #saveMedicalDeviceInfoFromPad(ParamPad) Pad上传医疗仪器信息,并为其分配采集场次号
 * @see #startDeviceDataCollectionPad(ParamPad) 控制一个Pad仪器的开始采集
 * @see #finishDeviceDataCollectionPad(ParamPad) 控制一个Pad仪器的结束采集
 * @see #abandonDeviceDataCollectionPad(ParamPad) 控制一个Pad仪器的放弃采集
 * @see #getDeviceHistoryOpenTimes(int) 获取指定天数内的仪器开机信息
 * @see #getOperationUsedDeviceInfo(int) 根据手术场次号获取使用仪器的信息
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:40
 */
@Slf4j
@Service
public class InfoDeviceDataCollectionServiceImpl extends BaseServiceImpl<InfoDeviceDataCollection, Integer> implements InfoDeviceDataCollectionService {


    @Autowired
    private InfoDeviceDataCollectionRepository deviceDataCollectionRepository;

    @Autowired
    private SystemLogService logService;

    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    /**
     * 上传医疗仪器信息(由Pad控制),并分配采集场次号
     */
    @Override
    public CommonResult saveMedicalDeviceInfoFromPad(ParamPad paramPad) {
        logService.info("获取仪器信息:" + paramPad.toString());
        try {
            // 解析Pad上传的仪器信息
            InfoMedicalDevice newDevice = JSON.parseObject(paramPad.getData(), InfoMedicalDevice.class);
            // 去数据库查询这个仪器是否已经入库
            InfoMedicalDevice device = medicalDeviceRepository
                    .findByDeviceCodeAndSerialNumber(newDevice.getDeviceCode(), newDevice.getSerialNumber());
            // 说明是新仪器(则构造信息并存储)
            if (device == null) {
                MedicalDeviceEnum deviceEnum = MedicalDeviceEnum.matchDeviceCodeEnum(newDevice.getDeviceCode());
                if (deviceEnum != null) {
                    newDevice.setCompanyName(deviceEnum.getCompanyName());
                    newDevice.setDeviceName(deviceEnum.getDeviceName());
                    newDevice.setDeviceType(deviceEnum.getDeviceType());
                    // 将新的仪器存入数据库中
                    device = medicalDeviceRepository.save(newDevice);
                } else {
                    return CommonResult.failed("仪器号不存在." + newDevice.getDeviceCode());
                }
            }
            // 下面说明仪器信息已经存在了并且存放于device对象中,此时构造采集信息对象并初始化
            InfoDeviceDataCollection collection = new InfoDeviceDataCollection();
            // 拷贝属性(DeviceCode属性)
            BeanUtil.copyProperties(device, collection);
            // 初始化仪器信息ID、MAC地址等
            collection.setMedicalDeviceId(device.getId());
            collection.setCollectorUniqueId(paramPad.getMac());

            // 持久化信息并将CollectionNumber返回给Pad
            collection = deviceDataCollectionRepository.save(collection);
            logService.info("成功分配采集序号:" + collection.toString());
            return CommonResult.success(collection.getCollectionNumber());

        } catch (Exception e) {
            e.printStackTrace();
            logService.error("开始采集失败,上传信息解析失败.:" + e.getCause().toString());
            return CommonResult.failed("开始采集失败,上传信息解析失败.:" + e.getCause().toString());
        }
    }

    /**
     * 开始采集仪器数据(由Pad控制)
     *
     * @param paramPad 采集器构造实体
     * @return 是否开始
     */
    @Override
    @Transactional
    public CommonResult startDeviceDataCollectionPad(ParamPad paramPad) {
        if (paramPad == null || paramPad.getData() == null) {
            return CommonResult.failed("开始Pad采集失败,上传信息无效.");
        }
        try {
            // 获取需要停止的采集号
            InfoDeviceDataCollection collection = deviceDataCollectionRepository.findByCollectionNumber(paramPad.getCollectionNumber());
            if (collection == null) {
                return CommonResult.failed("采集信息不存在: " + paramPad.toString());
            }
            if (!collection.getCollectionStatus().equals(CollectionStatusEnum.WAITING.getCode())) {
                return CommonResult.failed("当前采集不是等待状态,无法开始: " + paramPad.toString());
            }
            // 设置开始时间为当前时间
            collection.setCollectionStartTime(TimestampUtils.getCurrentTimeForDataBase());
            collection.setCollectionStatus(CollectionStatusEnum.COLLECTING.getCode());
            // 持久化信息并将CollectionNumber返回给Pad
            collection = deviceDataCollectionRepository.save(collection);
            logService.info("成功开始仪器采集:" + collection.toString());

            // 当前采集号加入缓存中
            CollectionNumberCacheUtil.addNewCollectionNumber(collection.getCollectionNumber());
            return CommonResult.success(collection.getCollectionNumber());

        } catch (Exception e) {
            logService.error("开始采集失败,上传信息解析失败.:" + paramPad.toString());
            return CommonResult.failed("开始采集失败,上传信息解析失败.:" + paramPad.toString());
        }

    }

    /**
     * 结束采集仪器数据(由Pad控制)
     *
     * @param paramPad 采集器构造实体
     * @return 是否开始
     */
    @Override
    public CommonResult finishDeviceDataCollectionPad(ParamPad paramPad) {

        if (paramPad == null || paramPad.getData() == null) {
            return CommonResult.failed("结束Pad采集失败,上传信息无效.");
        }
        try {
            // 获取需要停止的采集号
            InfoDeviceDataCollection collection = deviceDataCollectionRepository.findByCollectionNumber(paramPad.getCollectionNumber());
            if (collection == null) {
                return CommonResult.failed("采集信息不存在: " + paramPad.toString());
            }
            if (!collection.getCollectionStatus().equals(CollectionStatusEnum.COLLECTING.getCode())) {
                return CommonResult.failed("当前采集不是正在采集状态,无法停止: " + paramPad.toString());
            }
            // 可以停止采集(改变状态并更新数据等)
            collection.setCollectionStatus(CollectionStatusEnum.FINISHED.getCode());
            collection.setCollectionFinishTime(TimestampUtils.getCurrentTimeForDataBase());
            // TODO:下面进行数据统计,如掉线率等
            // collection.setOperationDurationTime(TimestampUtils.getDurationTime(collection.getCollectionStartTime(), collection.getCollectionFinishTime()));

            // 持久化更新采集信息
            deviceDataCollectionRepository.save(collection);
            logService.info("成功结束仪器采集:" + collection.toString());
            CollectionNumberCacheUtil.removeCollectionNumber(collection.getCollectionNumber());
            return CommonResult.success(collection.getCollectionNumber());
        } catch (Exception e) {
            logService.error("结束Pad采集失败:" + e.getCause().toString());
            return CommonResult.failed("结束Pad采集失败:" + e.getCause().toString());
        }
    }

    /**
     * 放弃采集仪器数据(由Pad控制)
     *
     * @param paramPad 采集器构造实体
     * @return 是否开始
     */
    @Override
    public CommonResult abandonDeviceDataCollectionPad(ParamPad paramPad) {
        if (paramPad == null || paramPad.getData() == null) {
            return CommonResult.failed("放弃Pad采集失败,上传信息无效.");
        }
        try {
            // 获取需要停止的采集号
            InfoDeviceDataCollection collection = deviceDataCollectionRepository.findByCollectionNumber(paramPad.getCollectionNumber());
            if (collection == null) {
                return CommonResult.failed("采集信息不存在: " + paramPad.toString());
            }
            // 可以放弃采集(改变状态并更新数据等)
            collection.setCollectionStatus(CollectionStatusEnum.ABANDON.getCode());
            // 持久化更新采集信息
            deviceDataCollectionRepository.save(collection);
            // TODO:修改由此受到影响的内容

            return CommonResult.success(collection.getCollectionNumber());
        } catch (Exception e) {
            logService.error("放弃Pad采集失败:" + e.getCause().toString());
            return CommonResult.failed("放弃Pad采集失败:" + e.getCause().toString());
        }

    }




    /**
     * 获取指定天数内的仪器开机信息
     *
     * @return 开机信息
     */
    @Override
    public CommonResult getDeviceHistoryOpenTimes(int historyDays) {
        // 存放历史开机数的Map,key是历史日期,0是今天,1是昨天,value是当天开机的仪器数
        Map<LocalDate, Integer> deviceOpenNumberMap = new TreeMap<>();

        // 获取当天的信息
        List<InfoDeviceDataCollection> operationDevice = deviceDataCollectionRepository.findByGmtCreateAfter(TimestampUtils.getCurrentDayZeroLocalDateTime());
        int todayCount = (int) operationDevice.stream().map(InfoDeviceDataCollection::getMedicalDeviceId).distinct().count();
        deviceOpenNumberMap.put(TimestampUtils.getCurrentDayZeroLocalDateTime().toLocalDate(), todayCount);

        // 获取历史的信息
        for (int day = 0; day < historyDays - 1; day++) {
            // 获取前一天开始与结束的时间戳
            LocalDateTime after = TimestampUtils.getHistoryDayZeroLocalDateTimeBeforeNow(day + 1);
            LocalDateTime before = TimestampUtils.getHistoryDayZeroLocalDateTimeBeforeNow(day);
            // 获取历史一天的手术仪器信息
            List<InfoDeviceDataCollection> operationDevices = deviceDataCollectionRepository.findByGmtCreateAfterAndGmtCreateBefore(after, before);
            // 这里需要去重，因为有的仪器可能一天采集多次，所以只安装仪器信息ID进行统计
            int count = (int) operationDevices.stream().map(InfoDeviceDataCollection::getMedicalDeviceId).distinct().count();
            deviceOpenNumberMap.put(after.toLocalDate(), count);
        }
        return CommonResult.success(deviceOpenNumberMap);
    }

    /**
     * 根据手术场次号获取使用仪器的信息
     *
     * @param collectionNumber 手术场次号
     * @return 仪器信息
     */
    @Override
    public CommonResult getOperationUsedDeviceInfo(int collectionNumber) {
        return null;
    }


    @Override
    protected JpaRepository<InfoDeviceDataCollection, Integer> initRepository() {
        return deviceDataCollectionRepository;
    }
}
