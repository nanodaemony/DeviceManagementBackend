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
 * 诺和数据实体
 * @author nano
 * Description:
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Data
@Table(name = "data_nuohe_nw9002s")
public class DataNuoHe9002s implements Serializable {

    private static final long serialVersionUID = -3302167041948046696L;

    /**
     * 自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Integer id;

    /**
     * 采集顺序号
     */
    @Column(name = "collection_number")
    private Integer collectionNumber;

    /**
     * 序列号
     */
    @Column(name = "serial_number")
    private String serialNumber;


    /**
     * 数据状态,1表示有效数据,0表示数据无效
     */
    @Column(name = "status")
    private Integer status;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // 中间是仪器数据
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * BS
     */
    @Column(name = "bs")
    private Integer bs;

    /**
     * EMG
     */
    @Column(name = "emg")
    private Integer emg;

    /**
     * SQI
     */
    @Column(name = "sqi")
    private Integer sqi;

    /**
     * CSI
     */
    @Column(name = "csi")
    private Integer csi;


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
