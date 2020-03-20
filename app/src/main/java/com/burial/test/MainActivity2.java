package com.burial.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import com.yan.burial.method.timer.BurialTimer;

public class MainActivity2 extends Activity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    BurialTimer.getTimer().setListener(
        (ignore, className, methodName, des, cost) -> Log.e("BurialTimer",
            className + "  " + methodName + "  " + des + "  " + des));
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState,
      @Nullable PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
  }

  @Override protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
  }

  @Override public void onPostCreate(@Nullable Bundle savedInstanceState,
      @Nullable PersistableBundle persistentState) {
    super.onPostCreate(savedInstanceState, persistentState);
  }

  @Override protected void onStart() {
    super.onStart();
  }

  @Override protected void onPostResume() {
    super.onPostResume();
  }

  @Override protected void onResume() {
    super.onResume();
  }
}