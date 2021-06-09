package com.nano.msc.collection.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

/**
 * Description: 经济性评价返回的视图对象
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/8 20:23
 */
@Data
public class EconomicEvaluationVo {

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


    // ****************************** 经济性指标 ***************************************

    /**
     * 历史维保费用总和(全部维保费用加起来,下面三项)
     */
    private double historyMaintenanceCostSum = 0D;

    /**
     * 历史配件费用总和(仅配件费)
     */
    private double historyCostAccessoryNum = 0D;

    /**
     * 历史维修费用总和(仅维修费)
     */
    private double historyCostRepairNum = 0D;

    /**
     * 历史其他费用总和(仅其他费用)
     */
    private double historyCostOtherNum = 0D;





}
