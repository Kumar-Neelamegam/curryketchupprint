package com.example.curryketchup_print.services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.JobIntentService;

import org.jetbrains.annotations.NotNull;

import static com.example.curryketchup_print.utils.Common.ALARM_SERVICE_EXECUTION_INTERVAL_IN_MS_4;


public class BackgroundServiceRestarter extends JobIntentService {

    public static int mainJobId = 234234;
    private final Handler handler = new Handler(Looper.myLooper());
    @RequiresApi(23)
    private final Runnable restart = () -> {
        Context context = BackgroundServiceRestarter.this.getApplicationContext();
        BackgroundReceiver.setAlarm(context, ALARM_SERVICE_EXECUTION_INTERVAL_IN_MS_4);
    };

    public static void enqueueWork(@NotNull Context context, @NotNull Intent intent) {
        JobIntentService.enqueueWork(context, BackgroundServiceRestarter.class, mainJobId, intent);
    }

    @RequiresApi(23)
    public void onCreate() {
        super.onCreate();
        this.handler.post(this.restart);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

    }

    public boolean onStopCurrentWork() {
        return super.onStopCurrentWork();
    }


}

