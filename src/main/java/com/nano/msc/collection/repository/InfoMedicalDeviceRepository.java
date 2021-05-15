package com.nano.msc.collection.repository;

import com.nano.msc.collection.entity.InfoMedicalDevice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: 医疗仪器信息
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:07
 */
@Repository
public interface InfoMedicalDeviceRepository extends JpaRepository<InfoMedicalDevice, Integer> {

    /**
     * 通过仪器号与序列号查找仪器
     *
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @return 仪器信息
     */
    InfoMedicalDevice findByDeviceCodeAndSerialNumber(Integer deviceCode, String serialNumber);

    /**
     * 通过仪器号查询仪器信息列表
     *
     * @param deviceCode 仪器号
     * @return 仪器信息列表
     */
    List<InfoMedicalDevice> findByDeviceCode(Integer deviceCode);

    /**
     * 通过采集器唯一ID查询仪器信息
     *
     * @param collectorUniqueId 采集器唯一ID
     * @return 当前采集器采集的仪器信息
     */
    InfoMedicalDevice findByCollectorUniqueId(String collectorUniqueId);
}
