package com.ike.l2_zxing.demo;

import android.widget.Toast;

import com.ike.l2_zxing.SimpleCaptureActivity;

public class MyCaptureActivity extends SimpleCaptureActivity {

    @Override
    public void handleResult(String resultString) {
        Toast.makeText(this, resultString, Toast.LENGTH_SHORT);
    }
}
