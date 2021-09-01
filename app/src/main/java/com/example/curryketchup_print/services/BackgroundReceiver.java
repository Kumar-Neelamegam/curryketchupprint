package com.example.curryketchup_print.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;

import com.example.curryketchup_print.utils.Common;


public class BackgroundReceiver extends BroadcastReceiver {

    /* ********************************************************************************************** */
    public static final int REQUEST_CODE = 65646362;
    private static final String TAG = BackgroundReceiver.class.getSimpleName();
    private static final long INTERVAL_TO_STOP_SCANNING_IN_MS = 12000;
    /* ********************************************************************************************** */

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager.WakeLock screenWakeLock = null;

        if (screenWakeLock == null) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            screenWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG);
            screenWakeLock.acquire(INTERVAL_TO_STOP_SCANNING_IN_MS);
        }

        Intent i = new Intent(context, BackgroundService.class);
        i.setAction(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startService(i);
        setAlarm(context, Common.ALARM_SERVICE_EXECUTION_INTERVAL_IN_MS_5);

        if (screenWakeLock != null) {
            screenWakeLock.release();
        }

    }

    /* ********************************************************************************************** */

    public static void setAlarm(Context context, long settime) {
        try {
            Intent i = new Intent(context, BackgroundReceiver.class);
            i.setAction(Intent.ACTION_MAIN);
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pi = PendingIntent.getBroadcast(context, REQUEST_CODE, i, PendingIntent.FLAG_UPDATE_CURRENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                long interval = System.currentTimeMillis() + settime;
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, interval, pi);
            } else {
                am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), settime, pi);
            }
        } catch (Exception e) {
            Common.errorLogger(e, e.getMessage(), TAG);
        }
    }
    /* ********************************************************************************************** */

}
