package com.yan.burial.method.timer;

public interface Listener {

  /**
   * @param ignore 插件寻找方法使用，值为空 应对Lambda 表达式
   */
  void timer(BurialTimer ignore, String className, String methodName, String des, long cost);

  void onTimerEntry(BurialTimer ignore, String className, String methodName, String des);
}

