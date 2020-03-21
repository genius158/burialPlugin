package com.yan.burial;

import com.android.build.gradle.internal.LoggerWrapper;

public class BurialLog {
  static LoggerWrapper logger = LoggerWrapper.getLogger(BurialLog.class);

  static boolean logEnable = true;

  static void info(String msg) {
    if (logEnable) {
      logger.info("BurialPlugin: " + msg);
    }
  }
}
