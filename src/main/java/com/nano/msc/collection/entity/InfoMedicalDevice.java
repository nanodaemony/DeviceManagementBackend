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
    @NotNull(message = "CompanyName must cannot empty.")
    @ApiModelProperty(value = "公司名字", example = "合肥诺和生物科技有限公司")
    @Column(name = "company_name")
    private String companyName;

    /**
     * 仪器名称
     */
    @NotNull(message = "DeviceName must cannot empty.")
    @ApiModelProperty(value = "仪器名称", example = "NW9002S")
    @Column(name = "device_name")
    private String deviceName;

    /**
     * 仪器类别
     */
    @NotNull(message = "DeviceType must cannot empty.")
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
    private LocalDate produceDate;

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
     * 使用科室
     */
    @ApiModelProperty(value = "使用科室", example = "麻醉科2手术室")
    @Column(name = "device_department")
    private String deviceDepartment;

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
