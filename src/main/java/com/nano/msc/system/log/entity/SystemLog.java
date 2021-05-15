package com.nano.msc.system.log.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nano.msc.common.converter.LocalDateTimeConverter;
import com.nano.msc.common.utils.TimestampUtils;
import com.nano.msc.system.log.enums.SystemLogEnum;

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

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统日志表
 * Description:
 * 存储日志信息到数据库
 * @author nano
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "system_log")
public class SystemLog implements Serializable {

    private static final long serialVersionUID = 1633536198072422737L;
    /**
     * 标记id，自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Integer id;

    /**
     * 日志级别
     */
    @Column(name = "log_level")
    private Integer logLevel;

    /**
     * 级别信息
     */
    @Column(name = "level_msg")
    private String levelMsg;

    /**
     * 日志信息
     */
    @Column(name = "msg")
    private String msg;

    /**
     * 数据创建时间，用于显示时间
     * 存储格式采用String，存储内容为2019/09/05/10:11
     */
    @Column(name = "record_time")
    private String recordTime;

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

    public SystemLog(Integer logLevel, String levelMsg, String msg, String recordDate) {
        this.logLevel = logLevel;
        this.levelMsg = levelMsg;
        this.msg = msg;
        this.recordTime = recordDate;
    }

    public static SystemLog errorLog(String message) {
        return new SystemLog(SystemLogEnum.ERROR.getCode(), SystemLogEnum.ERROR.getMsg(), message,
                TimestampUtils.getCurrentTimeAsString());
    }


    public static SystemLog infoLog(String message) {
        return new SystemLog(SystemLogEnum.INFO.getCode(), SystemLogEnum.INFO.getMsg(), message,
                TimestampUtils.getCurrentTimeAsString());
    }
}
