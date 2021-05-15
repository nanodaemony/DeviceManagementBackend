package com.nano.msc.collection.service.impl;

import com.nano.msc.collection.entity.InfoOperationMark;
import com.nano.msc.collection.repository.InfoOperationMarkRepository;
import com.nano.msc.collection.repository.MarkEventRepository;
import com.nano.msc.collection.service.InfoOperationMarkService;
import com.nano.msc.common.service.BaseServiceImpl;
import com.nano.msc.common.vo.CommonResult;
import com.nano.msc.system.log.service.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Description: 手术标记信息服务实现类
 * Usage:
 * @see #getMarkEventListByCollectionNumber(Integer) 根据采集场次号查找对应的标记事件
 * @see #addMarkEvent(InfoOperationMark) 新增一个手术标记事件
 * @see #updateMarkEvent(InfoOperationMark) 更新一个手术标记事件
 * @see #deleteEventByUniqueNumber(InfoOperationMark) 根据UniqueNumber删除一个标记事件
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:43
 */
@Slf4j
@Service
public class InfoOperationMarkServiceImpl extends BaseServiceImpl<InfoOperationMark, Integer> implements InfoOperationMarkService {

    @Autowired
    private InfoOperationMarkRepository operationMarkRepository;

    @Autowired
    private MarkEventRepository markEventRepository;

    @Autowired
    private SystemLogService logService;


    /**
     * 根据采集场次号查找标记事件
     *
     * @param collectionNumber 采集场次号
     * @return 标记列表
     */
    @Override
    public CommonResult getMarkEventListByCollectionNumber(Integer collectionNumber) {
        // TODO:
        return null;
    }


    /**
     * 保存从Pad上传的事件信息
     *
     * @param operationMark 事件信息
     * @return 是否成功
     */
    @Override
    public CommonResult<String> addMarkEvent(InfoOperationMark operationMark) {
        operationMarkRepository.save(operationMark);
        // 需要返回UniqueNumber
        return CommonResult.success(operationMark.getUniqueNumber());
    }

    /**
     * 更新从Pad上传的事件信息
     *
     * @param operationMark 事件信息
     * @return 是否成功
     */
    @Override
    public CommonResult<String> updateMarkEvent(InfoOperationMark operationMark) {

        InfoOperationMark historyMark = operationMarkRepository.findByUniqueNumber(operationMark.getUniqueNumber());
        if (historyMark == null) {
            return CommonResult.failed("UniqueNumber Not Exist.");
        }
        historyMark.setMarkTime(operationMark.getMarkTime());
        operationMarkRepository.save(historyMark);
        return CommonResult.success(operationMark.getUniqueNumber());
    }

    /**
     * 删除一个标记事件
     *
     * @param operationMark 标记事件
     * @return 是否成功
     */
    @Override
    public CommonResult<String> deleteEventByUniqueNumber(InfoOperationMark operationMark) {

        if (operationMark.getUniqueNumber() == null || operationMark.getUniqueNumber().length() == 0 ){
            return CommonResult.failed("UniqueNumber invalid.");
        }
        InfoOperationMark mark = operationMarkRepository.findByUniqueNumber(operationMark.getUniqueNumber());
        delete(mark);
        logService.info("删除手术标记:" + mark.toString());
        return CommonResult.success(operationMark.getUniqueNumber());
    }


    @Override
    protected JpaRepository<InfoOperationMark, Integer> initRepository() {
        return operationMarkRepository;
    }


}
