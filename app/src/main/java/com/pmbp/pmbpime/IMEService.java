package com.pmbp.pmbpime;

import android.annotation.SuppressLint;
import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class IMEService extends InputMethodService {
    @SuppressLint({"ClickableViewAccessibility", "InflateParams"})
    @Override
    public void onCreate() {
        super.onCreate();

        View inputView = getLayoutInflater().inflate(R.layout.ime_main, null);
        View candidatesView = getLayoutInflater().inflate(R.layout.ime_candidates, null);

        setInputView(inputView);
        setCandidatesView(candidatesView);

        setupListener(inputView, R.id.qwe);
        setupListener(inputView, R.id.rtyu);
        setupListener(inputView, R.id.iop);
        setupListener(inputView, R.id.asd);
        setupListener(inputView, R.id.fgh);
        setupListener(inputView, R.id.jkl);
        setupListener(inputView, R.id.zxc);
        setupListener(inputView, R.id.vbn);
        setupListener(inputView, R.id.m);
    }

    private void setupListener(View inputView, int resId) {
        ViewGroup viewGroup = inputView.findViewById(resId);
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.setOnClickListener(this::keyboardClick);
        }
    }

    private void keyboardClick(View view) {
        if (view instanceof TextView) {
            String text = ((TextView) view).getText().toString();
            getCurrentInputConnection().commitText(text, text.length());
        }
    }
}
