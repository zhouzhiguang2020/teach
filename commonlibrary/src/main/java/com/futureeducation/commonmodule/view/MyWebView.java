package com.futureeducation.commonmodule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class MyWebView extends WebView {
    public float oldY;
    public float oldX;
    public float newY;
    public float newX;
    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        getParent().getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                newX = ev.getX();
                newY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                oldX = newX;
                oldY = newY;
                newX = ev.getX();
                newY = ev.getY();
                float moveX = Math.abs(oldX - newX);
                float moveY = Math.abs(oldY - newY);
                if (moveX * 1.73 < moveY) {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        invalidate();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
