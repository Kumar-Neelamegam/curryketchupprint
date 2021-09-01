package com.example.curryketchup_print.activities;

import android.Manifest;
import android.content.Context;

import androidx.core.app.ActivityCompat;


import com.example.curryketchup_print.R;
import com.example.curryketchup_print.utils.Common;

public abstract class CoreActivity extends RuntimePermissionsActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String TAG = CoreActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS = 20;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setContentView(int layoutResID) {
        try {
            super.setContentView(layoutResID);
            bindViews();
            setContext(this);
            setListeners();

        } catch (Exception e) {
            Common.errorLogger(e, e.getMessage(), TAG);
        }
    }


    /**
     * method to bind all views related to resourceLayout
     */
    protected abstract void bindViews();

    /**
     * called to set view listener for views
     */
    protected abstract void setListeners();

    //***************************************************************************************************

    public void isStoragePermissionGranted() {

        super.requestAppPermissions(new
                        String[]{
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.CHANGE_NETWORK_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.WAKE_LOCK
                }, R.string
                        .runtime_permissions_txt
                , REQUEST_PERMISSIONS);


    }
    //***************************************************************************************************
//End
}
