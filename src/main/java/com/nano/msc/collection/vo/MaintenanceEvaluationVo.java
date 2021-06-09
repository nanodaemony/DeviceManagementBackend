package com.nano.msc.collection.vo;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 维保评价视图对象
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/9 15:12
 */
@Data
@NoArgsConstructor
public class MaintenanceEvaluationVo {

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


    // ****************************** 数据维保信息 ***************************************

    /**
     * 维修记录次数
     */
    private int maintenanceRecordCounter = 0;

//    /**
//     * 第一次故障发生时间(距离仪器启用后的天数)
//     */
//    private int firstErrorOccurTimeAfterUsage = 0;

    /**
     * 平均维修时间
     */
    private double averageRepairTime = 0;


    /**
     * 维修方式次数统计(自修)
     */
    private int maintenanceModeCounterRepairBySelf = 0;

    /**
     * 维修方式次数统计(上门修)
     */
    private int maintenanceModeCounterRepairByInDoor = 0;

    /**
     * 维修方式次数统计(返厂修)
     */
    private int maintenanceModeCounterRepairByBackToFactory = 0;

    /**
     * 维修方式次数统计(第三方修)
     */
    private int maintenanceModeCounterRepairByThirdParty = 0;


    /**
     * 维修方式次数统计(其他维修方式)
     */
    private int maintenanceModeCounterRepairByOtherMethod = 0;


    /**
     * 维修人员次数统计(设备科维系人员)
     */
    private int maintenancePeopleCounterFromEquipmentDepartment = 0;

    /**
     * 维修人员次数统计(厂家维修人员)
     */
    private int maintenancePeopleCounterFromFactory = 0;

    /**
     * 维修人员次数统计(科室配备维修人员)
     */
    private int maintenancePeopleCounterFromDetailDepartment = 0;

    /**
     * 维修人员次数统计(设备使用人员)
     */
    private int maintenancePeopleCounterFromDeviceUser = 0;

    /**
     * 维修人员次数统计(其他维修人员)
     */
    private int maintenancePeopleCounterFromOtherPeople = 0;

    /**
     * 是否在保质期内次数(是)
     */
    private int withinShelfLifeYes = 0;

    /**
     * 是否在保质期内次数(否)
     */
    private int withinShelfLifeNo = 0;

    /**
     * 故障原因次数(配件损坏)
     */
    private int errorReasonCounterComponentError = 0;

    /**
     * 故障原因次数(软件故障)
     */
    private int errorReasonCounterSoftwareError = 0;

    /**
     * 故障原因次数(操作失误)
     */
    private int errorReasonCounterOperationError = 0;

    /**
     * 故障原因次数(环境因素)
     */
    private int errorReasonCounterEnvironmentReason = 0;

    /**
     * 故障原因次数(其他故障原因)
     */
    private int errorReasonCounterOtherError = 0;

    /**
     * 是否更换配件次数(是)
     */
    private int replaceAccessoryYes = 0;

    /**
     * 是否更换配件次数(否)
     */
    private int replaceAccessoryNo = 0;

    /**
     * 故障是否解决次数(是)
     */
    private int errorOvercomeYes = 0;

    /**
     * 故障是否解决次数(否)
     */
    private int errorOvercomeNo = 0;


    /**
     * 历史配件费用总和
     */
    private double historyCostAccessoryNum = 0D;

    /**
     * 历史维修费用总和
     */
    private double historyCostRepairNum = 0D;

    /**
     * 历史其他费用总和
     */
    private double historyCostOtherNum = 0D;

    /**
     * 平均维修响应时间满意度
     */
    private double averageMaintenanceResponseTimeSatisfactionLevel = 0D;

    /**
     * 平均维修价格满意度
     */
    private double averageMaintenancePriceSatisfactionLevel = 0D;

    /**
     * 平均服务态度满意度
     */
    private double averageMaintenanceServiceAttitudeSatisfactionLevel = 0D;

    /**
     * 平均整体过程满意度
     */
    private double averageMaintenanceOverallProcessSatisfactionLevel = 0D;

    /**
     * 售后与维保改进建议
     */
    private int maintenanceImprovementSuggestionsCounter = 0;



}
