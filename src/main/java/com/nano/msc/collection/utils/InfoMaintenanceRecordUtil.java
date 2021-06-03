package com.nano.msc.collection.utils;

import com.nano.msc.collection.entity.InfoDeviceMaintenanceRecord;
import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;

import java.util.List;

/**
 * Description: 仪器维修记录工具类
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/3 15:50
 */
public class InfoMaintenanceRecordUtil {


    /**
     * 获取历史配件费之和
     *
     * @param maintenanceRecordList 维修记录情况
     * @return 配件费之和
     */
    public static double getHistoryCostAccessoryNum(List<InfoDeviceMaintenanceRecord> maintenanceRecordList) {
        if (maintenanceRecordList == null || maintenanceRecordList.size() == 0) {
            return 0D;
        }
        double sum = 0;
        for (InfoDeviceMaintenanceRecord record : maintenanceRecordList) {
            // 因为满意度最高在数据库中为1,需要切换为5分
            sum = sum + record.getCostAccessoryNum();
        }
        return sum;
    }

    /**
     * 获取历史维修费之和
     *
     * @param maintenanceRecordList 维修记录情况
     * @return 维修费之和
     */
    public static double getHistoryCostRepairNum(List<InfoDeviceMaintenanceRecord> maintenanceRecordList) {
        if (maintenanceRecordList == null || maintenanceRecordList.size() == 0) {
            return 0D;
        }
        double sum = 0;
        for (InfoDeviceMaintenanceRecord record : maintenanceRecordList) {
            // 因为满意度最高在数据库中为1,需要切换为5分
            sum = sum + record.getCostRepairNum();
        }
        return sum;
    }

    /**
     * 获取其他维修费之和
     *
     * @param maintenanceRecordList 维修记录情况
     * @return 其他费之和
     */
    public static double getHistoryCostOtherNum(List<InfoDeviceMaintenanceRecord> maintenanceRecordList) {
        if (maintenanceRecordList == null || maintenanceRecordList.size() == 0) {
            return 0D;
        }
        double sum = 0;
        for (InfoDeviceMaintenanceRecord record : maintenanceRecordList) {
            // 因为满意度最高在数据库中为1,需要切换为5分
            sum = sum + record.getCostOtherNum();
        }
        return sum;
    }


    /**
     * 计算某个仪器的平均维修响应时间满意度(按照分数计算)
     */
    public static double getAverageMaintenanceResponseTimeSatisfactionLevel(List<InfoDeviceMaintenanceRecord> maintenanceRecordList) {
        if (maintenanceRecordList == null || maintenanceRecordList.size() == 0) {
            return 0D;
        }
        double sum = 0;
        for (InfoDeviceMaintenanceRecord record : maintenanceRecordList) {
            // 因为满意度最高在数据库中为1,需要切换为5分
            sum = sum + record.getMaintenanceResponseTimeSatisfactionLevel();
        }
        return sum / maintenanceRecordList.size();
    }


    /**
     * 计算某个仪器的平均维修价格满意度(按照分数计算)
     */
    public static double getAverageMaintenancePriceSatisfactionLevel(List<InfoDeviceMaintenanceRecord> maintenanceRecordList) {
        if (maintenanceRecordList == null || maintenanceRecordList.size() == 0) {
            return 0D;
        }
        double sum = 0;
        for (InfoDeviceMaintenanceRecord record : maintenanceRecordList) {
            // 因为满意度最高在数据库中为1,需要切换为5分
            sum = sum + record.getMaintenancePriceSatisfactionLevel();
        }
        return sum / maintenanceRecordList.size();
    }


    /**
     * 计算某个仪器的平均服务态度满意度(按照分数计算)
     */
    public static double getAverageMaintenanceServiceAttitudeSatisfactionLevel(List<InfoDeviceMaintenanceRecord> maintenanceRecordList) {
        if (maintenanceRecordList == null || maintenanceRecordList.size() == 0) {
            return 0D;
        }
        double sum = 0;
        for (InfoDeviceMaintenanceRecord record : maintenanceRecordList) {
            // 因为满意度最高在数据库中为1,需要切换为5分
            sum = sum + record.getMaintenanceServiceAttitudeSatisfactionLevel();
        }
        return sum / maintenanceRecordList.size();
    }


    /**
     * 计算某个仪器的平均整体过程满意度(按照分数计算)
     */
    public static double getAverageMaintenanceOverallProcessSatisfactionLevel(List<InfoDeviceMaintenanceRecord> maintenanceRecordList) {
        if (maintenanceRecordList == null || maintenanceRecordList.size() == 0) {
            return 0D;
        }
        double sum = 0;
        for (InfoDeviceMaintenanceRecord record : maintenanceRecordList) {
            // 因为满意度最高在数据库中为1,需要切换为5分
            sum = sum + record.getMaintenanceOverallProcessSatisfactionLevel();
        }
        return sum / maintenanceRecordList.size();
    }

}
