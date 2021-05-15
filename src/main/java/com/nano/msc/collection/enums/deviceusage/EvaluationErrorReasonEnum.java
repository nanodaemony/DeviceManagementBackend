package com.nano.msc.collection.enums.deviceusage;

/**
 * 仪器评价故障情况枚举
 *
 * @author: nano
 * @time: 2020/5/29 18:39
 */
public enum EvaluationErrorReasonEnum {

    /**
     * 部件损坏
     */
    COMPONENT_DAMAGE("1", "部件损坏"),

    /**
     * 软件故障
     */
    SOFTWARE_FAILURE("2", "软件故障"),


    /**
     * 操作失误
     */
    OPERATION_ERROR("3", "操作失误"),

    /**
     * 环境因素
     */
    ENVIRONMENTAL_FACTOR("4", "环境因素"),

    ;

    /**
     * 代号
     */
    String code;

    /**
     * 原因
     */
    String reason;


    EvaluationErrorReasonEnum(String code, String reason) {
        this.code = code;
        this.reason = reason;
    }


    /**
     * 将存储的故障码转换为故障字符串
     *
     * @return 故障字符串
     */
    public static String convertErrorCodeToErrorString(String knownErrorCode) {
        if (knownErrorCode == null || knownErrorCode.length() == 0) {
            return "";
        }
        // 转换为2进制的ErrorCode字符串 Eg:1010
        String errorCode = Integer.toBinaryString(Integer.parseInt(knownErrorCode));
        if (errorCode.length() == 1) {
            errorCode = "000" + errorCode;
        } else if (errorCode.length() == 2) {
            errorCode = "00" + errorCode;
        } else if (errorCode.length() == 3) {
            errorCode = "0" + errorCode;
        }
        StringBuilder errorInfoBuilder = new StringBuilder();
        if (errorCode.charAt(0) == '1') {
            errorInfoBuilder.append("部件损坏").append(" ");
        }
        if (errorCode.charAt(1) == '1') {
            errorInfoBuilder.append("软件故障").append(" ");
        }
        if (errorCode.charAt(2) == '1') {
            errorInfoBuilder.append("操作失误").append(" ");
        }
        if (errorCode.charAt(3) == '1') {
            errorInfoBuilder.append("环境因素").append(" ");
        }
        return errorInfoBuilder.toString().trim();
    }

}
