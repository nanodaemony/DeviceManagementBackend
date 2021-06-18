package com.nano.msc.collection;

import com.nano.msc.collection.entity.InfoOperation;
import com.nano.msc.collection.service.InfoOperationService;

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
public class InfoOperationTest {

    @Autowired
    private InfoOperationService operationService;

    @Test
    public void testInfoOperation() {
        InfoOperation operation = new InfoOperation();
        operation.setHospitalOperationNumber("AS123890");
        operation.setOperationAsaLevel(2);
        operation.setOperationIsUrgent(false);
        operation.setOperationKidneyFunctionLevel(2);
        operation.setOperationLiverFunctionLevel(3);
        operation.setOperationLungFunctionLevel(1);
        operation.setOperationHeartFunctionLevel(1);
        operationService.save(operation);
    }

}
