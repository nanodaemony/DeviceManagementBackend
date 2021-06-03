package com.nano.msc.collection.repository;

import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: 采集完成后进行的仪器评价表单实体(用于每次结束数据采集对仪器进行评价)
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:03
 */
@Repository
public interface InfoDeviceUsageEvaluationRepository extends JpaRepository<InfoDeviceUsageEvaluation, Integer> {


    /**
     * 通过采集场次号进行查询
     *
     * @param collectionNumber 采集场次号
     * @return 结果
     */
    InfoDeviceUsageEvaluation findByCollectionNumber(Integer collectionNumber);

    /**
     * 通过手术场次号和仪器号查找评价信息
     *
     * @param collectionNumber 手术场次号
     * @param deviceCode 仪器号
     * @return 评价信息
     */
    InfoDeviceUsageEvaluation findByCollectionNumberAndDeviceCode(Integer collectionNumber, Integer deviceCode);

    /**
     * 通过仪器号和序列号找
     *
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @return 信息
     */
    List<InfoDeviceUsageEvaluation> findByDeviceCodeAndSerialNumber(int deviceCode, String serialNumber, Pageable pageable);

    List<InfoDeviceUsageEvaluation> findByDeviceCodeAndSerialNumber(int deviceCode, String serialNumber);

    /**
     * 通过仪器号和序列号找
     *
     * @param deviceCode 仪器号
     * @param serialNumber 序列号
     * @param experienceLevel 使用体验度
     * @return 信息
     */
    List<InfoDeviceUsageEvaluation> findByDeviceCodeAndSerialNumberAndExperienceLevel(int deviceCode, String serialNumber, String experienceLevel);

    /**
     * 通过仪器号找全部信息
     *
     * @param deviceCode 仪器号
     * @return 评价信息
     */
    Page<InfoDeviceUsageEvaluation> findByDeviceCode(int deviceCode, Pageable pageable);


    List<InfoDeviceUsageEvaluation> findByDeviceCode(int deviceCode);
}
