package com.nano.msc.collection.enums.deviceusage;

import lombok.Getter;

/**
 * Description: 满意度等级枚举
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/3 17:30
 */
@Getter
public enum SatisfactionLevelEnum {

    /**
     * 非常满意
     */
    VERY_GOOD(1, "非常满意"),

    /**
     * 满意
     */
    GOOD(2, "满意"),

    /**
     * 一般
     */
    JUST_SO_SO(3, "一般"),

    /**
     * 不满意
     */
    BAD(4, "不满意"),

    /**
     * 非常不满意
     */
    VERY_BAD(5, "非常不满意")
    ;

    /**
     * 评价等级
     */
    Integer level;

    /**
     * 评价信息
     */
    String msg;


    SatisfactionLevelEnum(Integer level, String msg) {
        this.level = level;
        this.msg = msg;
    }

    /**
     * 传入等级信息并得到字符串
     *
     * @param level 等级
     * @return 信息
     */
    public static String getLevelMsg(Integer level){
        for (SatisfactionLevelEnum levelEnum : SatisfactionLevelEnum.values()) {
            if (levelEnum.level.equals(level)) {
                return levelEnum.getMsg();
            }
        }
        return "";
    }

}
