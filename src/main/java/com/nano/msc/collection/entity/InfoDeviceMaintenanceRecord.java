package com.nano.msc.collection.entity;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 仪器维修记录表
 *
 * @author cz
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Data
@Table(name = "info_device_maintenance_record")
@AllArgsConstructor
public class InfoDeviceMaintenanceRecord implements Serializable {

    private static final long serialVersionUID = -4113168698411125393L;

    /**
     * 标记id，自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Integer id;

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
     * 故障发生时间
     */
    @JsonSerialize(using = LocalDateTimeConverter.class)
    @Column(name = "time_error_occur")
    private LocalDateTime timeErrorOccur;

    /**
     * 通知维修时间
     */
    @JsonSerialize(using = LocalDateTimeConverter.class)
    @Column(name = "time_notice_to_repair")
    private LocalDateTime timeNoticeToRepair;

    /**
     * 开始维修时间
     */
    @JsonSerialize(using = LocalDateTimeConverter.class)
    @Column(name = "time_start_repair")
    private LocalDateTime timeStartRepair;

    /**
     * 恢复使用时间
     */
    @JsonSerialize(using = LocalDateTimeConverter.class)
    @Column(name = "time_resume_use")
    private LocalDateTime timeResumeUse;

    /**
     * 维修方式
     */
    @Column(name = "maintenance_mode")
    private String maintenanceMode;

    /**
     * 维修人员
     */
    @Column(name = "maintenance_people")
    private String maintenancePeople;

    /**
     * 故障原因
     */
    @Column(name = "error_reason")
    private String errorReason;


    /**
     * 是否在保质期内
     */
    @Column(name = "within_the_shelf_life")
    private boolean withinTheShelfLife;

    /**
     * 是否更换配件
     */
    @Column(name = "replace_accessory")
    private boolean replaceAccessory;

    /**
     * 是否解决故障
     */
    @Column(name = "error_overcome")
    private boolean errorOvercome;

    /**
     * 故障未解决原因
     */
    @Column(name = "error_not_overcome_reason")
    private String errorNotOvercomeReason;

    /**
     * 配件费数值
     */
    @Column(name = "cost_accessory_num")
    private Double costAccessoryNum;

    /**
     * 维修费数值
     */
    @Column(name = "cost_repair_num")
    private Double costRepairNum;

    /**
     * 其他费用(原因)
     */
    @Column(name = "cost_other_method")
    private String costOtherMethod;

    /**
     * 其他费用(数值)
     */
    @Column(name = "cost_other_num")
    private Double costOtherNum;

    /**
     * 维修响应时间满意度
     */
    @Column(name = "maintenance_response_time_satisfaction_level")
    private Integer maintenanceResponseTimeSatisfactionLevel;

    /**
     * 维修价格满意度
     */
    @Column(name = "maintenance_price_satisfaction_level")
    private Integer maintenancePriceSatisfactionLevel;

    /**
     * 维修服务态度满意度
     */
    @Column(name = "maintenance_service_attitude_satisfaction_level")
    private Integer maintenanceServiceAttitudeSatisfactionLevel;

    /**
     * 维修过程整体满意度
     */
    @Column(name = "maintenance_overall_process_satisfaction_level")
    private Integer maintenanceOverallProcessSatisfactionLevel;

    /**
     * 售后与维保改进建议
     */
    @Column(name = "maintenance_improvement_suggestions")
    private String maintenanceImprovementSuggestions;

    /**
     * 记录人签名
     */
    @Column(name = "record_name")
    private String recordName;

    /**
     * 记录填写时间
     */
    @JsonSerialize(using = LocalDateTimeConverter.class)
    @Column(name = "record_time")
    private LocalDateTime recordTime;

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


    public InfoDeviceMaintenanceRecord() {

    }



}
