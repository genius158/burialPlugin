package com.yan.burial;

import com.android.build.gradle.internal.LoggerWrapper;

public class BurialLog {
  static private LoggerWrapper logger = LoggerWrapper.getLogger(BurialLog.class);

  static boolean logEnable = false;

  static void info(String msg) {
    if (logEnable) {
      logger.info(msg);
    }
  }
}
