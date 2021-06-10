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
     * 通过ID与仪器号查询已有的仪器信息
     *
     * @param id 数据库ID号
     * @param deviceCode 仪器号
     * @return 仪器信息
     */
    InfoMedicalDevice findByIdAndDeviceCode(int id, int deviceCode);

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


    /**
     * 通过接口类型查询仪器
     *
     * @param interfaceType 接口类型, 1: 网口 2: 串口
     * @see com.nano.msc.collection.enums.InterfaceTypeEnum
     */
    List<InfoMedicalDevice> findByInterfaceType(Integer interfaceType);


}
