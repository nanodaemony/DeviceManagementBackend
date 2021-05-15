package com.nano.msc.collection.service.impl;

import com.nano.msc.collection.entity.MarkEvent;
import com.nano.msc.collection.param.ParamPad;
import com.nano.msc.collection.repository.MarkEventRepository;
import com.nano.msc.collection.service.MarkEventService;
import com.nano.msc.common.service.BaseServiceImpl;
import com.nano.msc.common.vo.CommonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Description: 标记事件服务实现类(主要是给Pad做事件标记)
 * Usage:
 * @see #getOftenUseMarkEventList() 获取常用标记事件列表
 * @see #searchMarkEventList(ParamPad) 根据特定的关键字搜索事件,并根据使用频率排序
 * @see #addCustomizeMarkEvent(MarkEvent) 新增自定义手术事件
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/23 22:54
 */
@Slf4j
@Service
public class MarkEventServiceImpl extends BaseServiceImpl<MarkEvent, Integer> implements MarkEventService {

    @Autowired
    private MarkEventRepository markEventRepository;

    /**
     * 获取常用标记事件列表
     */
    @Override
    public CommonResult<List<MarkEvent>> getOftenUseMarkEventList() {
        // 1表示经常使用
        return CommonResult.success(markEventRepository.findByOftenUseOrderByMarkFrequencyDesc(1));
    }


    /**
     * 通过关键字查询匹配的事件列表
     * @param paramPad 关键字
     * @return 事件列表
     */
    @Override
    public CommonResult<List<MarkEvent>> searchMarkEventList(ParamPad paramPad) {
        String keyWord = paramPad.getData();
        if (keyWord == null || keyWord.length() == 0) {
            return CommonResult.success(new ArrayList<>());
        }
        // 一定要加%进行模糊查询!!!!!
        List<MarkEvent> matchList = markEventRepository.findByMarkEventLike("%" + keyWord + "%");
        return CommonResult.success(matchList);
    }

    /**
     * 增加自定义的标记事件
     * @return 是否成功
     */
    @Override
    public CommonResult<String> addCustomizeMarkEvent(MarkEvent markEvent) {

        if (markEvent == null || markEvent.getMarkMainType() == null
                || markEvent.getMarkSubType() == null || markEvent.getMarkEvent() == null) {
            return CommonResult.failed("自定义事件添加失败,信息不完整.");
        }

        MarkEvent event = markEventRepository.findByMarkMainTypeAndMarkSubTypeAndMarkEvent(markEvent.getMarkMainType(), markEvent.getMarkSubType(), markEvent.getMarkEvent());
        if (event != null) {
            return CommonResult.failed("标记信息已经存在.");
        }
        // 表示是新增的事件
        markEvent.setWhetherNewAdd(1);
        // 进行持久化存储
        save(markEvent);
        return CommonResult.success();
    }


    @Override
    protected JpaRepository<MarkEvent, Integer> initRepository() {
        return markEventRepository;
    }
}
