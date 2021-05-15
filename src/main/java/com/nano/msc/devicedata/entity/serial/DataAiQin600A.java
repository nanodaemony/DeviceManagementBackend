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
 * 苏州爱琴数据实体
 * @author cz
 */
@Data
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "data_ai_qin_600a")
public class DataAiQin600A implements Serializable {

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
    @Column(name = "toi2")
    private Double toi2;
    @Column(name = "toi3")
    private Double toi3;
    @Column(name = "toi4")
    private Double toi4;

    /**
     * THI
     */
    @Column(name = "thi1")
    private Double thi1;
    @Column(name = "thi2")
    private Double thi2;
    @Column(name = "thi3")
    private Double thi3;
    @Column(name = "thi4")
    private Double thi4;

    /**
     * △CHB值
     */
    @Column(name = "chb1")
    private Double chb1;
    @Column(name = "chb2")
    private Double chb2;
    @Column(name = "chb3")
    private Double chb3;
    @Column(name = "chb4")
    private Double chb4;

    /**
     * △CHBO2值
     */
    @Column(name = "chbo21")
    private Double chbo21;
    @Column(name = "chbo22")
    private Double chbo22;
    @Column(name = "chbo23")
    private Double chbo23;
    @Column(name = "chbo24")
    private Double chbo24;

    /**
     * △CTHB值
     */
    @Column(name = "cthb1")
    private Double cthb1 ;
    @Column(name = "cthb2")
    private Double cthb2 ;
    @Column(name = "cthb3")
    private Double cthb3 ;
    @Column(name = "cthb4")
    private Double cthb4 ;

    /**
     * 探头状态字符串(包含探头状态和通信质量) 这里存的是未解析的字符串 如果需要就解析
     */
    @Column(name = "probe_status1")
    private String probeStatus1;
    @Column(name = "probe_status2")
    private String probeStatus2;
    @Column(name = "probe_status3")
    private String probeStatus3;
    @Column(name = "probe_status4")
    private String probeStatus4;

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
