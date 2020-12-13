package com.stu.syllabuskt.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * StuMainViewPager: 不可滑动的ViewPager，只为使用其预加载fragment的功能
 * Create by yuan on 2020/12/12
 */
public class StuMainViewPager extends ViewPager {

    public StuMainViewPager(@NonNull Context context) {
        super(context);
    }

    public StuMainViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOffscreenPageLimit(int limit) {
        super.setOffscreenPageLimit(2);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
