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
    StackTraceElement[] stes = Thread.currentThread().getStackTrace();
    /*
     * 0 = {StackTraceElement@8226} "dalvik.system.VMStack.getThreadStackTrace(Native Method)"
     * 1 = {StackTraceElement@8227} "java.lang.Thread.getStackTrace(Thread.java:1720)"
     * 2 = {StackTraceElement@8228} "com.yan.burial.method.timer.BurialTimer.timer(BurialTimer.java:22)"
     * 3 = {StackTraceElement@8223} "current execute"
     *
     * 所以我们需要栈信息里的第4个
     */
    if (stes.length > 3) {
      StackTraceElement ste = stes[3];
      Listener listener = getTimer().listener;
      if (listener != null) {
        listener.timer(null, clazz.getName(), ste.getClassName() + "#" + ste.getMethodName(), des,
            cost);
      }
    }
  }
}

