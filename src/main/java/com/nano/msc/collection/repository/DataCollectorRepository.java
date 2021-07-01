package com.nano.msc.collection.repository;

import com.nano.msc.collection.entity.DataCollector;
import com.nano.msc.collection.entity.SuggestionAndBug;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Description: 数据采集器
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:07
 */
@Repository
public interface DataCollectorRepository extends JpaRepository<DataCollector, Integer> {



}
