package com.nano.msc.system.log.service;


import com.nano.msc.system.log.entity.SystemLog;

import java.util.List;

/**
 * 系统日志服务接口
 * @author nano
 */
public interface SystemLogService {

    /**
     * SystemLog保存
     */
    void save(SystemLog systemLog);

    /**
     * 得到当天的日志
     *
     * @param page 页
     * @param size 每页条数
     * @return List<SystemLog>
     */
    List<SystemLog> listCurrentDay(int page, int size);

    /**
     * 得到当天的日志(同时满足日志的级别)
     *
     * @param page     页
     * @param size     每页条数
     * @param logLevel 日志级别
     * @return List<SystemLog>
     */
    List<SystemLog> listCurrentDayAndLogLevel(int page, int size, int logLevel);

    /**
     * 得到所有的日志
     *
     * @param page 页
     * @param size 每页条数
     * @return List<SystemLog>
     */
    List<SystemLog> list(int page, int size);

    void error(String errorLog);

    void error(String source, String errorLog);

    void info(String infoLog);

    void info(String source, String infoLog);
}
