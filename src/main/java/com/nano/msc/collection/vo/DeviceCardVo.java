package com.nano.msc.collection.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiOperation;
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
public class DeviceCardVo {


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
    private LocalDateTime produceDate;

    /**
     * 仪器的使用年限
     */
    private Double serviceLife = 0D;


    // ********************************* 数据采集统计信息 ***************************************/

    /**
     * 总采集的场次数
     */
    int totalCollectionCounter = 0;

    /**
     * 总采集的时长
     */
    int totalCollectionTime = 0;

    // ********************************* 使用评价统计信息 ***************************************/

    /**
     * 平均使用满意度
     */
    double averageUsageExperienceLevel = 0D;

    /**
     * 平均可靠性满意度
     */
    double averageUsageReliabilityLevel = 0D;


    // ********************************* 维修记录统计信息 ***************************************/

    /**
     * 维修记录次数
     */
    int maintenanceRecordCounter = 0;

    /**
     * 历史维保费用总和(全部维保费用加起来,下面三项)
     */
    double historyMaintenanceCostSum = 0D;

    /**
     * 历史配件费用总和
     */
    double historyCostAccessoryNum = 0D;

    /**
     * 历史维修费用总和
     */
    double historyCostRepairNum = 0D;

    /**
     * 历史其他费用总和
     */
    double historyCostOtherNum = 0D;

    /**
     * 平均维修响应时间满意度
     */
    double averageMaintenanceResponseTimeSatisfactionLevel = 0D;

    /**
     * 平均维修价格满意度
     */
    double averageMaintenancePriceSatisfactionLevel = 0D;

    /**
     * 平均服务态度满意度
     */
    double averageMaintenanceServiceAttitudeSatisfactionLevel = 0D;

    /**
     * 平均整体过程满意度
     */
    double averageMaintenanceOverallProcessSatisfactionLevel = 0D;

    // ********************************* 经济性指标统计信息 ***************************************/

    /**
     * 仪器购买价格(元)
     */
    private Double devicePurchasePrice;

    /**
     * 技师每月工资(元/月)
     */
    private Double technicianMonthlySalary;

    /**
     * 手术耗材费用(元/年)
     */
    private Double consumableCostMoney;

    /**
     * 固定维护维修费用(元/年)
     */
    private Double fixRepairCostMoney;

    /**
     * 每小时收益
     */
    private Double profitMoney;


    /**
     * 技师总工资
     */
    private Double totalTechnicianMonthlySalary;


    /**
     * 手术耗材费用
     */
    private Double totalConsumableCostMoney;

    /**
     * 固定维护维修费用
     */
    private Double totalFixRepairCostMoney;

    /**
     * 总收益
     */
    private Double totalProfitMoney;




}
