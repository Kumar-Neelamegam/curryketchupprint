package com.example.curryketchup_print.activities;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.BounceInterpolator;


import com.example.curryketchup_print.databinding.ActivitySplashBinding;
import com.example.curryketchup_print.utils.CheckNetwork;
import com.example.curryketchup_print.utils.Common;
import com.example.curryketchup_print.utils.SharedPrefUtils;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;


public class SplashActivity extends CoreActivity {

    SharedPrefUtils sharedPrefUtils;
    ActivitySplashBinding activitySplashBinding;
    String TAG = SplashActivity.class.getSimpleName();

    //**********************************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPrefUtils = SharedPrefUtils.getInstance(this);

        activitySplashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = activitySplashBinding.getRoot();
        setContentView(view);

        initiateDashboard();


    }

    void initiateDashboard() {

        try {

            isStoragePermissionGranted();

        } catch (Exception e) {
            Common.errorLogger(e, e.getMessage(), TAG);
        }
    }


    //**********************************************************************************************
    @Override
    public void onPermissionsGranted(int requestCode) {

        try {
            getInit();
        } catch (Exception e) {
            Common.errorLogger(e, e.getMessage(), TAG);
        }

    }

    //**********************************************************************************************
    private void getInit() {

        Sprite animate = new Wave();
        activitySplashBinding.progressBar.setIndeterminateDrawable(animate);
        doBounceAnimation(activitySplashBinding.imageView, 2);
        CheckNetwork checkNetwork = new CheckNetwork(getApplicationContext());
        checkNetwork.registerNetworkCallback();

        FirebaseApp.initializeApp(getApplicationContext());
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        new Handler(Looper.myLooper()).postDelayed(() -> navigate(), 5000);
    }


    public static void doBounceAnimation(View targetView, int repeatCount) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "translationY", 0, 100, 0);
        animator.setInterpolator(new BounceInterpolator());
        animator.setStartDelay(500);
        animator.setDuration(2500);
        animator.setRepeatCount(repeatCount);
        animator.start();
    }

    //**********************************************************************************************
    @Override
    protected void onResume() {
        super.onResume();
    }

    private void navigate() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finishAffinity();
    }

    //**********************************************************************************************
    @Override
    public void onBackPressed() {

    }

    //**********************************************************************************************
    @Override
    protected void bindViews() {

    }

    //**********************************************************************************************
    @Override
    protected void setListeners() {

    }

    //**********************************************************************************************
    @Override
    protected void onPause() {
        super.onPause();
    }

    //**********************************************************************************************
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //**********************************************************************************************
    @Override
    protected void onStop() {
        super.onStop();
    }

//**********************************************************************************************
}
