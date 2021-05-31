package com.nano.msc.collection.repository;

import com.nano.msc.collection.entity.InfoDeviceMaintenanceRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: 仪器维修记录
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:03
 */
@Repository
public interface InfoDeviceMaintenanceRecordRepository extends JpaRepository<InfoDeviceMaintenanceRecord, Integer> {

}
