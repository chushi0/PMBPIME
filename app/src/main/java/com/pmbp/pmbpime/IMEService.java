package com.pmbp.pmbpime;

import android.annotation.SuppressLint;
import android.inputmethodservice.InputMethodService;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pmbp.pmbpime.view.KeyboardLayout;

public class IMEService extends InputMethodService {

    private static final int VIBRATE_TIME = 50;

    private KeyboardLayout keyboardLayout;

    private Vibrator vibrator;

    private boolean capsLock;
    private View inputView;

    @SuppressLint({"ClickableViewAccessibility", "InflateParams"})
    @Override
    public void onCreate() {
        super.onCreate();

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        inputView = getLayoutInflater().inflate(R.layout.ime_main, null);
        View candidatesView = getLayoutInflater().inflate(R.layout.ime_candidates, null);

        setInputView(inputView);
        setCandidatesView(candidatesView);

        inputView.findViewById(R.id.layout_2).setVisibility(View.INVISIBLE);
        inputView.findViewById(R.id.layout_1).setEnabled(false);

        setupListener(inputView, R.id.qwe);
        setupListener(inputView, R.id.rtyu);
        setupListener(inputView, R.id.iop);
        setupListener(inputView, R.id.asd);
        setupListener(inputView, R.id.fgh);
        setupListener(inputView, R.id.jkl);
        setupListener(inputView, R.id.zxc);
        setupListener(inputView, R.id.vbn);
        setupListener(inputView, R.id.m);
        inputView.findViewById(R.id.space).setOnClickListener(v -> {
            vibrator.vibrate(VIBRATE_TIME);
            sendKeyChar(' ');
        });
        inputView.findViewById(R.id.backspace).setOnClickListener(v -> {
            vibrator.vibrate(VIBRATE_TIME);
            sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
        });
        inputView.findViewById(R.id.switch_single).setOnClickListener(v -> {
            vibrator.vibrate(VIBRATE_TIME);
            inputView.findViewById(R.id.layout_2).setVisibility(View.VISIBLE);
            inputView.findViewById(R.id.layout_1).setVisibility(View.INVISIBLE);
            inputView.findViewById(R.id.layout_2).setEnabled(true);
            inputView.findViewById(R.id.layout_1).setEnabled(false);
        });
        inputView.findViewById(R.id.capsLock).setOnClickListener(v -> {
            vibrator.vibrate(VIBRATE_TIME);
            revCapsLock();
        });
        TouchTickAction.setupView(inputView.findViewById(R.id.space));
        TouchTickAction.setupView(inputView.findViewById(R.id.backspace));
        TouchTickAction.setupView(inputView.findViewById(R.id.switch_single));
        TouchTickAction.setupView(inputView.findViewById(R.id.capsLock));

        keyboardLayout = inputView.findViewById(R.id.keyboard_single_hand);
        keyboardLayout.setListener((string, down, tick) -> {
            vibrator.vibrate(VIBRATE_TIME);
            switch (string) {
                case "🔡":
                    revCapsLock();
                    break;
                case "👈":
                    inputView.findViewById(R.id.layout_1).setVisibility(View.VISIBLE);
                    inputView.findViewById(R.id.layout_2).setVisibility(View.INVISIBLE);
                    inputView.findViewById(R.id.layout_1).setEnabled(true);
                    inputView.findViewById(R.id.layout_2).setEnabled(false);
                    break;
                case "︺":
                    sendKeyChar(' ');
                    break;
                case "←":
                    sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
                    break;
                default:
                    type(string);
                    break;
            }
        });
    }

    private void revCapsLock() {
        capsLock = !capsLock;
        keyboardLayout.setCapsLock(capsLock);
        TextView textView;
        if (capsLock) {
            textView = inputView.findViewById(R.id.q);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.w);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.e);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.r);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.t);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.y);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.u);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.i);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.o);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.p);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.a);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.s);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.d);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.f);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.g);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.h);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.j);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.k);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.l);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.z);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.x);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.c);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.v);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.b);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.n);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.M);
            toUpperCase(textView);
            textView = inputView.findViewById(R.id.capsLock);
            textView.setText("🔡");
        } else {
            textView = inputView.findViewById(R.id.q);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.w);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.e);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.r);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.t);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.y);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.u);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.i);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.o);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.p);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.a);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.s);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.d);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.f);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.g);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.h);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.j);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.k);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.l);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.z);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.x);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.c);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.v);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.b);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.n);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.M);
            toLowerCase(textView);
            textView = inputView.findViewById(R.id.capsLock);
            textView.setText("🔠");
        }
    }

    private void toUpperCase(TextView textView) {
        String text = (String) textView.getText();
        textView.setText(text.toUpperCase());
    }

    private void toLowerCase(TextView textView) {
        String text = (String) textView.getText();
        textView.setText(text.toLowerCase());
    }

    private void type(String string) {
        if (capsLock) {
            getCurrentInputConnection().commitText(string.toUpperCase(), string.length());
        } else {
            getCurrentInputConnection().commitText(string.toLowerCase(), string.length());
        }
    }

    private void setupListener(View inputView, int resId) {
        ViewGroup viewGroup = inputView.findViewById(resId);
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.setOnClickListener(this::keyboardClick);
            TouchTickAction.setupView(view);
        }
    }

    private void keyboardClick(View view) {
        if (view instanceof TextView) {
            vibrator.vibrate(VIBRATE_TIME);
            String text = ((TextView) view).getText().toString();
            type(text);
        }
    }


    private static class TouchTickAction implements View.OnTouchListener {

        long downTick;

        static void setupView(View v) {
            v.setOnTouchListener(new TouchTickAction());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (!v.isEnabled()) {
                return false;
            }

            int action = event.getAction();
            if (action != MotionEvent.ACTION_DOWN && action != MotionEvent.ACTION_MOVE) {
                return true;
            }

            long realTime = SystemClock.elapsedRealtime();
            boolean isDown = action == MotionEvent.ACTION_DOWN;
            if (isDown) {
                downTick = realTime + KeyboardLayout.TICK_START;
            } else {
                if (realTime < downTick) return true;
                downTick = realTime + KeyboardLayout.TICK_PER;
            }

            v.performClick();

            return true;
        }
    }
}
