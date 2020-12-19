package com.pmbp.pmbpime.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Objects;

public class KeyboardLayout extends View {

    public static final long TICK_START = 500;
    public static final long TICK_PER = 50;

    private static final String[] s1 = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "←"};
    private static final String[] s2 = {"A", "S", "D", "F", "G", "H", "J", "K", "L", "︺"};
    private static final String[] s3 = {"Z", "X", "C", "V", "B", "N", "M", "👈"};

    private final Paint mPaint;
    private long downTick;

    private String lastType;

    private Listener listener;

    public KeyboardLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(60);
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

        drawArcButtons(canvas, width, height, s1, 0.9f);
        drawArcButtons(canvas, width, height, s2, 0.7f);
        drawArcButtons(canvas, width, height, s3, 0.5f);
    }

    private void drawArcButtons(Canvas canvas, int width, int height, String[] strings, float v) {
        canvas.save();
        canvas.translate(width, height);
        canvas.rotate(-90);
        canvas.rotate(90f / (strings.length + 1) / 2);
        for (String s : strings) {
            if (s.equals(lastType)) {
                mPaint.setColor(Color.LTGRAY);
                canvas.drawCircle(height * 0.03f, -height * (v + 0.03f), height * 0.05f, mPaint);
                mPaint.setColor(Color.BLACK);
            }
            canvas.drawText(s, 0, -height * v, mPaint);
            canvas.rotate(90f / (strings.length + 1));
        }
        canvas.restore();
    }

    @SuppressLint({"ClickableViewAccessibility", "DefaultLocale"})
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        postInvalidate();
        if (!isEnabled()) {
            lastType = null;
            return false;
        }

        int action = event.getAction();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            lastType = null;
        }
        if (action != MotionEvent.ACTION_DOWN && action != MotionEvent.ACTION_MOVE) {
            return true;
        }

        float width = getWidth();
        float height = getHeight();
        float x = event.getX() - width;
        float y = event.getY() - height;

        float radius = (float) (Math.sqrt(x * x + y * y) / height);
        float angle = (float) (Math.atan(Math.abs(y / x)) * 180 / Math.PI);

        String[] judge;
        switch ((int) Math.floor(radius / 0.2)) {
            case 2:
                judge = s3;
                break;
            case 3:
                judge = s2;
                break;
            case 4:
                judge = s1;
                break;
            default:
                return true;
        }

        float anglePer = 90f / (judge.length + 1);
        int index = Math.round(angle / anglePer) - 1;
        String type = null;
        if (index >= 0 && index < judge.length) {
            type = judge[index];
        }

        boolean tick = true;
        if (!Objects.equals(type, lastType)) {
            lastType = type;
            downTick = 0;
            tick = false;
        }

        long realTime = SystemClock.elapsedRealtime();
        boolean isDown = action == MotionEvent.ACTION_DOWN;
        if (isDown) {
            downTick = realTime + TICK_START;
        } else {
            if (realTime < downTick) return true;
            downTick = realTime + TICK_PER;
        }

        if (listener != null && type != null) {
            listener.onType(type, isDown, isDown && !tick);
        }

        return true;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onType(String string, boolean down, boolean tick);
    }
}
