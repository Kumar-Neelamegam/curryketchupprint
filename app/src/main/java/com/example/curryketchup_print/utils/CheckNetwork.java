package com.example.curryketchup_print.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class CheckNetwork {

    String TAG = CheckNetwork.class.getSimpleName();

    Context context;
    SharedPrefUtils sharedPrefUtils;

    public CheckNetwork(Context context) {
        this.context = context;
        this.sharedPrefUtils = new SharedPrefUtils(context);
    }

    // Network Check
    public void registerNetworkCallback() {
        try {

            final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            manager.registerNetworkCallback(
                    new NetworkRequest.Builder()
                            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                            .addTransportType(NetworkCapabilities.TRANSPORT_VPN)
                            .build(),
                    new ConnectivityManager.NetworkCallback() {
                        @Override
                        public void onAvailable(Network network) {
                            sharedPrefUtils.setNetworkAvailability(true);
                            Common.debugLogger( "onAvailable", TAG);
                        }

                        @Override
                        public void onUnavailable() {
                            sharedPrefUtils.setNetworkAvailability(false);
                            Common.debugLogger( "onUnavailable", TAG);
                        }

                        @Override
                        public void onLost(@NonNull Network network) {
                            sharedPrefUtils.setNetworkAvailability(false);
                            Common.debugLogger( "onLost", TAG);
                        }
                    });

        } catch (Exception e) {
            Common.errorLogger(e, "registerNetworkCallback", TAG);
        }
    }
}
