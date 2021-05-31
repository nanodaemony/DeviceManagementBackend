package com.nano.msc.collection.service;

import com.nano.msc.common.vo.CommonResult;

import java.time.LocalDate;
import java.util.Map;

/**
 * Description: 仪器管理相关接口
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/31 14:53
 */
public interface DeviceManageService {

    /**
     * 获取仪器过去一段时间开机个数信息(总的)
     * @return 开机信息
     */
    CommonResult<Map<LocalDate, Integer>> getDeviceHistoryOpenNumberOfOneDay(int days);


    /**
     * 获取仪器过去一段时间内每一天的采集时长信息
     * @param days 历史天数
     * @return 过去每天的采集时长
     */
    CommonResult<Map<LocalDate, Long>> getDeviceHistoryTotalCollectionTimeOfOneDay(int days);


    /**
     * 获取仪器过去一段时间采集场次信息(总的)
     * @return 采集场次信息
     */
    CommonResult<Map<LocalDate, Integer>> getDeviceHistoryCollectionCounterOfOneDay(int days);

}
