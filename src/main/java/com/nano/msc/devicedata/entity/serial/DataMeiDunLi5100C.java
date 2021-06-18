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
 * 美敦力5100C数据实体
 * @author cz
 */
@Data
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "data_mei_dun_li_5100c")
public class DataMeiDunLi5100C implements Serializable {

    private static final long serialVersionUID = -8055671381912697103L;

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
    private Integer status = 1;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // 中间是仪器数据
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Column(name = "oxygen_saturation_l")
    private Double oxygenSaturationL;
    @Column(name = "oxygen_saturation_r")
    private Double oxygenSaturationR;
    @Column(name = "oxygen_saturation_s1")
    private Double oxygenSaturationS1;
    @Column(name = "oxygen_saturation_s2")
    private Double oxygenSaturationS2;

    @Column(name = "event_l")
    private Double eventL;
    @Column(name = "event_r")
    private Double eventR;
    @Column(name = "event_s1")
    private Double eventS1;
    @Column(name = "event_s2")
    private Double eventS2;

    @Column(name = "status_l")
    private Double statusL;
    @Column(name = "status_r")
    private Double statusR;
    @Column(name = "status_s1")
    private Double statusS1;
    @Column(name = "status_s2")
    private Double statusS2;

    @Column(name = "base_l")
    private Double baseL;
    @Column(name = "base_r")
    private Double baseR;
    @Column(name = "base_s1")
    private Double baseS1;
    @Column(name = "base_s2")
    private Double baseS2;

    @Column(name = "auc_l")
    private Double aucL;
    @Column(name = "auc_r")
    private Double aucR;
    @Column(name = "auc_s1")
    private Double aucS1;
    @Column(name = "auc_s2")
    private Double aucS2;

    @Column(name = "ual_l")
    private Double ualL;
    @Column(name = "ual_r")
    private Double ualR;
    @Column(name = "ual_s1")
    private Double ualS1;
    @Column(name = "ual_s2")
    private Double ualS2;

    @Column(name = "lal_l")
    private Double lalL;
    @Column(name = "lal_r")
    private Double lalR;
    @Column(name = "lal_s1")
    private Double lalS1;
    @Column(name = "lal_s2")
    private Double lalS2;

    @Column(name = "a_l")
    private Double aL;
    @Column(name = "a_r")
    private Double aR;
    @Column(name = "a_s1")
    private Double aS1;
    @Column(name = "a_s2")
    private Double aS2;

    @Column(name = "b_l")
    private Double bL;
    @Column(name = "b_r")
    private Double bR;
    @Column(name = "b_s1")
    private Double bS1;
    @Column(name = "b_s2")
    private Double bS2;

    @Column(name = "c_l")
    private Double cL;
    @Column(name = "c_r")
    private Double cR;
    @Column(name = "c_s1")
    private Double cS1;
    @Column(name = "c_s2")
    private Double cS2;


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
