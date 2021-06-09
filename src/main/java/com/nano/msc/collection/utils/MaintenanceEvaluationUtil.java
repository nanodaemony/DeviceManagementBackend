package com.nano.msc.collection.utils;

import com.nano.msc.collection.entity.InfoDeviceMaintenanceRecord;
import com.nano.msc.collection.entity.InfoMedicalDevice;
import com.nano.msc.collection.vo.MaintenanceEvaluationVo;
import com.nano.msc.common.utils.TimestampUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * Description: 维保评价工具类
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/9 15:34
 */
public class MaintenanceEvaluationUtil {

    /**
     * 计算平均维修时间
     *
     * @param maintenanceRecordList 维修信息列表
     */
    public static double getAverageRepairTime(List<InfoDeviceMaintenanceRecord> maintenanceRecordList) {
        if (maintenanceRecordList.size() == 0) {
            return 0D;
        }
        double total = 0D;
        for (InfoDeviceMaintenanceRecord record : maintenanceRecordList) {
            LocalDate start = record.getTimeStartRepair().toLocalDate();
            LocalDate finish = record.getTimeResumeUse().toLocalDate();
            double days = Math.abs((int) start.toEpochDay() - finish.toEpochDay()) + 1;
            total = total + days;
        }
        return total / maintenanceRecordList.size();
    }


    /**
     * 统计维修方式的次数
     *
     * @param maintenanceRecordList 维修信息列表
     * @param evaluationVo          维系记录信息
     */
    public static void getMaintenanceModeCounterRepairCounter(List<InfoDeviceMaintenanceRecord> maintenanceRecordList, MaintenanceEvaluationVo evaluationVo) {
        int self = 0;
        int inDoor = 0;
        int backToFacotry = 0;
        int thirdParty = 0;
        int other = 0;
        for (InfoDeviceMaintenanceRecord record : maintenanceRecordList) {
            if ("自修".equals(record.getMaintenanceMode())) {
                self++;
            } else if ("上门修".equals(record.getMaintenanceMode())) {
                inDoor++;
            } else if ("返厂修".equals(record.getMaintenanceMode())) {
                backToFacotry++;
            } else if ("第三方修".equals(record.getMaintenanceMode())) {
                thirdParty++;
            } else {
                other++;
            }
        }
        evaluationVo.setMaintenanceModeCounterRepairBySelf(self);
        evaluationVo.setMaintenanceModeCounterRepairByInDoor(inDoor);
        evaluationVo.setMaintenanceModeCounterRepairByBackToFactory(backToFacotry);
        evaluationVo.setMaintenanceModeCounterRepairByThirdParty(thirdParty);
        evaluationVo.setMaintenanceModeCounterRepairByOtherMethod(other);
    }

    /**
     * 统计维修人员的次数
     *
     * @param maintenanceRecordList 维修信息列表
     * @param evaluationVo          维系记录信息
     */
    public static void getMaintenancePeopleCounter(List<InfoDeviceMaintenanceRecord> maintenanceRecordList, MaintenanceEvaluationVo evaluationVo) {
        int equipmentDepartment = 0;
        int factory = 0;
        int detailDepartment = 0;
        int deviceUser = 0;
        int other = 0;
        for (InfoDeviceMaintenanceRecord record : maintenanceRecordList) {
            if ("设备科维修人员".equals(record.getMaintenanceMode())) {
                equipmentDepartment++;
            } else if ("厂家维修人员".equals(record.getMaintenanceMode())) {
                factory++;
            } else if ("科室配备维修人员".equals(record.getMaintenanceMode())) {
                detailDepartment++;
            } else if ("设备使用人员".equals(record.getMaintenanceMode())) {
                deviceUser++;
            } else {
                other++;
            }
        }
        evaluationVo.setMaintenancePeopleCounterFromEquipmentDepartment(equipmentDepartment);
        evaluationVo.setMaintenancePeopleCounterFromFactory(factory);
        evaluationVo.setMaintenancePeopleCounterFromDetailDepartment(detailDepartment);
        evaluationVo.setMaintenancePeopleCounterFromDeviceUser(deviceUser);
        evaluationVo.setMaintenancePeopleCounterFromOtherPeople(other);
    }


    /**
     * 统计故障原因的次数
     *
     * @param maintenanceRecordList 维修信息列表
     * @param evaluationVo          维系记录信息
     */
    public static void getErrorReasonCounter(List<InfoDeviceMaintenanceRecord> maintenanceRecordList, MaintenanceEvaluationVo evaluationVo) {
        int componentError = 0;
        int softwareError = 0;
        int operationError = 0;
        int environmentReason = 0;
        int other = 0;
        for (InfoDeviceMaintenanceRecord record : maintenanceRecordList) {
            if ("配件损坏".equals(record.getMaintenanceMode())) {
                componentError++;
            } else if ("软件故障".equals(record.getMaintenanceMode())) {
                softwareError++;
            } else if ("操作失误".equals(record.getMaintenanceMode())) {
                operationError++;
            } else if ("环境因素".equals(record.getMaintenanceMode())) {
                environmentReason++;
            } else {
                other++;
            }
        }
        evaluationVo.setErrorReasonCounterComponentError(componentError);
        evaluationVo.setErrorReasonCounterSoftwareError(softwareError);
        evaluationVo.setErrorReasonCounterOperationError(operationError);
        evaluationVo.setErrorReasonCounterEnvironmentReason(environmentReason);
        evaluationVo.setErrorReasonCounterOtherError(other);
    }

    /**
     * 统计是否在保质期内的次数
     *
     * @param maintenanceRecordList 维修信息列表
     * @param evaluationVo          维系记录信息
     */
    public static void getWithinShelfLifeCounter(List<InfoDeviceMaintenanceRecord> maintenanceRecordList, MaintenanceEvaluationVo evaluationVo) {
        int yes = 0;
        int no = 0;
        for (InfoDeviceMaintenanceRecord record : maintenanceRecordList) {
            if (record.isWithinTheShelfLife()) {
                yes++;
            } else {
                no++;
            }
        }
        evaluationVo.setWithinShelfLifeYes(yes);
        evaluationVo.setWithinShelfLifeNo(no);
    }

    /**
     * 统计是否更换配件的次数
     *
     * @param maintenanceRecordList 维修信息列表
     * @param evaluationVo          维系记录信息
     */
    public static void getReplaceAccessoryCounter(List<InfoDeviceMaintenanceRecord> maintenanceRecordList, MaintenanceEvaluationVo evaluationVo) {
        int yes = 0;
        int no = 0;
        for (InfoDeviceMaintenanceRecord record : maintenanceRecordList) {
            if (record.isReplaceAccessory()) {
                yes++;
            } else {
                no++;
            }
        }
        evaluationVo.setReplaceAccessoryYes(yes);
        evaluationVo.setReplaceAccessoryNo(no);
    }


    /**
     * 统计是否解决故障的次数
     *
     * @param maintenanceRecordList 维修信息列表
     * @param evaluationVo          维系记录信息
     */
    public static void getErrorOvercomeCounter(List<InfoDeviceMaintenanceRecord> maintenanceRecordList, MaintenanceEvaluationVo evaluationVo) {
        int yes = 0;
        int no = 0;
        for (InfoDeviceMaintenanceRecord record : maintenanceRecordList) {
            if (record.isErrorOvercome()) {
                yes++;
            } else {
                no++;
            }
        }
        evaluationVo.setErrorOvercomeYes(yes);
        evaluationVo.setErrorOvercomeNo(no);
    }


    /**
     * 统计维保建议的次数
     *
     * @param maintenanceRecordList 维修信息列表
     */
    public static int getMaintenanceImprovementSuggestionsCounter(List<InfoDeviceMaintenanceRecord> maintenanceRecordList) {
        int res = 0;
        for (InfoDeviceMaintenanceRecord record : maintenanceRecordList) {
            String suggestion = record.getMaintenanceImprovementSuggestions();
            if (suggestion != null && suggestion.length() > 0) {
                res++;
            }
        }
        return res;
    }




}