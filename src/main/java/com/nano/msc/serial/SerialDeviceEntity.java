package com.nano.msc.serial;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.enums.CollectionStatusEnum;

import lombok.Data;

/**
 * Description: 串口类仪器的实体
 * Usage:
 * 1. 保存串口仪器的基本信息
 * 2. 保存串口仪器的采集信息
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/15 16:09
 */
@Data
public class SerialDeviceEntity {

    /**
     * 仪器信息
     */
    private InfoMedicalDevice medicalDevice;


    /**
     * 仪器数据采集信息
     */
    private InfoDeviceDataCollection deviceDataCollection;

    /**
     * 判断当前是否正常采集,有效条件: 仪器信息有效且采集信息有效
     */
    public boolean isNormalCollecting() {
        return medicalDevice != null
                && deviceDataCollection != null
                && !deviceDataCollection.getCollectionStatus().equals(CollectionStatusEnum.FINISHED.getCode());
    }
}
