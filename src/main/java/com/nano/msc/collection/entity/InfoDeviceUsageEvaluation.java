package com.nano.msc.collection.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nano.msc.collection.enums.deviceusage.EvaluationLevelEnum;
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
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 采集完成后进行的仪器评价表单实体(用于每次结束数据采集对仪器进行评价)
 *
 * @author cz
 * Description：手术场次号 仪器代号 仪器序列号 科室 使用的各种信息等
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Data
@Table(name = "info_device_usage_evaluation")
@AllArgsConstructor
public class InfoDeviceUsageEvaluation implements Serializable {

    private static final long serialVersionUID = -4113168698465125393L;

    /**
     * 标记id，自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Integer id;

    /**
     * 手术顺序号
     */
    @Column(name = "collection_number")
    private Integer collectionNumber;

    /**
     * 产生标记的识别号(用于回传给采集器标识哪些已经成功存储)
     */
    @NotBlank(message = "Unique_number must cannot empty")
    @Column(name = "unique_number")
    private String uniqueNumber;

    /**
     * 仪器代号
     */
    @Column(name = "device_code")
    private Integer deviceCode;

    /**
     * 仪器序列号
     */
    @Column(name = "serial_number")
    private String serialNumber;

    /**
     * 使用科室
     */
    @Column(name = "device_department")
    private String deviceDepartment;

    /**
     * 使用评价等级
     */
    @Column(name = "experience_level")
    private Integer experienceLevel;

    /**
     * 可靠性等级
     */
    @Column(name = "reliability_level")
    private Integer reliabilityLevel;

    /**
     * 是否有错误信息
     */
    @Column(name = "has_error")
    private Boolean hasError;

    /**
     * 错误原因
     */
    @Column(name = "known_error")
    private String knownError;

    /**
     * 其他错误
     */
    @Column(name = "other_error")
    private String otherError;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 记录人签名
     */
    @Column(name = "record_name")
    private String recordName;

    /**
     * 是否有效
     */
    @Column(name = "valid")
    private Boolean valid = true;

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


    public InfoDeviceUsageEvaluation() {
    }

    public InfoDeviceUsageEvaluation(Integer collectionNumber, Integer deviceCode, String serialNumber, String deviceDepartment, Integer experienceLevel, Integer reliabilityLevel, Boolean hasError, String knownError, String otherError, String remark, String recordName) {
        this.collectionNumber = collectionNumber;
        this.deviceCode = deviceCode;
        this.serialNumber = serialNumber;
        this.deviceDepartment = deviceDepartment;
        this.experienceLevel = experienceLevel;
        this.reliabilityLevel = reliabilityLevel;
        this.hasError = hasError;
        this.knownError = knownError;
        this.otherError = otherError;
        this.remark = remark;
        this.recordName = recordName;
    }


}
