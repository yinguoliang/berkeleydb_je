package com.test;

public class Utils {
    public static void checkBytes(byte[] bytes) {
        String s = null;
        try {
            s = new String(bytes);

        } catch (Exception ex) {
            s = null;
        }
    }
}
