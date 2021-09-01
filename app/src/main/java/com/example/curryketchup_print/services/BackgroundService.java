package com.example.curryketchup_print.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.curryketchup_print.utils.Common;
import com.example.curryketchup_print.utils.SharedPrefUtils;
import com.example.curryketchup_print.webservice.GetPrinterWorker;

import static com.example.curryketchup_print.utils.Common.debugLogger;
import static com.example.curryketchup_print.utils.Common.isMyServiceRunning;


public class BackgroundService extends Service {

    /* ********************************************************************************************** */
    String TAG = BackgroundService.class.getSimpleName();
    SharedPrefUtils sharedPrefUtils;

    /* ********************************************************************************************** */
    @Override
    public void onCreate() {
        debugLogger(TAG, "Service OnCreate");
        sharedPrefUtils = new SharedPrefUtils(getApplicationContext());
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        debugLogger(TAG, "Service onStartCommand");
        Constraints myConstraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest runWorldDataRetrievalWorker = new OneTimeWorkRequest.Builder(GetPrinterWorker.class).setConstraints(myConstraints)
                .addTag(Common.WORKER_TAG_LATESTDATA)
                .build();
        WorkManager.getInstance(getApplicationContext()).enqueue(runWorldDataRetrievalWorker);

        return START_STICKY;

    }
    /* ********************************************************************************************** */

    @Override
    public void onDestroy() {
        super.onDestroy();
        debugLogger(TAG, "onDestroy");
        callDozeService();
    }
    /* ********************************************************************************************** */

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        debugLogger(TAG, "onTaskRemoved");
        callDozeService();
    }

    public void callDozeService() {
        if (!isMyServiceRunning(BackgroundService.class, this)) {
            Intent intent1 = new Intent(this, BackgroundService.class);
            BackgroundServiceRestarter.enqueueWork(this, intent1);
            debugLogger(TAG, "BackgroundService started on doze mode");
        }
    }


}
