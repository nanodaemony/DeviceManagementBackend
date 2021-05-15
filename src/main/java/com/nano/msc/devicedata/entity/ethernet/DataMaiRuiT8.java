package com.nano.msc.devicedata.entity.ethernet;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nano.msc.common.converter.LocalDateTimeConverter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 迈瑞T8监护仪数据实体
 *
 * @author cz
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Data
@Table(name = "data_mai_rui_t8")
public class DataMaiRuiT8 implements Serializable {

    private static final long serialVersionUID = 2996146984144237891L;

    /**
     * 自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Integer id;
    /**
     * 序列号
     */
    @Column(name = "serial_number")
    private String serialNumber;

    /**
     * 采集顺序号
     */
    @NotNull(message = "collectionNumber must cannot empty")
    @Column(name = "collection_number")
    private Integer collectionNumber;


    /**
     * 数据状态,1表示有效数据,0表示数据无效
     */
    @Column(name = "status")
    private Integer status;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // 中间是仪器数据
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * ECG Heart Rate
     */
    @Column(name = "ecg_heart_rate")
    private Integer ecgHeartRate;

    /**
     * ECG PVC Sum
     */
    @Column(name = "ecg_pvc_sum")
    private Integer ecgPvcSum;

    @Column(name = "ecg_st_param_i")
    private Double ecgStParamI = 0D;
    @Column(name = "ecg_st_param_ii")
    private Double ecgStParamII = 0D;
    @Column(name = "ecg_st_param_iii")
    private Double ecgStParamIII = 0D;
    @Column(name = "ecg_st_param_avr")
    private Double ecgStParamAvr = 0D;
    @Column(name = "ecg_st_param_avl")
    private Double ecgStParamAvl = 0D;
    @Column(name = "ecg_st_param_avf")
    private Double ecgStParamAvf = 0D;
    @Column(name = "ecg_st_param_v1")
    private Double ecgStParamV1 = 0D;
    @Column(name = "ecg_st_param_v2")
    private Double ecgStParamV2 = 0D;
    @Column(name = "ecg_st_param_v3")
    private Double ecgStParamV3 = 0D;
    @Column(name = "ecg_st_param_v4")
    private Double ecgStParamV4 = 0D;
    @Column(name = "ecg_st_param_v5")
    private Double ecgStParamV5 = 0D;
    @Column(name = "ecg_st_param_v6")
    private Double ecgStParamV6 = 0D;


    /**
     * RESP Respiration Rate
     */
    @Column(name = "resp_respiration_rate")
    private Integer respRespirationRate;


    /**
     * SPO2 Percent Oxygen Saturation
     */
    @Column(name = "spo2_percent_oxygen_saturation")
    private Integer spo2PercentOxygenSaturation;


    /**
     * SPO2 pulse rate
     */
    @Column(name = "spo2_pulse_rate")
    private Integer spo2PulseRate;

    /**
     * SPO2 pulse PI
     */
    @Column(name = "spo2_pi")
    private Double spo2Pi;


    /**
     * NIBP 无创血压的几个参数值
     */
    @Column(name = "nibp_systolic")
    private Double nibpSystolic = 0D;
    @Column(name = "nibp_diastolic")
    private Double nibpDiastolic = 0D;
    @Column(name = "nibp_mean")
    private Double nibpMean = 0D;

    /**
     * 两个温度值及其差值
     */
    @Column(name = "temp_temperature1")
    private Double tempTemperature1 = 0D;
    @Column(name = "temp_temperature2")
    private Double tempTemperature2 = 0D;
    @Column(name = "temp_temperature_difference")
    private Double tempTemperatureDifference = 0D;

    /**
     * 由ART模块产生的有创血压参数: 120 93 80 (仪器展示)
     */
    @Column(name = "art_ibp_systolic")
    private Integer artIbpSystolic = 0;
    @Column(name = "art_ibp_mean")
    private Integer artIbpMean = 0;
    @Column(name = "art_ibp_diastolic")
    private Integer artIbpDiastolic = 0;

    /**
     * 由PA模块产生的有创血压参数: 25 14 9(仪器没有展示)
     */
    @Column(name = "pa_ibp_systolic")
    private Integer paIbpSystolic = 0;
    @Column(name = "pa_ibp_mean")
    private Integer paIbpMean = 0;
    @Column(name = "pa_ibp_diastolic")
    private Integer paIbpDiastolic = 0;

    /**
     * ART PPV
     */
    @Column(name = "art_ppv")
    private Double artPpv = 0D;

    /**
     * PR(来自PR模块的PR参数)
     */
    @Column(name = "pr_pr")
    private Integer prPr = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // 中间是仪器数据
    ////////////////////////////////////////////////////////////////////////////////////////////////

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
