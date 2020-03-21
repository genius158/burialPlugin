package com.burial.test;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

public class TestView extends FrameLayout {
  public TestView(Context context) {
    this(context, null);
  }

  public TestView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
    test2();
  }

  public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    test3(null, null);
  }

  @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }

  Presenter2 p1 = new Presenter2();

  void test1() {
    p1.forClass();
    Presenter2.forClass(p1);
  }

  void test2() {
    Log.e("test2", new Throwable().getStackTrace()[0].getMethodName());
    new Presenter2().forClass();

    Presenter2.forClass(p1);
  }

  void test3(Integer iin, Long l) {
    //new Throwable().getStackTrace();
    //Thread.currentThread().getStackTrace();
  }
}
