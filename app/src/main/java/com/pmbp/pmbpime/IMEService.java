package com.pmbp.pmbpime;

import android.annotation.SuppressLint;
import android.inputmethodservice.InputMethodService;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pmbp.pmbpime.view.KeyboardLayout;

public class IMEService extends InputMethodService {
    @SuppressLint({"ClickableViewAccessibility", "InflateParams"})
    @Override
    public void onCreate() {
        super.onCreate();

        View inputView = getLayoutInflater().inflate(R.layout.ime_main, null);
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

        inputView.findViewById(R.id.space).setOnClickListener(v -> sendKeyChar(' '));
        inputView.findViewById(R.id.backspace).setOnClickListener(v -> sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL));
        inputView.findViewById(R.id.switch_single).setOnClickListener(v -> {
            inputView.findViewById(R.id.layout_2).setVisibility(View.VISIBLE);
            inputView.findViewById(R.id.layout_1).setVisibility(View.INVISIBLE);
            inputView.findViewById(R.id.layout_2).setEnabled(true);
            inputView.findViewById(R.id.layout_1).setEnabled(false);
        });
        TouchTickAction.setupView(inputView.findViewById(R.id.space));
        TouchTickAction.setupView(inputView.findViewById(R.id.backspace));
        TouchTickAction.setupView(inputView.findViewById(R.id.switch_single));

        ((KeyboardLayout) inputView.findViewById(R.id.keyboard_single_hand)).setListener((string, down, tick) -> {
            switch (string) {
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
                    getCurrentInputConnection().commitText(string, string.length());
                    break;
            }
        });
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
            String text = ((TextView) view).getText().toString();
            getCurrentInputConnection().commitText(text, text.length());
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
