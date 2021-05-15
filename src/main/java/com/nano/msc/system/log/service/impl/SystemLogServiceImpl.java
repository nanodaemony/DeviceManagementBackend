package com.nano.msc.system.log.service.impl;

import com.nano.msc.common.utils.TimestampUtils;
import com.nano.msc.system.log.entity.SystemLog;
import com.nano.msc.system.log.enums.SystemLogEnum;
import com.nano.msc.system.log.repository.SystemLogRepository;
import com.nano.msc.system.log.service.SystemLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统日志服务接口
 * @author nano
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {

    @Autowired
    private SystemLogRepository systemLogRepository;

    private final Logger logger = LoggerFactory.getLogger("LoggerService:");

    /**
     * 保存日志信息
     *
     * @param systemLog 系统日志
     */
    @Override
    public void save(SystemLog systemLog) {
        systemLogRepository.save(systemLog);
    }

    @Override
    public List<SystemLog> listCurrentDay(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return systemLogRepository.findByGmtCreateAfter(TimestampUtils.getCurrentDayZeroLocalDateTime(), pageable);
    }

    @Override
    public List<SystemLog> listCurrentDayAndLogLevel(int page, int size, int logLevel) {
        Pageable pageable = PageRequest.of(page, size);
        return systemLogRepository.findByGmtCreateAfterAndLogLevel(TimestampUtils.getCurrentDayZeroLocalDateTime(), logLevel, pageable);
    }

    /**
     * 列出全部日志
     * @param page 页
     * @param size 每页条数
     */
    @Override
    public List<SystemLog> list(int page, int size) {
        return systemLogRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    /**
     * 保存并打印错误日志
     *
     * @param errorLog 错误日志
     */
    @Override
    public void error(String errorLog) {
        logger.error(errorLog);
        systemLogRepository.save(new SystemLog(SystemLogEnum.ERROR.getCode(), SystemLogEnum.ERROR.getMsg(), errorLog, TimestampUtils.getCurrentTimeAsString()));
    }


    /**
     * 保存并打印错误日志
     *
     * @param errorLog 错误日志
     */
    @Override
    public void error(String source, String errorLog) {
        logger.error(errorLog);
        systemLogRepository.save(new SystemLog(SystemLogEnum.ERROR.getCode(), SystemLogEnum.ERROR.getMsg(), errorLog, TimestampUtils.getCurrentTimeAsString()));
    }


    /**
     * 保存并打印信息日志
     *
     * @param infoLog 信息日志
     */
    @Override
    public void info(String infoLog) {
        logger.info(infoLog);
        systemLogRepository.save(new SystemLog(SystemLogEnum.INFO.getCode(), SystemLogEnum.INFO.getMsg(), infoLog, TimestampUtils.getCurrentTimeAsString()));
    }

    /**
     * 保存并打印信息日志
     *
     * @param infoLog 信息日志
     */
    @Override
    public void info(String source, String infoLog) {
        logger.info(infoLog);
        systemLogRepository.save(new SystemLog(SystemLogEnum.INFO.getCode(), SystemLogEnum.INFO.getMsg(), infoLog, TimestampUtils.getCurrentTimeAsString()));
    }
}
