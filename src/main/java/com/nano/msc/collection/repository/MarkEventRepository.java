package com.nano.msc.collection.repository;

import com.nano.msc.collection.entity.MarkEvent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Description: 标记事件的仓库
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/2 16:11
 */
@Repository
public interface MarkEventRepository extends JpaRepository<MarkEvent, Integer> {


    /**
     * 查询经常使用的标记信息列表并按照使用频率排序
     *
     * @param isOftenUse 是否经常使用 1:经常使用
     * @return 常用手术事件列表
     */
    List<MarkEvent> findByOftenUseOrderByMarkFrequencyDesc(Integer isOftenUse);


    /**
     * 通过给定的关键字模糊查询
     * @param keyWord 关键字
     * @return 结果
     */
    List<MarkEvent> findByMarkEventLike(String keyWord);


    /**
     * 通过信息找数据库中存在的这个标记事件
     *
     * @param markMainType 大类
     * @param markSubType 子类
     * @param markEvent 事件
     * @return 返回信息
     */
    MarkEvent findByMarkMainTypeAndMarkSubTypeAndMarkEvent(String markMainType, String markSubType, String markEvent);

}
