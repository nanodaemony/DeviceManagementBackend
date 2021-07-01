package com.nano.msc.collection.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nano.msc.common.converter.LocalDateTimeConverter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 医疗仪器表
 *
 * @author cz
 * @version V1.0
 * Description: 仪器生产厂商 仪器序列号 购买时间 使用年限
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Data
@NoArgsConstructor
@Table(name = "info_medical_device")
public class InfoMedicalDevice implements Serializable {

    private static final long serialVersionUID = 233410313766289238L;

    /**
     * 自动增长ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Integer id;

    /**
     * 仪器代号
     */
    @NotNull(message = "DeviceCode must cannot empty.")
    @ApiModelProperty(value = "仪器代号", example = "30")
    @Column(name = "device_code")
    private Integer deviceCode;

    /**
     * 公司名字
     */
    @ApiModelProperty(value = "公司名字", example = "合肥诺和生物科技有限公司")
    @Column(name = "company_name")
    private String companyName;

    /**
     * 仪器名称
     */
    @ApiModelProperty(value = "仪器名称", example = "NW9002S")
    @Column(name = "device_name")
    private String deviceName;

    /**
     * 仪器类别
     */
    @ApiModelProperty(value = "仪器类别", example = "1#2#3")
    @Column(name = "device_type")
    private String deviceType;

    /**
     * 设备序列号,不一定唯一,初始化为NULL
     */
    @ApiModelProperty(value = "设备序列号", example = "B123XY182019")
    @Column(name = "serial_number")
    private String serialNumber;

    /**
     * 设备购买时间(可后续补充)
     */
    @ApiModelProperty(value = "设备购买时间", example = "2018.06.25")
    @Column(name = "produce_date")
    @JsonSerialize(using = LocalDateTimeConverter.class)
    private LocalDateTime produceDate;

    /**
     * 仪器的使用年限
     */
    @ApiModelProperty(value = "仪器的使用年限", example = "5")
    @Column(name = "service_life")
    private Double serviceLife;

    /**
     * 当前仪器对应的采集器唯一ID号,仅适用于通过串口采集的设备
     */
    @ApiModelProperty(value = "采集器唯一ID号", example = "DC-75-001")
    @Column(name = "collector_unique_id")
    private String collectorUniqueId;

    /**
     * 接口类型, 1: 网口 2: 串口
     */
    @ApiModelProperty(value = "接口类型", example = "2")
    @Column(name = "interface_type")
    private Integer interfaceType;

    /**
     * 能否实现数据采集
     */
    @ApiModelProperty(value = "能否实现数据采集", example = "false")
    @Column(name = "can_collect_data")
    private Boolean canCollectData;

    /**
     * 使用科室
     */
    @ApiModelProperty(value = "使用科室", example = "麻醉科2手术室")
    @Column(name = "device_department")
    private String deviceDepartment;


    // *************************** 经济性指标 **********************************************//

    /**
     * 仪器购买价格(元)
     */
    @ApiModelProperty(value = "仪器购买价格(元)", example = "200000")
    @Column(name = "device_purchase_price")
    private Double devicePurchasePrice;

    /**
     * 技师每月工资(元/月)
     */
    @ApiModelProperty(value = "技师每月工资(元/月)", example = "8000")
    @Column(name = "technician_monthly_salary")
    private Double technicianMonthlySalary;

    /**
     * 手术耗材费用(元/年)
     */
    @ApiModelProperty(value = "手术耗材费用(元/年)", example = "2000")
    @Column(name = "consumable_cost_money")
    private Double consumableCostMoney;

    /**
     * 固定维护维修费用(元/年)
     */
    @ApiModelProperty(value = "固定维护维修费用(元/年)", example = "2000")
    @Column(name = "fix_repair_cost_money")
    private Double fixRepairCostMoney;

    /**
     * 单次收益费用
     */
    @ApiModelProperty(value = "单次收益费用", example = "500")
    @Column(name = "profit_money")
    private Double profitMoney;

    /**
     * 每天电费消费
     */
    @ApiModelProperty(value = "每天电费消费(元)", example = "8")
    @Column(name = "daily_power_cost")
    private Double dailyPowerCost;

    /**
     * 数据创建时间
     */
    @JsonSerialize(using = LocalDateTimeConverter.class)
    @Column(name = "gmt_create")
    @ApiModelProperty(value = "创建时间")
    @CreationTimestamp
    private LocalDateTime gmtCreate;

    /**
     * 数据修改时间
     */
    @JsonSerialize(using = LocalDateTimeConverter.class)
    @Column(name = "gmt_modified")
    @ApiModelProperty(value = "更新时间")
    @UpdateTimestamp
    private LocalDateTime gmtModified;

}
