package com.nano.msc.collection;

import com.nano.msc.collection.entity.InfoDeviceDataCollection;
import com.nano.msc.collection.entity.InfoDeviceUsageEvaluation;
import com.nano.msc.collection.repository.InfoDeviceDataCollectionRepository;
import com.nano.msc.collection.repository.InfoDeviceUsageEvaluationRepository;
import com.nano.msc.devicedata.entity.ethernet.DataNuoHe9002s;
import com.nano.msc.devicedata.repository.NuoHe9002sRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Description:
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 21:20
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class InfoTest {

    @Autowired
    private InfoDeviceDataCollectionRepository dataCollectionRepository;

    @Autowired
    private InfoDeviceUsageEvaluationRepository usageEvaluationRepository;

    @Autowired
    private NuoHe9002sRepository nuoHe9002sRepository;

    @Test
    public void testInfoDeviceDataCollection() {
        List<InfoDeviceDataCollection> dataCollectionList = dataCollectionRepository.findAll();
        for (InfoDeviceDataCollection collection : dataCollectionList) {
            System.out.println(collection.toString());
        }
    }

    @Test
    public void testInfoDeviceUsageEvaluation() {
        List<InfoDeviceUsageEvaluation> usageEvaluationList = usageEvaluationRepository.findAll();
        for (InfoDeviceUsageEvaluation evaluation : usageEvaluationList) {

            if ("0".equals(evaluation.getKnownError())) {
                evaluation.setKnownError("");
                usageEvaluationRepository.save(evaluation);
            }

        }
    }


    @Test
    public void testNuoHeDataClean() {

        List<InfoDeviceDataCollection> dataCollectionList = dataCollectionRepository.findAll();
        for (InfoDeviceDataCollection collection : dataCollectionList) {

            if (usageEvaluationRepository.findByCollectionNumber(collection.getCollectionNumber()) == null) {
                System.out.println(collection.toString());

                InfoDeviceUsageEvaluation evaluation = new InfoDeviceUsageEvaluation();
                evaluation.setUniqueNumber("" + System.currentTimeMillis());
                evaluation.setCollectionNumber(collection.getCollectionNumber());
                evaluation.setDeviceCode(collection.getDeviceCode());
                evaluation.setSerialNumber(collection.getSerialNumber());
                evaluation.setDeviceDepartment("主病房8手术间");
                evaluation.setRecordName("方亮");
                evaluation.setReliabilityLevel(5);
                evaluation.setExperienceLevel(5);
                evaluation.setKnownError("");
                evaluation.setHasError(false);
                evaluation.setOtherError("");
                evaluation.setRemarkInfo("无");
                usageEvaluationRepository.save(evaluation);
            }

        }
    }



    @Test
    public void add() {

    }

}
