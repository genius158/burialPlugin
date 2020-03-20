package com.yan.burial.method.timer;

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

  public static void timer(String className, String methodName, String des, long startTime) {
    long cost = System.currentTimeMillis() - startTime;
    Listener listener = getTimer().listener;
    if (listener != null) {
      listener.timer(null, className, methodName, des, cost);
    }
  }
}

