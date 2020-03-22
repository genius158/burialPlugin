package com.yan.burial.method.timer;

import android.os.Looper;

public class BurialTimer {
  private volatile static BurialTimer timer;
  private Listener listener;

  public static BurialTimer getTimer() {
    if (timer == null) {
      synchronized (BurialTimer.class) {
        if (timer == null) timer = new BurialTimer();
      }
    }
    return timer;
  }

  public void setListener(Listener listener) {
    this.listener = listener;
  }

  public static void timer(Class clazz, String method, String des, long startTime) {
    /*非主线程不统计*/
    if (Looper.getMainLooper().getThread() != Thread.currentThread()) return;
    long cost = System.currentTimeMillis() - startTime;

    Listener listener = getTimer().listener;
    String clazzName = clazz.getName();
    if (method != null) {
      if (listener != null) {
        listener.timer(null, clazzName, clazzName + "#" + method, des, cost);
      }
      return;
    }

    StackTraceElement[] sates = new Throwable().getStackTrace();
    if (sates.length > 1) {
      StackTraceElement ste = sates[1];
      if (listener != null) {
        listener.timer(null, clazzName, ste.getClassName() + "#" + ste.getMethodName(), des,
            cost);
      }
    }
  }
}

