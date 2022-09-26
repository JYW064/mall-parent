package com.jyw.portal.util;

import java.util.Random;

/**
 * @program: mall-parent
 * @description:
 * @author: ji yi wei
 * @create: 2022-04-14
 */
public class OrderIdUtil {

    /**
     * 随机id生成
     */
    public static String getRandomId() {
        long millis = System.currentTimeMillis();
        //加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);
        //如果不足两位前面补0
        String str = millis + String.format("%02d", end2);
        return str;
    }
}