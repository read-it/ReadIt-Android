package com.computer.inu.readit_appjam

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class MainViewPager(ctx: Context, attr: AttributeSet) : ViewPager(ctx, attr) {

    // 스와이프에 따른 ViewPager의 이동을 막는다.
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}