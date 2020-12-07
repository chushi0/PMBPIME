package com.pmbp.pmbpime;

import android.annotation.SuppressLint;
import android.inputmethodservice.InputMethodService;
import android.view.View;

public class IMEService extends InputMethodService {
    @SuppressLint({"ClickableViewAccessibility", "InflateParams"})
    @Override
    public void onCreate() {
        super.onCreate();

        View inputView = getLayoutInflater().inflate(R.layout.ime_main, null);
        View candidatesView = getLayoutInflater().inflate(R.layout.ime_candidates, null);

        setInputView(inputView);
        setCandidatesView(candidatesView);

        inputView.setOnTouchListener((v, event) -> {
            setCandidatesViewShown(true);
            return false;
        });
        candidatesView.setOnTouchListener((v, event) -> {
            setCandidatesViewShown(false);
            return false;
        });
    }
}
