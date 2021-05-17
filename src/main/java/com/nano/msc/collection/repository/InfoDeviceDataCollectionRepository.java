package com.nano.msc.collection.repository;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description: 仪器数据采集
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:05
 */
@Repository
public interface InfoDeviceDataCollectionRepository extends JpaRepository<InfoDeviceDataCollection, Integer>  {

    /**
     * 通过采集场次号进行查询
     *
     * @param collectionNumber 采集场次号
     * @return 结果
     */
    InfoDeviceDataCollection findByCollectionNumber(Integer collectionNumber);

    /**
     * 找到某一天的记录
     *
     * @param localDateTimeAfter 当天开始时间戳
     * @param localDateTimeBefore 当天结束时间戳
     * @return 仪器使用记录
     */
    List<InfoDeviceDataCollection> findByGmtCreateAfterAndGmtCreateBefore(LocalDateTime localDateTimeAfter, LocalDateTime localDateTimeBefore);


    /**
     * 找到某时间之后的记录
     *
     * @param localDateTimeAfter 当天开始时间戳
     * @return 仪器使用记录
     */
    List<InfoDeviceDataCollection> findByGmtCreateAfter(LocalDateTime localDateTimeAfter);


    /**
     * 找到某时间之后的记录
     *
     * @param localDateTimeAfter 当天开始时间戳
     * @param collectionState 采集状态
     * @return 仪器使用记录
     */
    List<InfoDeviceDataCollection> findByCollectionStatusAndGmtCreateAfter(Integer collectionState, LocalDateTime localDateTimeAfter);

    /**
     * 通过仪器号和序列号找手术使用仪器信息
     *
     * @param deviceInfoId 仪器信息ID
     * @return 手术使用仪器信息
     */
    List<InfoDeviceDataCollection> findByMedicalDeviceId(Integer deviceInfoId);

    /**
     * 获取当前正在进行的手术信息列表
     * @param operationStatus 手术状态
     *
     * @return 信息列表
     */
    List<InfoDeviceDataCollection> findByCollectionStatusOrderByCollectionNumberDesc(Integer operationStatus);


    /**
     * 通过手术场次号找使用的仪器
     *
     * @param collectionNumber 手术场次号
     * @param deviceCode 仪器号
     * @return 仪器信息
     */
    InfoDeviceDataCollection findByCollectionNumberAndDeviceCode(int collectionNumber, String deviceCode);


    /**
     * 通过仪器号找手术使用的仪器信息
     *
     * @param deviceCode 仪器号
     * @return 手术仪器信息
     */
    List<InfoDeviceDataCollection> findByDeviceCode(String deviceCode);


    /**
     * 分页获取最新的采集信息
     *
     * @param of 分页查询
     * @return 结果
     */
    @Query("select e from InfoDeviceDataCollection e where e.collectionStatus=?1 ORDER BY e.collectionNumber DESC")
    Page<InfoDeviceDataCollection> findFinishedCollectionListDesc(int collectionStatus, PageRequest of);


    /**
     * 通过采集器唯一ID以及采集状态查询采集信息,这里可能因为一些错误原因导致会有多个记录
     *
     * @param collectorUniqueId 串口采集器唯一ID
     * @param collectionStatus 采集状态
     * @return 采集信息
     */
    List<InfoDeviceDataCollection> findByCollectorUniqueIdAndCollectionStatus(String collectorUniqueId, int collectionStatus);

    /**
     * 根据采集状态查询采集信息
     * @param collectionStatus 采集状态
     * @return 采集信息
     */
    List<InfoDeviceDataCollection> findByCollectionStatus(int collectionStatus);

}
