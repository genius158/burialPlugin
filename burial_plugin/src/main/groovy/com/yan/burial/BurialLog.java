package com.yan.burial;

public class BurialLog {

    static boolean logEnable = true;

    static void info(String msg) {
        if (logEnable) {
            System.out.println("BurialPlugin: " + msg);
        }
    }
}
