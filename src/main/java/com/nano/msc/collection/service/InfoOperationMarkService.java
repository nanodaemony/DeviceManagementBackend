package com.nano.msc.collection.service;

import com.nano.msc.collection.entity.InfoOperationMark;
import com.nano.msc.common.service.BaseService;
import com.nano.msc.common.vo.CommonResult;


/**
 * Description: 手术标记事件服务
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:17
 */
public interface InfoOperationMarkService extends BaseService<InfoOperationMark, Integer> {


    /**
     * 根据手术场次号查找标记事件
     * @param collectionNumber 手术场次号
     * @return 场次号列表
     */
    CommonResult getMarkEventListByCollectionNumber(Integer collectionNumber);


    /**
     * 保存从Pad上传的事件信息
     *
     * @param operationMark 事件信息
     * @return 是否成功
     */
    CommonResult<String> addMarkEvent(InfoOperationMark operationMark);


    /**
     * 更新从Pad上传的事件信息
     *
     * @param operationMark 事件信息
     * @return 是否成功
     */
    CommonResult<String> updateMarkEvent(InfoOperationMark operationMark);

    /**
     * 删除一个标记事件
     *
     * @param operationMark 标记事件
     * @return 是否成功
     */
    CommonResult<String> deleteEventByUniqueNumber(InfoOperationMark operationMark);


}
