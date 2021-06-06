package com.nano.msc.system;

import com.alibaba.fastjson.JSON;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/6 17:13
 */
@Data
@NoArgsConstructor
public class InfoDeviceDataCollection {

    private static final long serialVersionUID = -7456701585085979233L;

    /**
     * 采集顺序号
     */
    private Integer collectionNumber;

    /**
     * 仪器号
     */
    private Integer deviceCode;

    /**
     * 设备序列号,不一定唯一,初始化为NULL
     */
    private String serialNumber;

    /**
     * 仪器信息的ID号 外键仪器信息表的主键ID号
     */
    private Integer medicalDeviceId;

    /**
     * 仪器信息的ID号 外键仪器信息表的主键ID号
     */
    private String collectorUniqueId;

    /**
     * 采集状态,对应枚举值(初始化为未开始)
     */
    private Integer collectionStatus;

    /**
     * 采集开始时间(默认为当前时间)
     */
    private LocalDateTime collectionStartTime;

    /**
     * 采集结束时间(默认为当前时间)
     */
    private LocalDateTime collectionFinishTime;

    public static void main(String[] args) {

        String data = "[{\"collectionFinishTime\":1622729016000,\"collectionNumber\":2,\"collectionStartTime\":1622728908000,\"collectionStatus\":2,\"collectorUniqueId\":\"DC-75-0001\",\"deviceCode\":75,\"gmtCreate\":1622728908000,\"gmtModified\":1622729080000,\"lastReceiveDeviceDataTime\":1622729016000,\"lastReceiveHeartMessageTime\":1622729076000,\"medicalDeviceId\":1,\"serialNumber\":\"B123XY182019\"},{\"collectionFinishTime\":1622728436000,\"collectionNumber\":1,\"collectionStartTime\":1622728409000,\"collectionStatus\":2,\"collectorUniqueId\":\"DC-75-0001\",\"deviceCode\":75,\"gmtCreate\":1622728410000,\"gmtModified\":1622728588000,\"lastReceiveDeviceDataTime\":1622728436000,\"lastReceiveHeartMessageTime\":1622728566000,\"medicalDeviceId\":1,\"serialNumber\":\"B123XY182019\"}]";
        List<InfoDeviceDataCollection> dataCollectionList = JSON.parseArray(data, InfoDeviceDataCollection.class);
        System.out.println(dataCollectionList.size());

    }
}
