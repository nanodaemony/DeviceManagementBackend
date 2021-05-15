package com.nano.msc.common.service;

import com.nano.msc.common.vo.CommonResult;

/**
 * Description: 基础服务接口
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:29
 */
public interface BaseService<T, ID> {

    public CommonResult list(int page, int size) ;

    public CommonResult save(T t) ;

    public CommonResult save(T[] t) ;

    public CommonResult delete(T t) ;

    public CommonResult deleteById(ID id) ;

    public CommonResult update(T t) ;

}
