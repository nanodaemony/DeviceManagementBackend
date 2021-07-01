package com.nano.msc.collection.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nano.msc.common.converter.LocalDateTimeConverter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 数据采集器类
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/10 23:00
 */
@DynamicInsert
@Entity
@Data
@NoArgsConstructor
@Table(name = "info_data_collector")
public class DataCollector implements Serializable {

    private static final long serialVersionUID = -7410701585085979233L;

    /**
     * 标记id，自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Integer id;

    /**
     * 仪器信息的ID号 外键仪器信息表的主键ID号
     */
    @Column(name = "collector_unique_id")
    private String collectorUniqueId;

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
     * 采集器类别(1是Pad采集器, 2是串口采集器)
     */
    @Column(name = "collector_type")
    private Integer collectorType;

    /**
     * 采集器状态
     */
    @Column(name = "collector_status")
    private Integer collectorStatus;

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
