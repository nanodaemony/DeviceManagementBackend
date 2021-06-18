package com.nano.msc.devicedata.base;

import com.nano.msc.common.vo.CommonResult;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Description: 基础数据服务接口
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:57
 */
public interface BaseDataService<T, ID> {

    CommonResult list(int page, int size);

    CommonResult save(T t);

    CommonResult save(T[] t);

    CommonResult delete(T t);

    CommonResult deleteById(ID id);

    CommonResult update(T t);

    /**
     * 通过采集场次号和序列号查询数据
     *
     * @param collectionNumber 采集场次号
     * @param serialNumber     序列号
     * @return 数据列表
     */
    List<T> getDeviceHistoryData(Integer collectionNumber, String serialNumber);

    /**
     * 通过采集场次号和序列号查询数据
     *
     * @param collectionNumber 采集场次号
     * @param serialNumber     序列号
     * @return 数据列表
     */
    Page<T> getDeviceHistoryData(Integer collectionNumber, String serialNumber, int page, int size);
}
