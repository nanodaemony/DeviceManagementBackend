package com.nano.msc.collection.utils;


import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;

import java.util.List;

/**
 * Description: 仪器评价的工具类
 * Usage:
 *     @see #getAverageUsageEvaluationExperienceLevel(List) 计算某个仪器的平均使用体验度等级(按照分数计算).
 *     @see #getAverageUsageEvaluationReliabilityLevel(List) (List) 计算某个仪器的平均可靠性等级(按照分数计算).
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/8 16:01
 */
public class InfoDeviceUsageEvaluationUtil {


    /**
     * 计算某个仪器的平均使用体验度等级(按照分数计算)
     *
     *
     * @param evaluationList 某仪器的全部评价信息列表
     * @return 平均使用体验度等级
     */
    public static double getAverageUsageEvaluationExperienceLevel(List<InfoDeviceUsageEvaluation> evaluationList) {
        if (evaluationList == null || evaluationList.size() == 0) {
            return 0D;
        }
        double experienceScore = 0;
        for (InfoDeviceUsageEvaluation evaluation : evaluationList) {
            // 因为满意度最高在数据库中为1,需要切换为5分
            experienceScore = experienceScore + 6 - evaluation.getExperienceLevel();
        }
        return experienceScore / evaluationList.size();
    }


    /**
     * 计算某个仪器的平均可靠性等级(按照分数计算)
     *
     * @param evaluationList 某仪器的全部评价信息列表
     * @return 平均可靠性等级
     */
    public static double getAverageUsageEvaluationReliabilityLevel(List<InfoDeviceUsageEvaluation> evaluationList) {
        if (evaluationList == null || evaluationList.size() == 0) {
            return 0D;
        }
        double reliabilityScore = 0;
        for (InfoDeviceUsageEvaluation evaluation : evaluationList) {
            reliabilityScore = reliabilityScore + (6 - evaluation.getReliabilityLevel());
        }
        return reliabilityScore / evaluationList.size();
    }


    /**
     * 获取总的记录的错误发生次数
     *
     * @param evaluationList 评价列表
     * @return 总错误次数
     */
    public static int getTotalRecordErrorCounter(List<InfoDeviceUsageEvaluation> evaluationList) {
        int res = 0;
        for (InfoDeviceUsageEvaluation evaluation : evaluationList) {
            if (evaluation.getHasError()) {
                res++;
            }
        }
        return res;
    }

}
