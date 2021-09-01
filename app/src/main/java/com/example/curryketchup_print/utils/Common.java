package com.example.curryketchup_print.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

public class Common {

    public static final long ALARM_SERVICE_EXECUTION_INTERVAL_IN_MS_5 = 300000;
    public static long ALARM_SERVICE_EXECUTION_INTERVAL_IN_MS_4 = 240000;
    public static String KEY_NETWORKSTATUS = "networkstatus";
    public static String KEY_IPADDRESS = "ipaddress";
    public static String WORKER_TAG_LATESTDATA = "WORKER_TAG_LATESTDATA";
    public static String baseURL="https://curryandketchup.no/myapi/";

    /* ********************************************************************************************** */

    public static void errorLogger(Exception e, String message, String TAG) {
        if (e != null) {
            Log.e(TAG, "errorLogger: " + message, e);
            FirebaseCrashlytics.getInstance().log(Objects.requireNonNull(e.getMessage()));
        }
    }

    public static void debugLogger(String tag, String message) {
        Log.e(tag, message);
    }

    /* ********************************************************************************************** */
    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(1)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    /* ********************************************************************************************** */


}
