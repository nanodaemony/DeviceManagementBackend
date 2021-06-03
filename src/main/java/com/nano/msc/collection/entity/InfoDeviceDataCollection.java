package com.nano.msc.collection.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nano.msc.collection.enums.CollectionStatusEnum;
import com.nano.msc.common.converter.LocalDateTimeConverter;
import com.nano.msc.common.utils.TimestampUtils;

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
import lombok.NoArgsConstructor;

/**
 * 仪器数据采集实体
 *
 * @author nano
 * @version V1.0
 * @date 2019/5/28 13:00
 * Description:手术场次号 仪器号 仪器序列号
 */
@DynamicInsert
@Entity
@Data
@NoArgsConstructor
@Table(name = "info_device_data_collection")
public class InfoDeviceDataCollection implements Serializable {

    private static final long serialVersionUID = -7456701585085979233L;

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // 采集场次信息
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 采集顺序号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_collection_number")
    private Integer collectionNumber;

    /**
     * 仪器号
     */
    @NotNull(message = "DeviceCode must cannot empty")
    @Column(name = "device_code")
    private Integer deviceCode;


    /**
     * 设备序列号,不一定唯一,初始化为NULL
     */
    @ApiModelProperty(value = "设备序列号", example = "B123XY182019")
    @Column(name = "serial_number")
    private String serialNumber;

    /**
     * 仪器信息的ID号 外键仪器信息表的主键ID号
     */
    @Column(name = "medical_device_id")
    private Integer medicalDeviceId;

    /**
     * 仪器信息的ID号 外键仪器信息表的主键ID号
     */
    @Column(name = "collector_unique_id")
    private String collectorUniqueId;

    /**
     * 采集状态,对应枚举值(初始化为未开始)
     */
    @Column(name = "collection_status")
    private Integer collectionStatus = CollectionStatusEnum.WAITING.getCode();

    /**
     * 采集开始时间(默认为当前时间)
     */
    @Column(name = "collection_start_time")
    private LocalDateTime collectionStartTime = TimestampUtils.getCurrentTimeForDataBase();

    /**
     * 采集结束时间(默认为当前时间)
     */
    @Column(name = "collection_finish_time")
    private LocalDateTime collectionFinishTime = TimestampUtils.getCurrentTimeForDataBase();


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // 串口类仪器数据采集特有信息
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 串口数据采集器上次接收心跳消息的时间戳
     */
    @Column(name = "last_receive_heart_message_time")
    private LocalDateTime lastReceiveHeartMessageTime = TimestampUtils.getCurrentTimeForDataBase();

    /**
     * 串口数据采集器上次接收仪器数据消息的时间戳
     */
    @Column(name = "last_receive_device_data_time")
    private LocalDateTime lastReceiveDeviceDataTime = TimestampUtils.getCurrentTimeForDataBase();

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // 采集统计与分析相关信息
    ///////////////////////////////////////////////////////////////////////////////////////////////////

//    /**
//     * 这个仪器这场手术采集数据总条数
//     */
//    @Column(name = "data_number")
//    private Integer dataNumber;
//
//    /**
//     * 手术持续时间
//     */
//    @Column(name = "operation_duration_time")
//    private Long operationDurationTime;
//
//    /**
//     * 掉线率
//     */
//    @Column(name = "collection_drop_rate")
//    private Double collectionDropRate;

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


    public InfoDeviceDataCollection(@NotNull(message = "collectionNumber must cannot empty") Integer collectionNumber,
                                    @NotNull(message = "DeviceCode must cannot empty") Integer deviceCode,
                                    @NotNull(message = "DeviceInfoId must cannot empty") Integer medicalDeviceId) {
        this.collectionNumber = collectionNumber;
        this.deviceCode = deviceCode;
        this.medicalDeviceId = medicalDeviceId;
    }
}
