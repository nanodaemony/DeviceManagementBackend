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


    /**
     * 根据仪器号查询维修记录
     *
     * @param deviceCode 仪器号
     * @return 列表
     */
    List<InfoDeviceMaintenanceRecord> findByDeviceCode(int deviceCode);

    /**
     * 根据仪器号及序列号查询维修记录
     *
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @return 列表
     */
    List<InfoDeviceMaintenanceRecord> findByDeviceCodeAndSerialNumber(int deviceCode, String serialNumber);

}
