
-- ---------------------------------------------------------------------------------------------------------------
--                                                  医疗仪器数据实体对应的表
-- ---------------------------------------------------------------------------------------------------------------

-- ----------------------------
-- 诺和NW9002S
-- ----------------------------
create TABLE `data_nuohe_nw9002s`
(
    `pk_id`              INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动增长',
    `collection_number`     INT UNSIGNED NOT NULL COMMENT '采集顺序号,外键连接手术信息表',
    `serial_number` VARCHAR(32)  NOT NULL DEFAULT '-1' COMMENT '设备序列号,不一定唯一',
    `status` TINYINT  NOT NULL DEFAULT '1' COMMENT '数据状态,1表示有效数据,0表示数据无效',

    `bs`                   INT      NOT NULL DEFAULT -1 COMMENT '0-100 无效值 -1000',
    `emg`                  INT      NOT NULL DEFAULT -1 COMMENT '0-100 无效值 -1000',
    `sqi`                  INT      NOT NULL DEFAULT -1 COMMENT '0-100 无效值 -1000',
    `csi`                  INT      NOT NULL DEFAULT -1 COMMENT '0-100 无效值 -1000',

    `gmt_create`           DATETIME     NOT NULL COMMENT '创建时间',
    `gmt_modified`         DATETIME     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (pk_id)
) COMMENT '诺和9002s的数据表5';


-- ----------------------------
-- 普可YY106
-- ----------------------------
create TABLE `data_puke_yy106` (
    `pk_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动增长',
    `collection_number`     INT UNSIGNED NOT NULL COMMENT '采集顺序号,外键连接手术信息表',
    `serial_number` VARCHAR(32)  NOT NULL DEFAULT '-1' COMMENT '设备序列号,不一定唯一',
    `status` TINYINT  NOT NULL DEFAULT '1' COMMENT '数据状态,1表示有效数据,0表示数据无效',

    `sqi`                  INT      NOT NULL DEFAULT -1 COMMENT '0-100 无效值 -1000',
    `ai`                   INT      NOT NULL DEFAULT -1 COMMENT '0-100 无效值 -1000',
    `emg`                  INT      NOT NULL DEFAULT -1 COMMENT '0-100 无效值 -1000',
    `bsr`                  INT      NOT NULL DEFAULT -1 COMMENT '0-100 无效值 -1000',

    `gmt_create`           DATETIME    NOT NULL COMMENT '创建时间',
    `gmt_modified`         DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (pk_id)
) COMMENT '普可yy106的数据表6';


-- ----------------------------
-- 宝莱特 A8
-- ----------------------------
CREATE TABLE `data_bao_lai_te_a8` (
    `pk_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动增长',
    `collection_number`     INT UNSIGNED NOT NULL COMMENT '采集顺序号,外键连接手术信息表',
    `serial_number` VARCHAR(32)  NOT NULL DEFAULT '-1' COMMENT '设备序列号,不一定唯一',
    `status` TINYINT  NOT NULL DEFAULT '1' COMMENT '数据状态,1表示有效数据,0表示数据无效',

    `hr` int(11) DEFAULT NULL,
    `pr` int(11) DEFAULT NULL,
    `spo2` int(11) DEFAULT NULL,
    `temperature1` double DEFAULT NULL,
    `temperature2` double DEFAULT NULL,
    `temperature_difference` double DEFAULT NULL,
    `nibp_dia` double DEFAULT NULL,
    `nibp_map` double DEFAULT NULL,
    `nibp_sys` double DEFAULT NULL,
    `ibp_dia1` double DEFAULT NULL,
    `ibp_map1` double DEFAULT NULL,
    `ibp_sys1` double DEFAULT NULL,
    `ibp_dia2` double DEFAULT NULL,
    `ibp_map2` double DEFAULT NULL,
    `ibp_sys2` double DEFAULT NULL,

    `gmt_create`           DATETIME    NOT NULL COMMENT '创建时间',
    `gmt_modified`         DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- 宜安麻醉机 8700A
-- ----------------------------
CREATE TABLE `data_yi_an8700a` (

    `pk_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动增长',
    `collection_number`     INT UNSIGNED NOT NULL COMMENT '采集顺序号,外键连接手术信息表',
    `serial_number` VARCHAR(32)  NOT NULL DEFAULT '-1' COMMENT '设备序列号,不一定唯一',
    `status` TINYINT  NOT NULL DEFAULT '1' COMMENT '数据状态,1表示有效数据,0表示数据无效',

    `air` double DEFAULT NULL,
    `et_co2` double DEFAULT NULL,
    `fi_co2` double DEFAULT NULL,
    `fi_o2` double DEFAULT NULL,
    `freq` double DEFAULT NULL,
    `mac` double DEFAULT NULL,
    `mv` double DEFAULT NULL,
    `n2o` double DEFAULT NULL,
    `n2o_exp` double DEFAULT NULL,
    `n2o_insp` double DEFAULT NULL,
    `o2` double DEFAULT NULL,
    `peak` double DEFAULT NULL,
    `peep` double DEFAULT NULL,
    `plat` double DEFAULT NULL,
    `pmean` double DEFAULT NULL,
    `vte` double DEFAULT NULL,

    `gmt_create`           DATETIME    NOT NULL COMMENT '创建时间',
    `gmt_modified`         DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;



-- ----------------------------
-- 迈瑞BeneView T8
-- ----------------------------
CREATE TABLE `data_mai_rui_t8` (
    `pk_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动增长',
    `collection_number`     INT UNSIGNED NOT NULL COMMENT '采集顺序号,外键连接手术信息表',
    `serial_number` VARCHAR(32)  NOT NULL DEFAULT '-1' COMMENT '设备序列号,不一定唯一',
    `status` TINYINT  NOT NULL DEFAULT '1' COMMENT '数据状态,1表示有效数据,0表示数据无效',

    `ecg_heart_rate` int(11) DEFAULT NULL,
    `ecg_pvc_sum` int(11) DEFAULT NULL,
    `ecg_st_param_i` double DEFAULT NULL,
    `ecg_st_param_ii` double DEFAULT NULL,
    `ecg_st_param_iii` double DEFAULT NULL,
    `ecg_st_param_avf` double DEFAULT NULL,
    `ecg_st_param_avl` double DEFAULT NULL,
    `ecg_st_param_avr` double DEFAULT NULL,
    `ecg_st_param_v1` double DEFAULT NULL,
    `ecg_st_param_v2` double DEFAULT NULL,
    `ecg_st_param_v3` double DEFAULT NULL,
    `ecg_st_param_v4` double DEFAULT NULL,
    `ecg_st_param_v5` double DEFAULT NULL,
    `ecg_st_param_v6` double DEFAULT NULL,
    `resp_respiration_rate` int(11) DEFAULT NULL,
    `spo2_percent_oxygen_saturation` int(11) DEFAULT NULL,
    `spo2_pulse_rate` int(11) DEFAULT NULL,
    `spo2_pi` double DEFAULT NULL,
    `nibp_systolic` double DEFAULT NULL,
    `nibp_diastolic` double DEFAULT NULL,
    `nibp_mean` double DEFAULT NULL,
    `temp_temperature1` double DEFAULT NULL,
    `temp_temperature2` double DEFAULT NULL,
    `temp_temperature_difference` double DEFAULT NULL,
    `art_ibp_systolic` int(11) DEFAULT NULL,
    `art_ibp_mean` int(11) DEFAULT NULL,
    `art_ibp_diastolic` int(11) DEFAULT NULL,
    `pa_ibp_systolic` int(11) DEFAULT NULL,
    `pa_ibp_mean` int(11) DEFAULT NULL,
    `pa_ibp_diastolic` int(11) DEFAULT NULL,
    `art_ppv` double DEFAULT NULL,
    `pr_pr` int(11) DEFAULT NULL,

    `gmt_create`           DATETIME    NOT NULL COMMENT '创建时间',
    `gmt_modified`         DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;



-- ----------------------------
-- 迈瑞麻醉机 WATOEX_65
-- ----------------------------
CREATE TABLE `data_mai_rui_watoex65` (
    `pk_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动增长',
    `collection_number`     INT UNSIGNED NOT NULL COMMENT '采集顺序号,外键连接手术信息表',
    `serial_number` VARCHAR(32)  NOT NULL DEFAULT '-1' COMMENT '设备序列号,不一定唯一',
    `status` TINYINT  NOT NULL DEFAULT '1' COMMENT '数据状态,1表示有效数据,0表示数据无效',

    `p_peak` double DEFAULT NULL,
    `p_mean` double DEFAULT NULL,
    `p_plat` double DEFAULT NULL,
    `peep` double DEFAULT NULL,
    `rate` double DEFAULT NULL,
    `mv` double DEFAULT NULL,
    `tve` double DEFAULT NULL,
    `ie` double DEFAULT NULL,
    `c` double DEFAULT NULL,
    `r` double DEFAULT NULL,

    `gmt_create`           DATETIME    NOT NULL COMMENT '创建时间',
    `gmt_modified`         DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- 理邦 EliteV8
-- ----------------------------
CREATE TABLE `data_li_bang_elite_v8` (

    `pk_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动增长',
    `collection_number`     INT UNSIGNED NOT NULL COMMENT '采集顺序号,外键连接手术信息表',
    `serial_number` VARCHAR(32)  NOT NULL DEFAULT '-1' COMMENT '设备序列号,不一定唯一',
    `status` TINYINT  NOT NULL DEFAULT '1' COMMENT '数据状态,1表示有效数据,0表示数据无效',

    `height` double DEFAULT NULL COMMENT '身高',
    `weight` double DEFAULT NULL COMMENT '体重',

    `hr` double DEFAULT NULL,
    `pvcs` double DEFAULT NULL,
    `rr` double DEFAULT NULL,
    `pr` double DEFAULT NULL,
    `spo2` double DEFAULT NULL,
    `temp1` double DEFAULT NULL,
    `temp2` double DEFAULT NULL,
    `cvp_map` double DEFAULT NULL,
    `lap_map` double DEFAULT NULL,
    `art_dia` double DEFAULT NULL,
    `art_map` double DEFAULT NULL,
    `art_sys` double DEFAULT NULL,
    `p2_dia` double DEFAULT NULL,
    `p2_map` double DEFAULT NULL,
    `p2_sys` double DEFAULT NULL,
    `nibp_sys` double DEFAULT NULL,
    `nibp_dia` double DEFAULT NULL,
    `nibp_map` double DEFAULT NULL,
    `nibp_pr` double DEFAULT NULL,

    `gmt_create`           DATETIME    NOT NULL COMMENT '创建时间',
    `gmt_modified`         DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- 爱琴 EGOS 600A
-- ----------------------------
CREATE TABLE `data_ai_qin_600a` (
    `pk_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动增长',
    `collection_number`     INT UNSIGNED NOT NULL COMMENT '采集顺序号,外键连接手术信息表',
    `serial_number` VARCHAR(32)  NOT NULL DEFAULT '-1' COMMENT '设备序列号,不一定唯一',
    `status` TINYINT  NOT NULL DEFAULT '1' COMMENT '数据状态,1表示有效数据,0表示数据无效',

    `toi1` double DEFAULT NULL,
    `toi2` double DEFAULT NULL,
    `toi3` double DEFAULT NULL,
    `toi4` double DEFAULT NULL,
    `thi1` double DEFAULT NULL,
    `thi2` double DEFAULT NULL,
    `thi3` double DEFAULT NULL,
    `thi4` double DEFAULT NULL,
    `chb1` double DEFAULT NULL,
    `chb2` double DEFAULT NULL,
    `chb3` double DEFAULT NULL,
    `chb4` double DEFAULT NULL,
    `chbo21` double DEFAULT NULL,
    `chbo22` double DEFAULT NULL,
    `chbo23` double DEFAULT NULL,
    `chbo24` double DEFAULT NULL,
    `cthb1` double DEFAULT NULL,
    `cthb2` double DEFAULT NULL,
    `cthb3` double DEFAULT NULL,
    `cthb4` double DEFAULT NULL,
    `probe_status1` varchar(10) DEFAULT NULL,
    `probe_status2` varchar(10) DEFAULT NULL,
    `probe_status3` varchar(10) DEFAULT NULL,
    `probe_status4` varchar(10) DEFAULT NULL,

    `gmt_create`           DATETIME    NOT NULL COMMENT '创建时间',
    `gmt_modified`         DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- 爱琴 EGOS 600B
-- ----------------------------
CREATE TABLE `data_ai_qin_600b` (
    `pk_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动增长',
    `collection_number`     INT UNSIGNED NOT NULL COMMENT '采集顺序号,外键连接手术信息表',
    `serial_number` VARCHAR(32)  NOT NULL DEFAULT '-1' COMMENT '设备序列号,不一定唯一',
    `status` TINYINT  NOT NULL DEFAULT '1' COMMENT '数据状态,1表示有效数据,0表示数据无效',

    `toi1` double DEFAULT NULL,
    `toi2` double DEFAULT NULL,
    `thi1` double DEFAULT NULL,
    `thi2` double DEFAULT NULL,
    `chb1` double DEFAULT NULL,
    `chb2` double DEFAULT NULL,
    `chbo21` double DEFAULT NULL,
    `chbo22` double DEFAULT NULL,
    `cthb1` double DEFAULT NULL,
    `cthb2` double DEFAULT NULL,
    `probe_status1` varchar(10) DEFAULT NULL,
    `probe_status2` varchar(10) DEFAULT NULL,

    `gmt_create`           DATETIME    NOT NULL COMMENT '创建时间',
    `gmt_modified`         DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- 爱琴 EGOS 600C
-- ----------------------------
CREATE TABLE `data_ai_qin_600c` (
    `pk_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动增长',
    `collection_number`     INT UNSIGNED NOT NULL COMMENT '采集顺序号,外键连接手术信息表',
    `serial_number` VARCHAR(32)  NOT NULL DEFAULT '-1' COMMENT '设备序列号,不一定唯一',
    `status` TINYINT  NOT NULL DEFAULT '1' COMMENT '数据状态,1表示有效数据,0表示数据无效',

    `toi1` double DEFAULT NULL,
    `thi1` double DEFAULT NULL,
    `chb1` double DEFAULT NULL,
    `chbo21` double DEFAULT NULL,
    `cthb1` double DEFAULT NULL,
    `probe_status1` varchar(10) DEFAULT NULL,

    `gmt_create`           DATETIME    NOT NULL COMMENT '创建时间',
    `gmt_modified`         DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- 美敦力 EEG-VISTA
-- ----------------------------
CREATE TABLE `data_mei_dun_li_eeg_vista` (
    `pk_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动增长',
    `collection_number`     INT UNSIGNED NOT NULL COMMENT '采集顺序号,外键连接手术信息表',
    `serial_number` VARCHAR(32)  NOT NULL DEFAULT '-1' COMMENT '设备序列号,不一定唯一',
    `status` TINYINT  NOT NULL DEFAULT '1' COMMENT '数据状态,1表示有效数据,0表示数据无效',

    `dsc` double DEFAULT NULL,
    `pic` double DEFAULT NULL,
    `sr1` double DEFAULT NULL,
    `sr2` double DEFAULT NULL,
    `sr3` double DEFAULT NULL,
    `sef1` double DEFAULT NULL,
    `sef2` double DEFAULT NULL,
    `sef3` double DEFAULT NULL,
    `bisbit1` varchar(10) DEFAULT NULL,
    `bisbit2` varchar(10) DEFAULT NULL,
    `bisbit3` varchar(10) DEFAULT NULL,
    `bis1` double DEFAULT NULL,
    `bis2` double DEFAULT NULL,
    `bis3` double DEFAULT NULL,
    `totpow1` double DEFAULT NULL,
    `totpow2` double DEFAULT NULL,
    `totpow3` double DEFAULT NULL,
    `emglow1` double DEFAULT NULL,
    `emglow2` double DEFAULT NULL,
    `emglow3` double DEFAULT NULL,
    `sqi1` double DEFAULT NULL,
    `sqi2` double DEFAULT NULL,
    `sqi3` double DEFAULT NULL,
    `impednce1` double DEFAULT NULL,
    `impednce2` double DEFAULT NULL,
    `impednce3` double DEFAULT NULL,
    `artf21` varchar(16) DEFAULT NULL,
    `artf22` varchar(16) DEFAULT NULL,
    `artf23` varchar(16) DEFAULT NULL,
    `gmt_create`           DATETIME    NOT NULL COMMENT '创建时间',
    `gmt_modified`         DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- 美敦力 5100C
-- ----------------------------
CREATE TABLE `data_mei_dun_li_5100C` (
    `pk_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动增长',
    `collection_number`     INT UNSIGNED NOT NULL COMMENT '采集顺序号,外键连接手术信息表',
    `serial_number` VARCHAR(32)  NOT NULL DEFAULT '-1' COMMENT '设备序列号,不一定唯一',
    `status` TINYINT  NOT NULL DEFAULT '1' COMMENT '数据状态,1表示有效数据,0表示数据无效',

    `origin_data` varchar(1024) DEFAULT NULL,

    `gmt_create`           DATETIME    NOT NULL COMMENT '创建时间',
    `gmt_modified`         DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- 普博 700
-- ----------------------------
CREATE TABLE `data_pu_bo_700` (
    `pk_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动增长',
    `collection_number`     INT UNSIGNED NOT NULL COMMENT '采集顺序号,外键连接手术信息表',
    `serial_number` VARCHAR(32)  NOT NULL DEFAULT '-1' COMMENT '设备序列号,不一定唯一',
    `status` TINYINT  NOT NULL DEFAULT '1' COMMENT '数据状态,1表示有效数据,0表示数据无效',

    `origin_data` varchar(1024) DEFAULT NULL,

    `gmt_create`           DATETIME    NOT NULL COMMENT '创建时间',
    `gmt_modified`         DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- 普博 5000D
-- ----------------------------
CREATE TABLE `data_pu_bo_5000d` (
    `pk_id`                INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动增长',
    `collection_number`     INT UNSIGNED NOT NULL COMMENT '采集顺序号,外键连接手术信息表',
    `serial_number` VARCHAR(32)  NOT NULL DEFAULT '-1' COMMENT '设备序列号,不一定唯一',
    `status` TINYINT  NOT NULL DEFAULT '1' COMMENT '数据状态,1表示有效数据,0表示数据无效',

    `origin_data` varchar(1024) DEFAULT NULL,

    `gmt_create`           DATETIME    NOT NULL COMMENT '创建时间',
    `gmt_modified`         DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;