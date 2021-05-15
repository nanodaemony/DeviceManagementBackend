package com.nano.msc.collection.service.impl;

import com.nano.msc.collection.entity.InfoOperation;
import com.nano.msc.collection.repository.InfoOperationRepository;
import com.nano.msc.collection.service.InfoOperationService;
import com.nano.msc.common.service.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Description: 手术信息服务实现类
 * Usage:
 * 1. 实现对手术相关信息的增删改查
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:18
 */
@Slf4j
@Service
public class InfoOperationServiceImpl extends BaseServiceImpl<InfoOperation, Integer> implements InfoOperationService {

    @Autowired
    private InfoOperationRepository operationRepository;


    @Override
    protected JpaRepository initRepository() {
        return operationRepository;
    }
}
