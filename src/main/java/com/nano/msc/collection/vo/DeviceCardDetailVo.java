package com.nano.msc.collection.vo;

import java.time.LocalDate;

import lombok.Data;

/**
 * Description: 仪器卡片统计信息的VO
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/31 16:53
 */
@Data
public class DeviceCardDetailVo {


    // ********************************* 仪器基本信息 ***************************************/

    /**
     * 仪器代号
     */
    private int deviceCode = 0;

    /**
     * 序列号
     */
    private String serialNumber = "";

    /**
     * 公司名字
     */
    private String companyName = "";

    /**
     * 仪器名称
     */
    private String deviceName = "";

    /**
     * 仪器类别
     */
    private String deviceType = "";


    /**
     * 设备购买时间(可后续补充)
     */
    private LocalDate produceDate;

    /**
     * 仪器的使用年限
     */
    private Double serviceLife = 0D;


    // ********************************* 统计信息 ***************************************/

    /**
     * 总采集的场次数
     */
    int totalCollectionCounter = 0;

    /**
     * 总采集的时长
     */
    int totalCollectionTime = 0;




}
