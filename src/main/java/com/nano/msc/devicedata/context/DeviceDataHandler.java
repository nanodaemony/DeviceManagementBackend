package com.nano.msc.devicedata.context;

import com.nano.msc.devicedata.base.BaseDataService;
import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.parser.DeviceDataParser;

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
     * 数据解析器
     */
    private DeviceDataParser<T> dataParser;

    public DeviceDataHandler(DeviceDataRepository<T, ID> dataRepository,
                             DeviceDataParser<T> dataParser) {
        this.dataRepository = dataRepository;
        this.dataParser = dataParser;
    }
}
