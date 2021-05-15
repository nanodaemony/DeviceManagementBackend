package com.nano.msc.info;

import com.nano.msc.collection.entity.InfoOperationMark;
import com.nano.msc.collection.service.InfoOperationMarkService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description:
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class InfoOperationMarkTest {

    @Autowired
    private InfoOperationMarkService operationMarkService;

    @Test
    public void testInfoOperation() {

        InfoOperationMark operationMark = new InfoOperationMark();

    }

}
