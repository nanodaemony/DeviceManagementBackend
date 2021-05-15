package com.nano.msc.collection.service;

import com.nano.msc.collection.entity.MarkEvent;
import com.nano.msc.collection.param.ParamPad;
import com.nano.msc.common.service.BaseService;
import com.nano.msc.common.vo.CommonResult;

import java.util.List;

/**
 * Description: 标记事件服务接口
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 22:53
 */
public interface MarkEventService extends BaseService<MarkEvent, Integer> {


    /**
     * 获取常用标记事件列表
     */
    CommonResult<List<MarkEvent>> getOftenUseMarkEventList();

    /**
     * 通过关键字查询匹配的事件列表
     * @param paramPad 关键字
     * @return 事件列表
     */
    CommonResult<List<MarkEvent>> searchMarkEventList(ParamPad paramPad);

    /**
     * 增加自定义的标记事件
     * @return 是否成功
     */
    CommonResult<String> addCustomizeMarkEvent(MarkEvent markEvent);
}
