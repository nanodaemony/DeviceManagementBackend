package com.nano.msc.collection.repository;

import com.nano.msc.collection.entity.InfoOperationMark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Description: 手术标记信息
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:08
 */
@Repository
public interface InfoOperationMarkRepository extends JpaRepository<InfoOperationMark, Integer> {


    /**
     * 通过唯一序号查找
     *
     * @param uniqueNumber 唯一序号
     * @return 标记信息
     */
    InfoOperationMark findByUniqueNumber(String uniqueNumber);

    /**
     * 通过序号删除
     * @param uniqueNumber 唯一序号
     */
    void deleteByUniqueNumber(String uniqueNumber);

}
