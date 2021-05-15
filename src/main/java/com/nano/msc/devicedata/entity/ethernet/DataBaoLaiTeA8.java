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
 * 宝莱特的数据实体类
 * @author cz
 *
 * 存储不同的参数数值以及对应的波形字符串以备后续使用
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Data
@Table(name = "data_bao_lai_te_a8")
public class DataBaoLaiTeA8 implements Serializable {

    private static final long serialVersionUID = -8052271381912697989L;

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
    @NotNull(message = "collectionNumber must cannot empty")
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
     * 心率数据值及其波形 E0
     */
    private Integer hr;

    /**
     * 血氧值模块数据及其波形 E1
     */
    private Integer spo2;

    /**
     * 脉搏数据值 E2
     */
    private Integer pr;

    /**
     * 体温值 E4
     */
    private Double temperature1;
    private Double temperature2;
    @Column(name = "temperature_difference")
    private Double temperatureDifference;

    /**
     * 无创血压全部数值(3个) E5 NIBP
     */
    @Column(name = "nibp_sys")
    private Double nibpSys;
    @Column(name = "nibp_map")
    private Double nibpMap;
    @Column(name = "nibp_dia")
    private Double nibpDia;


    /**
     * 有创血压全部数值(6个) 分别对应屏幕上的 P1与P2的三个值
     */
    @Column(name = "ibp_sys1")
    private Double ibpSys1;
    @Column(name = "ibp_map1")
    private Double ibpMap1;
    @Column(name = "ibp_dia1")
    private Double ibpDia1;

    @Column(name = "ibp_sys2")
    private Double ibpSys2;
    @Column(name = "ibp_map2")
    private Double ibpMap2;
    @Column(name = "ibp_dia2")
    private Double ibpDia2;

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
