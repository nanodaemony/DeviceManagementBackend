package com.nano.msc.common.service;

import com.nano.msc.common.vo.CommonResult;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 基础服务类，定义了一些CRUD基本操作
 *
 * @author nano
 * @version V1.0
 * @date 2019/11/27
 * Description: 其他服务类继承这个抽象类可以实现一些通用的服务
 */
public abstract class BaseServiceImpl<T, ID> implements BaseService<T, ID> {

    /**
     * 抽象方法(实现类需要传入自己的JpaRepository)
     */
    protected abstract JpaRepository<T, ID> initRepository();

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

}
