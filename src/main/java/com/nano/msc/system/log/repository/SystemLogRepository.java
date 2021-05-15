package com.nano.msc.system.log.repository;

import com.nano.msc.system.log.entity.SystemLog;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统日志DAO层
 * @author nano
 */
@Repository
public interface SystemLogRepository extends JpaRepository<SystemLog, Integer> {

    /**
     * 通过gmtCreate来找到创建时间是localDateTime之后的数据
     *
     * @param localDateTime 输入的时间
     * @param pageable      分页信息
     * @return List<SystemLog>
     */
    List<SystemLog> findByGmtCreateAfter(LocalDateTime localDateTime, Pageable pageable);

    /**
     * 通过gmtCreate来找到创建时间是localDateTime之后的数据
     * 同时需要满足日志级别在指定的级别
     *
     * @param localDateTime 时间
     * @param logLevel      日志级别
     * @param pageable      分页信息
     * @return List<SystemLog>
     */
    List<SystemLog> findByGmtCreateAfterAndLogLevel(LocalDateTime localDateTime, Integer logLevel, Pageable pageable);
}
