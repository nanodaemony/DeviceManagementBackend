package com.nano.msc.collection.service.impl;

import com.nano.msc.GlobalContext;
import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoMedicalDeviceRepository;
import com.nano.msc.collection.service.DeviceCardService;
import com.nano.msc.collection.utils.DeviceCardUtil;
import com.nano.msc.collection.vo.DeviceCardDetailVo;
import com.nano.msc.collection.vo.DeviceCardTotalVo;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;

/**
 * Description: 仪器卡片相关服务实现类
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/31 16:39
 */
@Service
public class DeviceCardServiceImpl implements DeviceCardService {

    @Autowired
    private InfoMedicalDeviceRepository medicalDeviceRepository;

    @Autowired
    private InfoDeviceDataCollectionRepository dataCollectionRepository;

    /**
     * 获取某型号仪器的仪器卡片展示信息(某仪器型号)
     * @param deviceCode 仪器号
     * @return 仪器信息
     */
    @Override
    public CommonResult getDeviceCardInfoForOneDeviceModel(Integer deviceCode) {
        if (!GlobalContext.deviceCodeSet.contains(deviceCode)) {
            return CommonResult.failed("仪器号不存在:" + deviceCode);
        }
        // 查找这个仪器号下面的全部仪器信息
        List<InfoMedicalDevice> medicalDeviceList = medicalDeviceRepository.findByDeviceCode(deviceCode);
        // 构造返回的信息
        DeviceCardTotalVo deviceCardTotalVo = new DeviceCardTotalVo();
        List<InfoDeviceDataCollection> dataCollectionList = new ArrayList<>();
        // 将全部采集信息加入到结合中
        for (InfoMedicalDevice device : medicalDeviceList) {
            dataCollectionList.addAll(dataCollectionRepository.findByDeviceCodeAndSerialNumber(device.getDeviceCode(), device.getSerialNumber()));
            // 拷贝仪器信息
            BeanUtil.copyProperties(device, deviceCardTotalVo);
        }
        // 设置总采集场次数
        deviceCardTotalVo.setTotalCollectionCounter(dataCollectionList.size());
        // 设置总采集时长之和
        deviceCardTotalVo.setTotalCollectionTime(DeviceCardUtil.getTotalCollectionTime(dataCollectionList));
        // 返回统计结果
        return CommonResult.success(deviceCardTotalVo);
    }

    /**
     * 获取某型号仪器的仪器卡片展示信息
     * @param deviceCode 仪器号
     * @return 仪器信息
     */
    @Override
    public CommonResult getDeviceCardInfoForOneDeviceDetail(Integer deviceCode, String serialNumber) {
        if (!GlobalContext.deviceCodeSet.contains(deviceCode)) {
            return CommonResult.failed("仪器号不存在:" + deviceCode);
        }
        InfoMedicalDevice medicalDevice = medicalDeviceRepository.findByDeviceCodeAndSerialNumber(deviceCode, serialNumber);
        if (medicalDevice == null) {
            return CommonResult.failed("该仪器不存在:" + deviceCode + ", " + serialNumber);
        }
        // 构造返回的信息
        DeviceCardDetailVo deviceCardDetailVo = new DeviceCardDetailVo();
        // 拷贝基本仪器信息
        BeanUtil.copyProperties(medicalDevice, deviceCardDetailVo);

        // 查询该仪器的数据采集信息
        List<InfoDeviceDataCollection> dataCollectionList = dataCollectionRepository.findByDeviceCodeAndSerialNumber(deviceCode, serialNumber);
        // 设置总采集场次数
        deviceCardDetailVo.setTotalCollectionCounter(dataCollectionList.size());
        // 设置总采集时长之和
        deviceCardDetailVo.setTotalCollectionTime(DeviceCardUtil.getTotalCollectionTime(dataCollectionList));
        // 返回统计结果
        return CommonResult.success(deviceCardDetailVo);
    }
}
