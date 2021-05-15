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
 * 苏州爱琴数据实体 EGOS600C
 * @author cz
 */
@Data
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "data_ai_qin_600c")
public class DataAiQin600C implements Serializable {

    private static final long serialVersionUID = -8052271381912697983L;

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
     * TOI值
     */
    @Column(name = "toi1")
    private Double toi1;

    /**
     * THI
     */
    @Column(name = "thi1")
    private Double thi1;

    /**
     * △CHB值
     */
    @Column(name = "chb1")
    private Double chb1;

    /**
     * △CHBO2值
     */
    @Column(name = "chbo21")
    private Double chbo21;

    /**
     * △CTHB值
     */
    @Column(name = "cthb1")
    private Double cthb1 ;

    /**
     * 探头状态字符串(包含探头状态和通信质量) 这里存的是未解析的字符串 如果需要就解析
     */
    @Column(name = "probe_status1")
    private String probeStatus1;


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
