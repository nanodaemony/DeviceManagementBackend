package com.nano.msc.devicedata.repository;



import com.nano.msc.devicedata.base.DeviceDataRepository;
import com.nano.msc.devicedata.entity.ethernet.DataYiAn8700A;

import org.springframework.stereotype.Repository;


/**
 * 宜安8700A麻醉机
 * @author nano
 */
@Repository
public interface YiAn8700ARepository extends DeviceDataRepository<DataYiAn8700A, Integer> {

}
