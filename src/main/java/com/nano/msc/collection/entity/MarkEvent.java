package com.nano.msc.collection.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 手术事件的实体类
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/2 16:00
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Data
@NoArgsConstructor
@Table(name = "mark_event")
public class MarkEvent {

    private static final long serialVersionUID = -4892589808312433198L;

    /**
     * 标记id，自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Integer id;

    /**
     * 事件大类
     */
    @NotNull(message = "mark_main_type must cannot empty")
    @Column(name = "mark_main_type")
    private String markMainType;

    /**
     * 事件小类
     */
    @NotNull(message = "mark_sub_type must cannot empty")
    @Column(name = "mark_sub_type")
    private String markSubType;

    /**
     * 具体事件
     */
    @NotNull(message = "mark_event must cannot empty")
    @Column(name = "mark_event")
    private String markEvent;

    /**
     * 是否为常用事件
     */
    @Column(name = "often_use")
    private Integer oftenUse;

    /**
     * 使用此标记的频率(每使用一次就+1,获取到列表中就排序靠前)
     */
    @Column(name = "mark_frequency")
    private Integer markFrequency;

    /**
     * 是否是新增的标记信息
     */
    @Column(name = "whether_new_add")
    private Integer whetherNewAdd;

}
