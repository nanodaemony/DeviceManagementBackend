package com.nano.msc.system;

import com.nano.msc.common.utils.TimestampUtils;
import com.nano.msc.system.log.service.SystemLogService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Description:
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/1/22 16:50
 */
public class LogTest {

    @Autowired
    private SystemLogService logService;


    @Test
    public void test() {
        Scanner scanner = new Scanner(System.in);
        String data = scanner.next();
        String[] values = data.split(" ");
        int res = 0;
        for(String id : values) {
            if (id.contains("7") || Integer.parseInt(id) % 7 == 0) {
                res++;
            }
        }
        System.out.println(res);
    }



}
