package com.bt.rxtoolgoon.app;

import android.Manifest;
import android.app.Application;

import androidx.core.app.ActivityCompat;

import com.example.error_crash.RxTool;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RxTool.init(this);
    }
}
