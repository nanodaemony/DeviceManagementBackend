package com.nano.msc.collection.vo;

import com.nano.msc.collection.enums.CollectionStatusEnum;
import com.nano.msc.common.utils.TimestampUtils;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 仪器数据采集记录详细信息VO
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/3 14:19
 */
@Data
public class DeviceDataCollectionDetailInfoVo {


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // 基本信息
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 采集顺序号
     */
    private Integer collectionNumber;

    /**
     * 仪器号
     */
    private Integer deviceCode;

    /**
     * 设备序列号,不一定唯一,初始化为NULL
     */
    private String serialNumber;

    /**
     * 仪器信息的ID号 外键仪器信息表的主键ID号
     */
    private String collectorUniqueId;

    /**
     * 采集状态,对应枚举值(初始化为未开始)
     */
    private Integer collectionStatus;

    /**
     * 采集开始时间(默认为当前时间)
     */
    private LocalDateTime collectionStartTime;

    /**
     * 采集结束时间(默认为当前时间)
     */
    private LocalDateTime collectionFinishTime;


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // 额外统计信息: 仪器使用评价信息;
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 使用科室
     */
    private String deviceDepartment;

    /**
     * 使用评价等级
     */
    private Integer experienceLevel;

    /**
     * 可靠性等级
     */
    private Integer reliabilityLevel;

    /**
     * 是否有错误信息
     */
    private Boolean hasError;

    /**
     * 错误原因
     */
    private String knownError;

    /**
     * 其他错误
     */
    private String otherError;

    /**
     * 备注
     */
    private String remark;

    /**
     * 记录人签名
     */
    private String recordName;

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    // 采集数据信息
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 采集的数据条数
     */
    private Integer collectedDataCounter = 0;

}
