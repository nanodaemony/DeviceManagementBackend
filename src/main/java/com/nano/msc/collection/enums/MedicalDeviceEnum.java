package com.nano.msc.collection.enums;

import lombok.Getter;

/**
 * 仪器基本信息枚举
 *
 * @author nano
 * Description:
 * UDP类仪器以3开头
 * TCP类仪器以4开头
 * 串口类仪器以5开头
 *
 * 注意：
 * 仪器信息包含自定义的deviceCode
 * 仪器名称
 * 仪器厂家名称
 * 仪器类别：仪器如果涉及多个类别，则进行拼接得到字符串形式
 */
@Getter
public enum MedicalDeviceEnum {

    //------------------------------------------------------
    // UDP类仪器
    //------------------------------------------------------

    /**
     * 合肥诺和NW9002S
     */
    NUO_HE_NW9002S(30, "合肥诺和电子科技有限公司", "麻醉深度多参数监护仪(NW-9002S)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.ANESTHESIA_DEPTH_MONITOR),
            InterfaceTypeEnum.ETHERNET.getCode(), true),

    /**
     * 浙江普可YY-106
     */
    PU_KE_YY106(31, "浙江普可医疗科技有限公司", "麻醉深度监测仪(YY-106)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.ANESTHESIA_DEPTH_MONITOR),
            InterfaceTypeEnum.ETHERNET.getCode(), true),

    /**
     * 广东宝莱特 A8
     */
    BAO_LAI_TE_A8(32, "广东宝莱特医用科技股份有限公司", "重症插件式监护仪(A8)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.BLOOD_PRESSURE_MONITOR),
            InterfaceTypeEnum.ETHERNET.getCode(), true),

    /**
     * 宜安
     */
    YI_AN_8700A(33, "北京宜安医疗系统股份有限公司", "麻醉机(Aeon8700A)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.ANESTHESIA_MACHINE),
            InterfaceTypeEnum.ETHERNET.getCode(), true),

    //------------------------------------------------------
    // TCP类仪器
    //------------------------------------------------------
    /**
     * 迈瑞 BeneView T8
     */
    MAI_RUI_T8(42, "深圳迈瑞生物医疗电子股份有限公司", "监护仪(BeneViewT8)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.BLOOD_PRESSURE_MONITOR),
            InterfaceTypeEnum.ETHERNET.getCode(), true),

    /**
     * 迈瑞 WATOEX 65
     */
    MAI_RUI_WATOEX_65(43, "深圳迈瑞生物医疗电子股份有限公司", "麻醉机(WATOEX65)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.ANESTHESIA_MACHINE),
            InterfaceTypeEnum.ETHERNET.getCode(), true),

    /**
     * 理邦 ELite V8
     */
    LI_BANG_ELITE_V8(45, "深圳市理邦精密仪器股份有限公司", "监护仪(ELiteV8)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.BLOOD_PRESSURE_MONITOR),
            InterfaceTypeEnum.ETHERNET.getCode(), true),

    /**
     * 科曼 C90
     */
    KE_MAN_C90(46, "深圳市科曼医疗设备有限公司", "无创血压/监护仪(C90)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.BLOOD_PRESSURE_MONITOR),
            InterfaceTypeEnum.ETHERNET.getCode(), true),

    /**
     * 威浩康 Angle600D麻醉深度监测仪
     */
    WEI_HAO_KANG_ANGEL6000D (47, "深圳市威浩康医疗器械有限公司","麻醉深度监测仪(ANGEL6000D)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.ANESTHESIA_DEPTH_MONITOR),
            InterfaceTypeEnum.ETHERNET.getCode(), true),

    /**
     * 科曼 AX700 麻醉机
     */
    KE_MAN_AX700(48, "深圳市科曼医疗设备有限公司", "麻醉机(AX700)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.ANESTHESIA_MACHINE),
            InterfaceTypeEnum.ETHERNET.getCode(), false),

    //------------------------------------------------------
    // 串口类仪器
    //------------------------------------------------------
    
    /**
     * 苏州爱琴
     */
    AI_QIN_EGOS600A(71, "苏州爱琴生物医疗电子有限公司", "近红外组织血氧参数无损监测仪(EGOS600A)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.HEMOGLOBIN_MONITOR, DeviceTypeEnum.BRAIN_OXYGEN_MONITOR),
            InterfaceTypeEnum.SERIAL.getCode(), true),

    /**
     * 苏州爱琴
     */
    AI_QIN_EGOS600B(72, "苏州爱琴生物医疗电子有限公司", "近红外组织血氧参数无损监测仪(EGOS600B)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.HEMOGLOBIN_MONITOR, DeviceTypeEnum.BRAIN_OXYGEN_MONITOR),
            InterfaceTypeEnum.SERIAL.getCode(), true),

    /**
     * 苏州爱琴
     */
    AI_QIN_EGOS600C(73, "苏州爱琴生物医疗电子有限公司", "近红外组织血氧参数无损监测仪(EGOS600C)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.BRAIN_OXYGEN_MONITOR),
            InterfaceTypeEnum.SERIAL.getCode(), true),

    /**
     * 重庆名希
     */
    MING_XI(74, "重庆名希医疗器械有限公司", "脑血氧无创检测仪(MINER-P100)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.BRAIN_OXYGEN_MONITOR),
            InterfaceTypeEnum.SERIAL.getCode(), false),

    /**
     * 美敦力 麻醉深度
     */
    MEIDUNLI_EEG_VISTA(75, "美国美敦力公司", "麻醉深度监护仪(EEG-VISTA)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.ANESTHESIA_DEPTH_MONITOR),
            InterfaceTypeEnum.SERIAL.getCode(), true),

    /**
     * 美敦力 血红蛋白
     */
    MEIDUNLI_5100C(76, "美国美敦力公司", "脑氧饱和度监测仪(5100C)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.BRAIN_OXYGEN_MONITOR),
            InterfaceTypeEnum.SERIAL.getCode(), true),

    /**
     * 普博 Boaray700麻醉机
     */
    PU_BO_BOARAY700 (77, "深圳市普博科技有限公司", "麻醉机(Boaray700)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.ANESTHESIA_MACHINE),
            InterfaceTypeEnum.SERIAL.getCode(), true),

    /**
     * 德尔格呼吸机V300
     */
    DRAGER_V300(78, "德国德尔格公司", "呼吸机(V300)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.RESPIRATOR_MACHINE),
            InterfaceTypeEnum.SERIAL.getCode(), true),

    /**
     * 德尔格 Fabius Plus麻醉机
     */
    DRAGER_FABIUS_PLUS (79, "德国德尔格公司", "麻醉机(Fabius Plus)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.ANESTHESIA_MACHINE),
            InterfaceTypeEnum.SERIAL.getCode(), true),

    /**
     * 舒普思达 S1200呼吸机
     */
    SHU_PU_SI_DA_S1200 (80, "南京舒普思达医疗设备有限公司", "呼吸机(S1200)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.RESPIRATOR_MACHINE),
            InterfaceTypeEnum.SERIAL.getCode(), true),

    /**
     * 普博 5000D呼吸机
     */
    PU_BO_5000D (81, "深圳市普博科技有限公司", "呼吸机(5000D)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.RESPIRATOR_MACHINE),
            InterfaceTypeEnum.SERIAL.getCode(), true),

    /**
     * 晨伟 麻醉机
     */
    CHEN_WEI_CWH302 (82, "南京晨伟医疗设备有限公司", "麻醉机(CWM302)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.ANESTHESIA_MACHINE),
            InterfaceTypeEnum.SERIAL.getCode(), true),

    /**
     * 晨伟 呼吸机
     */
    CHEN_WEI_CWH3020B (83, "南京晨伟医疗设备有限公司", "呼吸机(CWM3020B)",
            DeviceTypeEnum.getTypeString(DeviceTypeEnum.RESPIRATOR_MACHINE),
            InterfaceTypeEnum.SERIAL.getCode(), true),
    ;

    /**
     * 仪器号
     */
    Integer deviceCode;

    /**
     * 公司名称
     */
    String companyName;

    /**
     * 仪器名称
     */
    String deviceName;

    /**
     * 仪器类别
     */
    String deviceType;

    /**
     * 接口类型
     */
    Integer interfaceType;

    /**
     * 能否采集数据
     */
    Boolean canCollectData;

    MedicalDeviceEnum(Integer deviceCode, String companyName, String deviceName, String deviceType, Integer interfaceType, Boolean canCollectData) {
        this.deviceCode = deviceCode;
        this.companyName = companyName;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.interfaceType = interfaceType;
        this.canCollectData = canCollectData;
    }

    /**
     * 传入仪器号得到相应的枚举
     *
     * @param code 仪器号
     * @return 对应的枚举
     */
    public static MedicalDeviceEnum matchDeviceCodeEnum(Integer code) {
        for (MedicalDeviceEnum medicalDeviceEnum : MedicalDeviceEnum.values()) {
            if (medicalDeviceEnum.getDeviceCode().equals(code)) {
                return medicalDeviceEnum;
            }
        }
        return null;
    }

}
