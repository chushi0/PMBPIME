package com.pmbp.pmbpime.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class KeyboardLayout extends View {

    private Paint mPaint;

    public KeyboardLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(60);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        String[] s1 = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P","👈"};
        String[] s2 = {"A", "S", "D", "F", "G", "H", "J", "K", "L","︺"};
        String[] s3 = {"Z", "X", "C", "V", "B", "N", "M","◀"};
        drawArcButtons(canvas, width, height, s1, 0.9f);
        drawArcButtons(canvas, width, height, s2, 0.7f);
        drawArcButtons(canvas, width, height, s3, 0.5f);
    }

    private void drawArcButtons(Canvas canvas, int width, int height, String[] s2, float v) {
        canvas.save();
        canvas.translate(width, height);
        canvas.rotate(-90);
        canvas.rotate(90f / (s2.length + 1) / 2);
        for (String s : s2) {
            canvas.drawText(s, 0, -height * v, mPaint);
            canvas.rotate(90f / (s2.length + 1));
        }
        canvas.restore();
    }
}
