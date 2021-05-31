package com.nano.msc.devicedata.base;

import com.nano.msc.common.service.BaseServiceUtils;
import com.nano.msc.common.vo.CommonResult;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Description: 基础数据服务实现类
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:59
 */
public abstract class BaseDataServiceImpl<T, ID> implements BaseDataService<T, ID>{

    /**
     * 抽象方法(实现类需要传入自己的JpaRepository)
     */
    protected abstract DeviceDataRepository<T, ID> initRepository();

    @Override
    public CommonResult list(int page, int size) {
        return BaseServiceUtils.listObjectAndCheck(initRepository(), page, size);
    }

    @Override
    public CommonResult save(T t) {
        return BaseServiceUtils.saveObjectAndCheck(initRepository(), t);
    }

    @Override
    public CommonResult save(T[] t) {
        return BaseServiceUtils.saveObjectAndCheck(initRepository(), t);
    }

    @Override
    public CommonResult delete(T t) {
        return BaseServiceUtils.deleteObjectAndCheck(initRepository(), t);
    }

    @Override
    public CommonResult deleteById(ID id) {
        return BaseServiceUtils.deleteObjectAndCheck(initRepository(), id);
    }

    @Override
    public CommonResult update(T t) {
        return BaseServiceUtils.updateObjectAndCheck(initRepository(), t);
    }

    /**
     * 通过采集场次号和序列号查询数据(全部查询)
     *
     * @param collectionNumber 采集场次号
     * @param serialNumber 序列号
     * @return 数据列表
     */
    @Override
    public List<T> getDeviceHistoryData(Integer collectionNumber, String serialNumber) {
        return initRepository().findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber);
    }


    /**
     * 通过采集场次号和序列号查询数据(分页查询)
     *
     * @param collectionNumber 采集场次号
     * @param serialNumber 序列号
     * @return 数据列表
     */
    @Override
    public Page<T> getDeviceHistoryData(Integer collectionNumber, String serialNumber, int page, int size) {
        return initRepository().findByCollectionNumberAndSerialNumber(collectionNumber, serialNumber, PageRequest.of(page, size));
    }


}
