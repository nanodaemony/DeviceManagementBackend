package com.nano.msc.devicedata.context;

import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.manager.DeviceDataManager;

import lombok.Getter;

/**
 * Description: 仪器数据处理器的容器
 * Usage:
 * 1. 存放对应repository及data parser.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 23:46
 */
@Getter
public class DeviceDataHandler<T, ID> {


    /**
     * 数据Repository
     */
    private DeviceDataRepository<T, ID> dataRepository;

    /**
     * 数据管理器
     */
    private DeviceDataManager<T> dataManager;


    public DeviceDataHandler(DeviceDataRepository<T, ID> dataRepository,
                             DeviceDataManager<T> dataManager) {
        this.dataRepository = dataRepository;
        this.dataManager = dataManager;
    }
}
