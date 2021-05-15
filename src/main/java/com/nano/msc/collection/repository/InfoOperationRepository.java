package com.nano.msc.collection.repository;

import com.nano.msc.collection.entity.InfoOperation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description: 手术信息
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:08
 */
@Repository
public interface InfoOperationRepository extends JpaRepository<InfoOperation, Integer> {

    /**
     * 分页获取最新的手术信息
     *
     * @param of 分页查询
     * @return 结果
     */
    @Query("select e from InfoOperation e ORDER BY e.operationNumber DESC")
    Page<InfoOperation> findByOperationNumberDesc(PageRequest of);





    /**
     * 通过手术场次号找手术信息
     *
     * @param operationNumber 手术场次号
     * @return 手术信息
     */
    InfoOperation findByOperationNumber(Integer operationNumber);


    /**
     * 找到某一天的记录
     *
     * @param localDateTimeAfter 当天开始时间戳
     * @param localDateTimeBefore 当天结束时间戳
     * @return 仪器使用记录
     */
    List<InfoOperation> findByGmtCreateAfterAndGmtCreateBefore(LocalDateTime localDateTimeAfter, LocalDateTime localDateTimeBefore);


    /**
     * 找到某时间之后的记录
     *
     * @param localDateTimeAfter 当天开始时间戳
     * @return 仪器使用记录
     */
    List<InfoOperation> findByGmtCreateAfter(LocalDateTime localDateTimeAfter);



}
