package com.pmbp.pmbpime.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class SwipeLayout extends ViewGroup {

    private final Paint mPaint;
    private int mSelect = -1;

    private boolean dispatchTouchEvent;

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(
                    MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) / getChildCount(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.AT_MOST));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = (r - l) / getChildCount();
        int viewHeight = b - t;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            child.layout(width * i, (viewHeight - height) / 2, width * i + width, (viewHeight + height) / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mSelect != -1) {
            float width = (float) getWidth() / (float) getChildCount();
            float height = getHeight();
            mPaint.setColor(0x800000ff);
            canvas.drawRect(width * mSelect, 0, width * mSelect + width, height, mPaint);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        postInvalidate();
        if (!isEnabled()) {
            mSelect = -1;
            return false;
        }

        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            if (mSelect != -1) {
                if (!dispatchTouchEvent) {
                    getChildAt(mSelect).performClick();
                } else {
                    event.setAction(MotionEvent.ACTION_UP);
                    getChildAt(mSelect).dispatchTouchEvent(event);
                }
            }
            mSelect = -1;
        } else {
            float x = event.getX();

            float width = (float) getWidth() / (float) getChildCount();
            int select = (int) Math.floor(x / width);
            if (select < 0) select = 0;
            if (select >= getChildCount()) select = getChildCount() - 1;

            if (action == MotionEvent.ACTION_DOWN) {
                mSelect = select;
                if (dispatchTouchEvent) {
                    getChildAt(mSelect).dispatchTouchEvent(event);
                }
            } else {
                if (dispatchTouchEvent) {
                    if (mSelect != select) {
                        event.setAction(MotionEvent.ACTION_CANCEL);
                        getChildAt(mSelect).dispatchTouchEvent(event);
                        event.setAction(MotionEvent.ACTION_DOWN);
                        getChildAt(select).dispatchTouchEvent(event);
                    } else {
                        getChildAt(mSelect).dispatchTouchEvent(event);
                    }
                }
                mSelect = select;
            }

        }

        return true;
    }

    public void setDispatchTouchEvent(boolean dispatchTouchEvent) {
        this.dispatchTouchEvent = dispatchTouchEvent;
    }
}
