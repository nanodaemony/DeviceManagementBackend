package com.nano.msc.devicedata.entity.serial;


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
 * 美敦力EEG-VISTA数据实体
 * @author cz
 */
@Data
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "data_mei_dun_li_eeg_vista")
public class DataMeiDunLiVista implements Serializable {

    private static final long serialVersionUID = -8055671381912697983L;

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
     * DSC
     */
    @Column(name = "dsc")
    private Double dsc;

    /**
     * PIC
     */
    @Column(name = "pic")
    private Double pic;

    // 下面是三组数据

    /**
     * SR
     */
    @Column(name = "sr1")
    private Double sr1;
    @Column(name = "sr2")
    private Double sr2;
    @Column(name = "sr3")
    private Double sr3;

    /**
     * SEF
     */
    @Column(name = "sef1")
    private Double sef1;
    @Column(name = "sef2")
    private Double sef2;
    @Column(name = "sef3")
    private Double sef3;

    /**
     * BISBIT
     */
    @Column(name = "bisbit1")
    private String bisbit1;
    @Column(name = "bisbit2")
    private String bisbit2;
    @Column(name = "bisbit3")
    private String bisbit3;

    /**
     * BIS
     */
    @Column(name = "bis1")
    private Double bis1;
    @Column(name = "bis2")
    private Double bis2;
    @Column(name = "bis3")
    private Double bis3;

    /**
     * TOTPOW
     */
    @Column(name = "totpow1")
    private Double totpow1;
    @Column(name = "totpow2")
    private Double totpow2;
    @Column(name = "totpow3")
    private Double totpow3;

    /**
     * EMGLOW
     */
    @Column(name = "emglow1")
    private Double emglow1;
    @Column(name = "emglow2")
    private Double emglow2;
    @Column(name = "emglow3")
    private Double emglow3;

    /**
     * SQI
     */
    @Column(name = "sqi1")
    private Double sqi1;
    @Column(name = "sqi2")
    private Double sqi2;
    @Column(name = "sqi3")
    private Double sqi3;

    /**
     * IMPEDNCE
     */
    @Column(name = "impednce1")
    private Double impednce1;
    @Column(name = "impednce2")
    private Double impednce2;
    @Column(name = "impednce3")
    private Double impednce3;

    /**
     * ARTF2
     */
    @Column(name = "artf21")
    private String artf21;
    @Column(name = "artf22")
    private String artf22;
    @Column(name = "artf23")
    private String artf23;


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
