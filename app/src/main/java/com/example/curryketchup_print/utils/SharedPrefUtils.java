package com.example.curryketchup_print.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
import static com.example.curryketchup_print.utils.Common.KEY_IPADDRESS;
import static com.example.curryketchup_print.utils.Common.KEY_NETWORKSTATUS;


public class SharedPrefUtils {
    private static final String SHARED_PREF_NAME = "candkshare";
    private static SharedPrefUtils INSTANCE = null;
    private final SharedPreferences preferences;

    public SharedPrefUtils(Context context) {
        preferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
    }

    public static SharedPrefUtils getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPrefUtils(context);
            return INSTANCE;
        }
        return INSTANCE;
    }

    public void clearAllPrefs() {
        preferences.edit().clear().apply();
    }

    public boolean getNetworkAvailability() {
        return preferences.getBoolean(KEY_NETWORKSTATUS, false);
    }

    public void setNetworkAvailability(boolean status) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_NETWORKSTATUS, status);
        editor.apply();
    }

    public String getIpaddress() {
        return preferences.getString(KEY_IPADDRESS, "192.168.1.155");
    }

    public void setIpaddress(String ipaddress) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_IPADDRESS, ipaddress);
        editor.apply();
    }



}
