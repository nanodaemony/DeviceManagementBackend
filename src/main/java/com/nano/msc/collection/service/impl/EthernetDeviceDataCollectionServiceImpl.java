package com.nano.msc.collection.service.impl;

import com.alibaba.fastjson.JSON;
import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.enums.CollectionStatusEnum;
import com.nano.msc.collection.enums.InterfaceTypeEnum;
import com.nano.msc.collection.enums.MedicalDeviceEnum;
import com.nano.msc.collection.param.ParamPad;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.EthernetDeviceDataCollectionService;
import com.nano.msc.collection.service.InfoDeviceUsageEvaluationService;
import com.nano.msc.collection.utils.CollectionNumberCacheUtil;
import com.nano.msc.common.utils.TimestampUtils;
import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.system.log.service.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import cn.hutool.core.bean.BeanUtil;

/**
 * Description:
 * Usage:
 *  @see #saveEthernetMedicalDeviceInfoFromPadAndGrantCollectionNumber(ParamPad) Pad上传医疗仪器信息,并为其分配采集场次号
 *  @see #startEthernetDeviceDataCollectionPad(ParamPad) 控制一个Pad仪器的开始采集
 *  @see #finishEthernetDeviceDataCollectionPad(ParamPad) 控制一个Pad仪器的结束采集
 *  @see #abandonEthernetDeviceDataCollectionPad(ParamPad) 控制一个Pad仪器的放弃采集
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/16 21:39
 */
@Service
public class EthernetDeviceDataCollectionServiceImpl implements EthernetDeviceDataCollectionService {

    @Autowired
    private SystemLogService logger;

    @Autowired
    private InfoDeviceDataCollectionRepository deviceDataCollectionRepository;

    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    @Autowired
    private InfoDeviceUsageEvaluationService usageEvaluationService;

    /**
     * 上传医疗仪器信息(由Pad控制),并分配采集场次号
     */
    @Override
    public CommonResult saveEthernetMedicalDeviceInfoFromPadAndGrantCollectionNumber(ParamPad paramPad) {
        logger.info("获取从Pad上传的医疗仪器信息:" + paramPad.toString());
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
                    newDevice.setInterfaceType(InterfaceTypeEnum.ETHERNET.getCode());
                    // 默认值
                    newDevice.setDeviceDepartment("麻醉科2组");
                    newDevice.setCollectorUniqueId(paramPad.getMac());
                    // 将新的仪器存入数据库中
                    device = medicalDeviceRepository.save(newDevice);
                } else {
                    return CommonResult.failed("获取从Pad上传的医疗仪器信息失败, 仪器号不存在." + newDevice.getDeviceCode());
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
            logger.info("成功分配网口仪器数据采集场次号:" + collection);

            // 进行默认使用评价
            usageEvaluationService.addDefaultDeviceUsageEvaluationInfo(collection.getCollectionNumber(), collection.getDeviceCode(), collection.getSerialNumber(), device.getDeviceDepartment());
            logger.info("成功添加默认使用评价信息." + collection.getCollectionNumber());

            // 返回采集场次号
            return CommonResult.success(collection.getCollectionNumber());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("开始采集失败,上传仪器信息解析失败.:" + e.getCause().toString());
            return CommonResult.failed("开始采集失败,上传仪器信息解析失败.:" + e.getCause().toString());
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
    public CommonResult startEthernetDeviceDataCollectionPad(ParamPad paramPad) {
        if (paramPad == null || paramPad.getData() == null) {
            logger.error("开始Pad采集失败,上传信息无效.");
            return CommonResult.failed("开始Pad采集失败,上传信息无效.");
        }
        try {
            // 获取需要停止的采集号
            InfoDeviceDataCollection collection = deviceDataCollectionRepository.findByCollectionNumber(paramPad.getCollectionNumber());
            if (collection == null) {
                logger.error("当前采集信息不存在: " + paramPad);
                return CommonResult.failed("当前采集信息不存在: " + paramPad);
            }
            if (!collection.getCollectionStatus().equals(CollectionStatusEnum.WAITING.getCode())) {
                logger.error("当前采集不是等待状态,无法开始: " + paramPad);
                return CommonResult.failed("当前采集不是等待状态,无法开始: " + paramPad);
            }
            // 设置开始时间为当前时间
            collection.setCollectionStartTime(TimestampUtils.getCurrentTimeForDataBase());
            collection.setCollectionStatus(CollectionStatusEnum.COLLECTING.getCode());
            // 持久化信息并将CollectionNumber返回给Pad
            collection = deviceDataCollectionRepository.save(collection);
            logger.info("成功开始仪器采集:" + collection);
            // 当前采集号加入缓存中
            CollectionNumberCacheUtil.addNewCollectionNumber(collection.getCollectionNumber());
            return CommonResult.success(collection.getCollectionNumber());

        } catch (Exception e) {
            logger.error("开始采集失败,上传信息解析失败.:" + paramPad);
            return CommonResult.failed("开始采集失败,上传信息解析失败.:" + paramPad);
        }
    }

    /**
     * 结束采集仪器数据(由Pad控制)
     *
     * @param paramPad 采集器构造实体
     * @return 是否开始
     */
    @Override
    public CommonResult finishEthernetDeviceDataCollectionPad(ParamPad paramPad) {

        if (paramPad == null || paramPad.getData() == null) {
            logger.error("结束Pad采集失败,上传信息无效.");
            return CommonResult.failed("结束Pad采集失败,上传信息无效.");
        }
        try {
            // 获取需要停止的采集号
            InfoDeviceDataCollection collection = deviceDataCollectionRepository.findByCollectionNumber(paramPad.getCollectionNumber());
            if (collection == null) {
                logger.error("结束Pad采集失败,采集信息不存在.");
                return CommonResult.failed("采集信息不存在: " + paramPad);
            }
            if (!collection.getCollectionStatus().equals(CollectionStatusEnum.COLLECTING.getCode())) {
                logger.error("当前采集不是正在采集状态,停止失败.");
                return CommonResult.failed("当前采集不是正在采集状态,停止失败." + paramPad);
            }
            // 可以停止采集(改变状态并更新数据等)
            collection.setCollectionStatus(CollectionStatusEnum.FINISHED.getCode());
            collection.setCollectionFinishTime(TimestampUtils.getCurrentTimeForDataBase());

            // 持久化更新采集信息
            deviceDataCollectionRepository.save(collection);
            logger.info("成功结束Pad控制仪器采集:" + collection);
            CollectionNumberCacheUtil.removeCollectionNumber(collection.getCollectionNumber());
            return CommonResult.success(collection.getCollectionNumber());
        } catch (Exception e) {
            logger.error("结束Pad采集失败:" + e.getCause().toString());
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
    public CommonResult abandonEthernetDeviceDataCollectionPad(ParamPad paramPad) {
        if (paramPad == null || paramPad.getData() == null) {
            return CommonResult.failed("放弃Pad采集失败,上传信息无效.");
        }
        try {
            // 获取需要停止的采集号
            InfoDeviceDataCollection collection = deviceDataCollectionRepository.findByCollectionNumber(paramPad.getCollectionNumber());
            if (collection == null) {
                return CommonResult.failed("采集信息不存在: " + paramPad);
            }
            // 可以放弃采集(改变状态并更新数据等)
            collection.setCollectionStatus(CollectionStatusEnum.ABANDON.getCode());
            // 持久化更新采集信息
            deviceDataCollectionRepository.save(collection);
            logger.error("成功放弃数据采集信息:" + paramPad);
            return CommonResult.success(collection.getCollectionNumber());
        } catch (Exception e) {
            logger.error("放弃Pad采集失败:" + e.getCause().toString());
            return CommonResult.failed("放弃Pad采集失败:" + e.getCause().toString());
        }

    }
}
