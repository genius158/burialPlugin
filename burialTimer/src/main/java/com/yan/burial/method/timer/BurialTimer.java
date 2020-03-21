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

  public static void timer(Class clazz, String des, long startTime) {
    /*非主线程不统计*/
    if (Looper.getMainLooper().getThread() != Thread.currentThread()) return;

    long cost = System.currentTimeMillis() - startTime;
    StackTraceElement[] sates = new Throwable().getStackTrace();

    if (sates.length > 1) {
      StackTraceElement ste = sates[1];
      Listener listener = getTimer().listener;
      if (listener != null) {
        listener.timer(null, clazz.getName(), ste.getClassName() + "#" + ste.getMethodName(), des, cost);
      }
    }
  }
}

