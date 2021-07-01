package com.nano.msc.collection.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

/**
 * Description: 雷达图VO
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/29 16:01
 */
@Data
public class RadarMapVo {

    // ********************************* 仪器基本信息 ***************************************/

    /**
     * 仪器代号
     */
    private int deviceCode = 0;

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
     * 平均使用满意度
     */
    double averageUsageExperienceLevel = 0D;

    /**
     * 平均可靠性满意度
     */
    double averageUsageReliabilityLevel = 0D;

    /**
     * 30天内使用的天数
     */
    private Map<LocalDate, Boolean> usedDaysInPast30Days;

    /**
     * 平均维修记录次数
     */
    private double averageMaintenanceRecordCounter = 0;

    /**
     * 平均维修花费费用
     */
    private double averageHistoryMaintenanceCostSum = 0;

    /**
     * 平均整体过程满意度
     */
    private double averageMaintenanceOverallProcessSatisfactionLevel = 0D;

    /**
     * 仪器购买价格(元)
     */
    private Double devicePurchasePrice;

    /**
     * 总收益
     */
    private Double totalProfitMoney;


}
