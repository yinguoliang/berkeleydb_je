package com.test;

import java.util.Random;

public class Utils {
    private static Random random = new Random();

    public static void checkBytes(byte[] bytes) {
        String s = null;
        try {
            s = new String(bytes);

        } catch (Exception ex) {
            s = null;
        }
    }

    public static int randomNumber(int min, int max) {
        if (min >= max) {
            return 0;
        }
        int delta = max - min;
        //生成[0,delta)之间一个随机数
        int r = random.nextInt(delta);
        return r + min;
    }
}
