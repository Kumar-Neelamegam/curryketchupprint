package com.example.curryketchup_print.activities;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.tcp.TcpConnection;
import com.example.curryketchup_print.R;
import com.example.curryketchup_print.databinding.ActivityMainBinding;
import com.example.curryketchup_print.services.BackgroundReceiver;
import com.example.curryketchup_print.services.BackgroundService;
import com.example.curryketchup_print.services.BackgroundServiceRestarter;
import com.example.curryketchup_print.utils.Common;
import com.example.curryketchup_print.utils.SharedPrefUtils;

import static com.example.curryketchup_print.utils.Common.debugLogger;
import static com.example.curryketchup_print.utils.Common.isMyServiceRunning;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    ActivityMainBinding activityMainBinding;
    SharedPrefUtils sharedPrefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        sharedPrefUtils=new SharedPrefUtils(this);
        // TODO
        /*
        use work scheduler for 15 minutes
        call the api https://curryandketchup.no/myapi/getprinterdata.php
        call the printer service
        call the api https://curryandketchup.no/myapi/updateprinterdata.php
        repeat
         */

        activityMainBinding.contentinclude.btnsave.setOnClickListener(v -> {

            if(activityMainBinding.contentinclude.edtxtIpaddress.getText().toString().isEmpty())
            {
                activityMainBinding.contentinclude.edtxtIpaddress.setError("Enter the printer ip address");
                activityMainBinding.contentinclude.edtxtIpaddress.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Enter the printer ip address...", Toast.LENGTH_LONG).show();
                return ;
            }
            sharedPrefUtils.setIpaddress(activityMainBinding.contentinclude.edtxtIpaddress.getText().toString());
            Toast.makeText(getApplicationContext(), "Printer ip address saved successfully.. Service started..", Toast.LENGTH_LONG).show();
            activateService(null);
        });


        //checkServiceRunning(BackgroundService.class);
        //checkServiceRunning(BackgroundServiceRestarter.class);
        serviceCheck();

        //debugLogger(TAG, String.valueOf(isRunning(BackgroundService.class, this)));
        //debugLogger(TAG, String.valueOf(isRunning(BackgroundServiceRestarter.class, this)));

    }



    public static boolean isRunning(Class<? extends Service> serviceClass, Context context) {
        final Intent intent = new Intent(context, serviceClass);
        return (PendingIntent.getService(context, 65646362, intent, PendingIntent.FLAG_UPDATE_CURRENT) != null);
    }

    public boolean checkServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(1))
        {
            if (serviceClass.getName().equals(service.service.getClassName()))
            {
                debugLogger(TAG, "Service true");

                return true;
            }
        }
        debugLogger(TAG, "Service false");

        return false;
    }


    private void serviceCheck() {
        if (sharedPrefUtils.getIpaddress()!=null) {
            debugLogger(TAG, "Service active");
            activityMainBinding.contentinclude.edtxtIpaddress.setEnabled(false);
            activityMainBinding.contentinclude.txtstatus.setText("Active");
            activityMainBinding.contentinclude.txtstatus.setTextColor(ContextCompat.getColor(this, R.color.green_400));
        }else{
            debugLogger(TAG, "Service Inactive");
            activityMainBinding.contentinclude.edtxtIpaddress.setEnabled(true);
            activityMainBinding.contentinclude.txtstatus.setText("Inactive");
            activityMainBinding.contentinclude.txtstatus.setTextColor(ContextCompat.getColor(this, R.color.red_400));
        }
    }


    public void activateService(View view){
        //ip address check
        if(activityMainBinding.contentinclude.edtxtIpaddress.getText().toString().isEmpty())
        {
            activityMainBinding.contentinclude.edtxtIpaddress.setError("Enter the printer ip address");
            Toast.makeText(this, "Enter the printer ip address...", Toast.LENGTH_LONG).show();
            return ;
        }

        //network check
        if(!sharedPrefUtils.getNetworkAvailability()){
            Toast.makeText(this, "Enable wifi/network connection...", Toast.LENGTH_LONG).show();
            return ;
        }

        BackgroundReceiver.setAlarm(this, Common.ALARM_SERVICE_EXECUTION_INTERVAL_IN_MS_5);

        activityMainBinding.contentinclude.edtxtIpaddress.setEnabled(false);
        activityMainBinding.contentinclude.txtstatus.setText("Active");
        activityMainBinding.contentinclude.txtstatus.setTextColor(ContextCompat.getColor(this, R.color.green_400));

        //print test
        //testprint();

    }



    private void testprint() {
        new Thread(() -> {
            try {
                EscPosPrinter printer = new EscPosPrinter(new TcpConnection(sharedPrefUtils.getIpaddress(), 9100), 203, 48f, 32);
                printer.printFormattedTextAndCut(
                        "[L]\n" +
                                "[C]<u><font size='big'>TESTING OK</font></u>\n" +
                                "[C]<u><font size='big'>SERVICE STARTED</font></u>\n"+
                                "[C]<u><font size='big'>"+sharedPrefUtils.getIpaddress()+"</font></u>\n"

                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}