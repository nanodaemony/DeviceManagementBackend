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
 * 理邦 ELite V8监护仪数据实体
 * @author cz
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Data
@Table(name = "data_li_bang_elite_v8")
public class DataLiBangEliteV8 implements Serializable {

    private static final long serialVersionUID = 1217602298523007590L;

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

    ////////////////////////////////////////////////////////////////////////////////////////////
    // 下面是仪器参数
    ////////////////////////////////////////////////////////////////////////////////////////////

    @Column(name = "weight")
    private Double weight;
    @Column(name = "height")
    private Double height;

    @Column(name = "hr")
    private Double hr;
    @Column(name = "pvcs")
    private Double pvcs;

    @Column(name = "rr")
    private Double rr;

    @Column(name = "spo2")
    private Double spo2;

    @Column(name = "pr")
    private Double pr;

    @Column(name = "temp1")
    private Double temp1;
    @Column(name = "temp2")
    private Double temp2;

    @Column(name = "cvp_map")
    private Double cvpMap;

    @Column(name = "lap_map")
    private Double lapMap;

    /**
     * ART参数
     */
    @Column(name = "art_sys")
    private Double artSys;
    @Column(name = "art_dia")
    private Double artDia;
    @Column(name = "art_map")
    private Double artMap;

    /**
     * P2参数
     */
    @Column(name = "p2_sys")
    private Double p2Sys;
    @Column(name = "p2_dia")
    private Double p2Dia;
    @Column(name = "p2_map")
    private Double p2Map;

    /**
     * NIBP相关参数(演示模式没有这个参数,但是根据协议应该是有的.)
     */
    @Column(name = "nibp_sys")
    private Double nibpSys;
    @Column(name = "nibp_dia")
    private Double nibpDia;
    @Column(name = "nibp_map")
    private Double nibpMap;
    @Column(name = "nibp_pr")
    private Double nibpPr;


    ////////////////////////////////////////////////////////////////////////////////////////////
    // 下面是仪器参数
    ////////////////////////////////////////////////////////////////////////////////////////////

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
