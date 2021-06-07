package com.yan.burial.method.timer;

import android.util.Log;

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since 2020/11/11
 */
public class DefaultListener implements Listener {
    @Override
    public void timer(BurialTimer ignore, String className, String methodName, String des, long cost) {
        if (cost > 16) {
            Log.e("BurialTimer", "cost:" + cost + "  " + className + "#" + methodName);
        }
    }

    @Override
    public void onTimerEntry(BurialTimer ignore, String className, String methodName, String des) {

    }
}
