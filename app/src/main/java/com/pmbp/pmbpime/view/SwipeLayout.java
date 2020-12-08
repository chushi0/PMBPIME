package com.pmbp.pmbpime.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.pmbp.pmbpime.R;

public class SwipeLayout extends ViewGroup {

    private Paint mPaint;
    private int mSelect = -1;

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        setWillNotDraw(false);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = (r - l) / getChildCount();
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).layout(width * i, 0, width * i + width, b - t);
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
        if (!isEnabled()) {
            mSelect = -1;
            invalidate();
            return false;
        }

        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            if (mSelect != -1) {
                getChildAt(mSelect).performClick();
            }
            mSelect = -1;
        } else {
            float x = event.getX();

            float width = (float) getWidth() / (float) getChildCount();
            mSelect = (int) Math.floor(x / width);
            if (mSelect < 0) mSelect = 0;
            if (mSelect >= getChildCount()) mSelect = getChildCount() - 1;
        }
        invalidate();

        return true;
    }
}
