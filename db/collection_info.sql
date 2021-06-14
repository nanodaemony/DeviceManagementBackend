
-- ----------------------------
-- 医疗仪器信息表(OK)
-- ----------------------------
create TABLE `info_medical_device` (
    `pk_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键，自增',

    `device_code`   INT  NOT NULL COMMENT '设备号,自行定义,需要设备号对应的仪器名',
    `company_name` VARCHAR(32) COMMENT '公司名称',
    `device_name` VARCHAR(32) COMMENT '仪器名称',
    `device_type` VARCHAR(32) COMMENT '仪器类型(大类：麻醉机、呼吸机、监护仪等),如果涉及多个类别存成字符串形式,如1#2#5',
    `interface_type` INT COMMENT '接口类型, 1: 网口 2: 串口',
    `can_collect_data` BOOL COMMENT '能否实现数据采集',

    `collector_unique_id` VARCHAR(64) COMMENT '当前对应的采集器的唯一ID',
    `serial_number` VARCHAR(32) COMMENT '设备序列号,不一定唯一',
    `produce_date` DATE COMMENT '设备生产时间 eg: 2017-08-01',
    `service_life` FLOAT COMMENT '设备使用年限',
    `device_department` VARCHAR(256) COMMENT '使用科室',
    `consumable_cost_money` DOUBLE COMMENT '单次手术耗材费用(元)',
    `profit_money` DOUBLE COMMENT '单次手术收益(元)',
    `device_power` DOUBLE COMMENT '仪器功率(KW)',

    `gmt_create` DATETIME COMMENT '创建时间',
    `gmt_modified` DATETIME COMMENT '更新时间',
    PRIMARY KEY(pk_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '医疗仪器信息表';


-- ----------------------------
-- 仪器数据采集信息表
-- ----------------------------
create TABLE `info_device_data_collection` (
    `pk_collection_number` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键，自增,采集场次号',
    `medical_device_id`  TINYINT NOT NULL COMMENT '外键,医疗仪器信息表中仪器的行号ID',

    `device_code` VARCHAR(32)  NOT NULL COMMENT '设备号',
    `serial_number` VARCHAR(32)  DEFAULT NULL COMMENT '设备序列号',
    `collector_unique_id`  VARCHAR(40) NOT NULL COMMENT '采集器的唯一编号,网口仪器采集时为MAC地址,串口仪器采集时为唯一编号',
    `collection_status` TINYINT NOT NULL COMMENT '采集状态,对应状态枚举的值',
    `collection_start_time`  DATETIME COMMENT '采集整体开始时间',
    `collection_finish_time` DATETIME COMMENT '采集整体结束时间',

    `last_receive_heart_message_time` DATETIME COMMENT '上次接收心跳消息的时间戳',
    `last_receive_device_data_time` DATETIME COMMENT '上次接收仪器数据消息的时间戳',

    `gmt_create` DATETIME NOT NULL COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL COMMENT '更新时间',
    PRIMARY KEY(`pk_collection_number`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '采集使用仪器信息表';


-- ----------------------------
-- 数据采集器表
-- ----------------------------
create TABLE `info_data_collector` (
    `pk_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键，自增,采集场次号',
    `collector_unique_id`  VARCHAR(40) NOT NULL COMMENT '采集器的唯一编号,网口仪器采集时为MAC地址,串口仪器采集时为唯一编号',

    `device_code` VARCHAR(32)  NOT NULL COMMENT '设备号',
    `serial_number` VARCHAR(32)  DEFAULT NULL COMMENT '设备序列号',
    `collector_type` TINYINT NOT NULL COMMENT '采集器类型,1: Pad采集器, 2: 串口采集器',
    `collector_status` TINYINT NOT NULL COMMENT '采集器状态,对应状态枚举的值',

    `gmt_create` DATETIME NOT NULL COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL COMMENT '更新时间',
    PRIMARY KEY(`pk_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '数据采集器表';



-- ----------------------------
-- 仪器使用情况评价表
-- ----------------------------
CREATE TABLE `info_device_usage_evaluation` (
    `pk_id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID, 主键',

    `device_code` INT NOT NULL COMMENT '仪器号',
    `serial_number` VARCHAR(64) NOT NULL COMMENT '仪器序列号',
    `unique_number` VARCHAR(32) NOT NULL COMMENT '唯一序列号时间戳组成',
    `collection_number` INT NOT NULL DEFAULT 0 COMMENT '采集场次号(可以为空,表示是对无法采集仪器的评价信息)',

    `device_use_duration_time` INT COMMENT '仪器当次使用时长(单位为秒)',
    `device_department` VARCHAR(256) NOT NULL COMMENT '使用科室',
    `experience_level` TINYINT NOT NULL COMMENT '使用评价等级',
    `reliability_level` TINYINT NOT NULL COMMENT '可靠性等级',
    `has_error` BOOL NOT NULL COMMENT '是否有错误信息',
    `known_error` VARCHAR(256) COMMENT '错误原因',
    `other_error` VARCHAR(256) COMMENT '其他错误',
    `remark_info` VARCHAR(256) COMMENT '备注信息',
    `record_name` VARCHAR(32) COMMENT '记录人签名',

    `valid` BOOL NOT NULL COMMENT '是否有效',

    `gmt_create` DATETIME NOT NULL COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL COMMENT '更新时间',
    PRIMARY KEY (pk_id)
) COMMENT '仪器使用评价表';


-- ----------------------------
-- 仪器维修与保养记录表
-- ----------------------------
CREATE TABLE `info_device_maintenance_record` (
    `pk_id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID, 主键',

    `device_code` INT NOT NULL COMMENT '仪器号',
    `serial_number` VARCHAR(64) NOT NULL COMMENT '仪器序列号',

    `time_error_occur` DATETIME COMMENT '故障发生时间',
    `time_notice_to_repair` DATETIME COMMENT '通知维修时间',
    `time_start_repair` DATETIME COMMENT '开始维修时间',
    `time_resume_use` DATETIME COMMENT '恢复使用时间',

    `maintenance_mode` VARCHAR(16) COMMENT '维修方式:自修 上门修 返厂修 第三方维修 其他(通过#号连接不同的方式)',
    `maintenance_people` VARCHAR(32) COMMENT '维修人员: 设备科维修人员 厂家维修人员 科室配备维修人员 设备使用人员 其他(通过#连接不同人员信息)',
    `error_reason` VARCHAR(64) COMMENT '故障原因: 配件损坏 软件故障 操作失误 环境因素 其他(通过#连接不同故障原因)',

    `within_the_shelf_life` BOOL COMMENT '是否在保质期内: 是/否',
    `replace_accessory` BOOL COMMENT '更换配件: 是/否',
    `error_overcome` BOOL COMMENT '故障解决: 是/否',
    `error_not_overcome_reason` VARCHAR(256)  DEFAULT NULL COMMENT '故障未解决原因',

    `cost_accessory_num` DOUBLE  DEFAULT 0 COMMENT '配件费数值',
    `cost_repair_num` DOUBLE  DEFAULT 0 COMMENT '维修费数值',
    `cost_other_method` VARCHAR(32)  DEFAULT NULL COMMENT '其他费用: 记录费用来源',
    `cost_other_num` DOUBLE  DEFAULT 0 COMMENT '其他费用: 记录费用数值',

    `maintenance_response_time_satisfaction_level` TINYINT DEFAULT NULL COMMENT '维修响应时间满意度',
    `maintenance_price_satisfaction_level` TINYINT DEFAULT NULL COMMENT '维修价格满意度',
    `maintenance_service_attitude_satisfaction_level` TINYINT DEFAULT NULL COMMENT '维修服务态度满意度',
    `maintenance_overall_process_satisfaction_level` TINYINT DEFAULT NULL COMMENT '维修过程整体满意度',

    `maintenance_improvement_suggestions` VARCHAR(512) DEFAULT NULL COMMENT '售后与维保改进建议',
    `record_name` VARCHAR(32) DEFAULT NULL COMMENT '记录人签名',
    `record_time` DATETIME COMMENT '填写时间',

    `valid` BOOL DEFAULT NULL COMMENT '是否有效',

    `gmt_create` DATETIME NOT NULL COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL COMMENT '更新时间',
    PRIMARY KEY (pk_id)
) COMMENT '仪器维修记录表';

-- ----------------------------
-- 手术信息表: (预定可以从手麻系统导入) 此表可有可无,非强制
-- ----------------------------
create TABLE `info_operation` (
    `pk_operation_number` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键,手术场次号,自增',
    `collection_number_list`   VARCHAR(32) NOT NULL COMMENT '关联的采集号码列表,由数据采集APP端进行生成,多个之间通过#连接',
    `hospital_operation_number` VARCHAR(32)  NOT NULL COMMENT '医院的手术唯一顺序号',

    `patient_sex`              DOUBLE  DEFAULT NULL COMMENT '性别 0--男，1--女',
    `patient_height`           DOUBLE  DEFAULT NULL COMMENT '身高',
    `patient_weight`           DOUBLE  DEFAULT NULL COMMENT '体重',
    `patient_age`              DOUBLE  DEFAULT NULL COMMENT '年龄',

    `operation_name`           VARCHAR(64)  DEFAULT NULL COMMENT '手术名称',
    `operation_anesthesia_mode`  VARCHAR(32) DEFAULT NULL COMMENT '麻醉方式',
    `operation_is_urgent`      TINYINT     DEFAULT NULL COMMENT '是否急诊',
    `operation_asa_level`      TINYINT     DEFAULT NULL COMMENT 'ASA等级,必须',
    `operation_heart_function_level`  TINYINT    DEFAULT 0 COMMENT '心功能等级,非必须,0表示没填,分为 I级 II级 III级 IV级',
    `operation_lung_function_level`   TINYINT    DEFAULT 0 COMMENT '肺功能等级,非必须,0表示没填,分为 1234 级',
    `operation_liver_function_level`   TINYINT   DEFAULT 0 COMMENT '肝功能等级,非必须,0表示没填,分为 ABC 级',
    `operation_kidney_function_level`   TINYINT  DEFAULT 0 COMMENT '肾功能等级,非必须,0表示没填,分为 12345 级',

    `before_operation_diagnosis` TEXT DEFAULT NULL COMMENT '术前诊断',
    `past_medical_history`       TEXT DEFAULT NULL COMMENT '既往病史',
    `special_disease_case`       TEXT DEFAULT NULL COMMENT '特殊病情(情况)',

    `gmt_create` DATETIME   NOT NULL COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL COMMENT '更新时间',
    INDEX `operation_name` (`operation_name`),
    PRIMARY KEY(pk_operation_number)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '医院手术信息表';

-- ----------------------------
-- 手术标记信息表
-- ----------------------------
create TABLE `info_operation_mark` (
    `pk_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标记id,自动增长',
    `mark_event_id`        INT NOT NULL COMMENT '外键,具体标记事件的ID号',
    `collection_number_list`   VARCHAR(32) NOT NULL COMMENT '关联的采集号码列表,由数据采集APP端进行生成,多个之间通过#连接',
    `unique_number`        VARCHAR(32) NOT NULL COMMENT '手术标记产生号,由采集场次号+产生时时间戳组成',

    `mark_main_type`       VARCHAR(32)  NOT NULL COMMENT '标记大类型',
    `mark_sub_type`        VARCHAR(32)  NOT NULL COMMENT '标记小类型',
    `mark_event`           VARCHAR(32)  NOT NULL COMMENT '事件类型',
    `give_medicine_method` VARCHAR(32)  NOT NULL COMMENT '途径',
    `give_medicine_volume` VARCHAR(32)  NOT NULL COMMENT '剂量',
    `side_effect`          VARCHAR(256) NOT NULL COMMENT '不良反应/特殊情况',
    `mark_time`            DATETIME    NOT NULL COMMENT '标记信息标记的时间',

    `gmt_create` DATETIME COMMENT '创建时间',
    `gmt_modified` DATETIME COMMENT '更新时间',
    PRIMARY KEY (pk_id)
) COMMENT '手术标记信息表';

-- ----------------------------
-- 系统日志表
-- ----------------------------
CREATE TABLE `system_log` (
    `pk_id`        SMALLINT(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `log_level`     TINYINT     NOT NULL COMMENT '日志级别',
    `level_msg`  VARCHAR(10)  NOT NULL COMMENT '日志级别信息',
    `msg`      TEXT             NOT NULL COMMENT '日志信息',
    `record_time`  VARCHAR(32)  NOT NULL COMMENT '日志记录时间',
    `gmt_create` DATETIME NOT NULL COMMENT '创建时间',
    `gmt_modified` DATETIME NOT NULL COMMENT '更新时间',
    PRIMARY KEY (pk_id)
) COMMENT '系统日志表';

