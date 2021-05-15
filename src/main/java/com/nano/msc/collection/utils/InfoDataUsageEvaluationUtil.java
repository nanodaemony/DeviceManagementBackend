package com.nano.msc.collection.utils;


import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.collection.entity.InfoDeviceDataCollection;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Description: 仪器评价的工具类
 * Usage:
 *     @see #getEvaluationAverageExperienceLevel(List) 计算某个仪器的平均使用体验度等级(按照分数计算).
 *     @see #getEvaluationAverageReliabilityLevel(List) 计算某个仪器的平均可靠性等级(按照分数计算).
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/8 16:01
 */
public class InfoDataUsageEvaluationUtil {


    /**
     * 计算某个仪器的平均使用体验度等级(按照分数计算)
     *
     *
     * @param evaluationList 某仪器的全部评价信息列表
     * @return 平均使用体验度等级
     */
    public static double getEvaluationAverageExperienceLevel(List<InfoDeviceUsageEvaluation> evaluationList) {
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
    public static double getEvaluationAverageReliabilityLevel(List<InfoDeviceUsageEvaluation> evaluationList) {
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
     * 计算仪器平均故障率
     *
     * @param evaluationList 评价列表
     * @return 平均故障率
     */
    public static Double getDeviceAverageErrorRate(List<InfoDeviceUsageEvaluation> evaluationList) {
        if (evaluationList == null || evaluationList.size() == 0) {
            return 0.00;
        }
        long errorTimes = evaluationList.stream().filter(InfoDeviceUsageEvaluation::getHasError).count();
        // 保留4位小数
        DecimalFormat df = new DecimalFormat("#.0000");
        return Double.parseDouble(df.format((double) (errorTimes / evaluationList.size())));
    }

    /**
     * 计算采集掉线率
     *
     * @param operationDeviceList 手术仪器列表
     * @return 掉线率
     */
    public static Double getAverageDeviceDropRate(List<InfoDeviceDataCollection> operationDeviceList) {

//        if (operationDeviceList == null) {
//            return 0.00;
//        }
//        double sum = 0.00;
//        for (InfoDeviceDataCollection operationDevice : operationDeviceList) {
//            sum = sum + operationDevice.getCollectionDropRate();
//        }
//        double dropRate = sum / operationDeviceList.size();
//        // 保留4位小数
//        DecimalFormat df = new DecimalFormat("#.0000");
//
//        // 格式化返回
//        return Double.parseDouble(df.format(dropRate));
        return null;
    }
}
