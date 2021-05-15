package com.nano.msc.devicedata.base;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Description: 仪器数据通用的repository,一定要加@NoRepositoryBean注解
 *
 * 注意: 这里可以写不同Device Data Repository相关的公共方法.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 22:47
 */
@NoRepositoryBean
public interface DeviceDataRepository<T, ID> extends JpaRepository<T, ID> {

    /**
     * 通过采集场次号和序列号查询数据
     *
     * @param collectionNumber 采集场次号
     * @param serialNumber 序列号
     * @return 数据列表
     */
    List<T> findByCollectionNumberAndSerialNumber(Integer collectionNumber, String serialNumber);


    /**
     * 通过operationNumber和serialNumber查询指定手术的仪器输出数据
     *
     * @param collectionNumber 手术顺序号
     * @param serialNumber    仪器序列号
     * @param pageable        分页信息
     * @return Page<T> 数据
     */
    Page<T> findByCollectionNumberAndSerialNumber(Integer collectionNumber, String serialNumber, Pageable pageable);

}
