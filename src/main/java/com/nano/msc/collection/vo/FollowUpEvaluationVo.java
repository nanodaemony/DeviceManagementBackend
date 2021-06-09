package com.nano.msc.collection.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

/**
 * Description: 追踪评价返回的视图对象
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/8 20:23
 */
@Data
public class FollowUpEvaluationVo {

    /**
     * 仪器号
     */
    private int deviceCode;

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
    private LocalDateTime produceDate;

    /**
     * 仪器的使用年限
     */
    private Double serviceLife = 0D;


    // ****************************** 数据采集信息 ***************************************

    /**
     * 总数据采集场次数
     */
    private int totalDataCollectionCounter;

    /**
     * 总数据采集时长
     */
    private int totalDataCollectionTime;

    /**
     * 七天内使用的天数
     */
    private Map<LocalDate, Boolean> usedDaysInPast15Days;

    /**
     * 30天内使用的天数
     */
    private Map<LocalDate, Boolean> usedDaysInPast30Days;

    /**
     * 90天内使用的天数
     */
    private Map<LocalDate, Boolean> usedDaysInPast90Days;


    // ****************************** 使用评价信息 ***************************************

    /**
     * 总使用评价记录数
     */
    private int totalUsageEvaluationCounter;

    /**
     * 平均使用满意度
     */
    double averageUsageExperienceLevel = 0D;

    /**
     * 平均可靠性满意度
     */
    double averageUsageReliabilityLevel = 0D;

    /**
     * 记录故障次数
     */
    int recordErrorCounter = 0;




}
