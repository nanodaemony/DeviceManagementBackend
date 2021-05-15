package com.nano.msc.collection.entity;


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
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 手术信息实体
 *
 * @author nano
 * Description: 手术的各类信息汇总表
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Data
@Table(name = "info_operation")
public class InfoOperation implements Serializable {

    private static final long serialVersionUID = -5085503116296589504L;

    /**
     * 手术顺序号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_operation_number")
    private Integer operationNumber;


    /**
     * 医院手麻系统的手术顺序号(每个医院的都不一样)
     */
    @NotBlank(message = "hospital_operation_number must cannot empty")
    @Column(name = "hospital_operation_number")
    @ApiModelProperty(value = "医院的手术顺序号", example = "AS1235621")
    private String hospitalOperationNumber;


    ///////////////////////////////////////////////////////////////////////////////////////////
    // 病人信息
    ///////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 性别0--男，1--女
     */
    @ApiModelProperty(value = "性别0--男，1--女", example = "0")
    @Column(name = "patient_sex")
    private Integer patientSex;

    /**
     * 身高
     */
    @ApiModelProperty(value = "身高", example = "180")
    @Column(name = "patient_height")
    private Integer patientHeight;

    /**
     * 体重
     */
    @ApiModelProperty(value = "体重", example = "70")
    @Column(name = "patient_weight")
    private Double patientWeight;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄", example = "87")
    @Column(name = "patient_age")
    private Integer patientAge;


    ///////////////////////////////////////////////////////////////////////////////////////////
    // 手术信息
    ///////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 手术名称
     */
    @Column(name = "operation_name")
    @ApiModelProperty(value = "手术名称", example = "TestName")
    private String operationName;

    /**
     * 麻醉方式
     */
    @Column(name = "operation_anesthesia_mode")
    @ApiModelProperty(value = "麻醉方式", example = "局部麻醉")
    private String operationAnesthesiaMode;


    /**
     * 是否急诊
     */
    @Column(name = "operation_is_urgent")
    @ApiModelProperty(value = "是否急诊", example = "true")
    private Boolean operationIsUrgent;

    /**
     * ASA等级
     */
    @Column(name = "operation_asa_level")
    @ApiModelProperty(value = "ASA等级", example = "3")
    private Integer operationAsaLevel;

    /**
     * 心功能等级
     */
    @Column(name = "operation_heart_function_level")
    @ApiModelProperty(value = "心功能等级", example = "I级", notes = "心功能等级,非必须,0表示没填,分为 I级 II级 III级 IV级")
    private Integer operationHeartFunctionLevel;

    /**
     * 肺功能等级
     */
    @Column(name = "operation_lung_function_level")
    @ApiModelProperty(value = "肺功能等级", example = "4级", notes = "肺功能等级,非必须,0表示没填,分为 1234 级")
    private Integer operationLungFunctionLevel;

    /**
     * 肝功能等级
     */
    @Column(name = "operation_liver_function_level")
    @ApiModelProperty(value = "肝功能等级", example = "A级", notes = "肝功能等级,非必须,0表示没填,分为 ABC 级")
    private Integer operationLiverFunctionLevel;

    /**
     * 肾功能等级
     */
    @Column(name = "operation_kidney_function_level")
    @ApiModelProperty(value = "肾功能等级", example = "3", notes = "肾功能等级,非必须,0表示没填,分为 12345 级")
    private Integer operationKidneyFunctionLevel;

    /**
     * 术前诊断
     */
    @Column(name = "before_operation_diagnosis")
    @ApiModelProperty(value = "术前诊断", example = "感冒")
    private String beforeOperationDiagnosis;

    /**
     * 既往病史
     */
    @Column(name = "past_medical_history")
    @ApiModelProperty(value = "既往病史", example = "无")
    private String pastMedicalHistory;

    /**
     * 特殊病情(情况)
     */
    @Column(name = "special_disease_case")
    @ApiModelProperty(value = "特殊病情(情况)", example = "无")
    private String specialDiseaseCase;


    /**
     * 数据创建时间
     */
    @Column(name = "gmt_create")
    @ApiModelProperty(value = "创建时间")
    @CreationTimestamp
    private LocalDateTime gmtCreate;

    /**
     * 数据修改时间
     */
    @Column(name = "gmt_modified")
    @ApiModelProperty(value = "更新时间")
    @UpdateTimestamp
    private LocalDateTime gmtModified;

}

