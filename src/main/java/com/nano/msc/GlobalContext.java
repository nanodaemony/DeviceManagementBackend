package com.nano.msc;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

/**
 * Description: 全局静态资源
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/6/1 12:06
 */
@Data
public class GlobalContext {

    public static Set<Integer> deviceCodeSet = new HashSet<>();
    static {

        int[] codes = {30, 31, 32, 33, 34, 35, 36,
        42, 43, 44, 45, 46, 47, 48,
        71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82};
        for (int c : codes) {
            deviceCodeSet.add(c);
        }
    }


}
