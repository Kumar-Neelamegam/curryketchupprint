package com.example.curryketchup_print.webservice;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.tcp.TcpConnection;
import com.example.curryketchup_print.utils.Common;
import com.example.curryketchup_print.utils.SharedPrefUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.curryketchup_print.utils.Common.baseURL;
import static com.example.curryketchup_print.utils.Common.debugLogger;
import static com.example.curryketchup_print.utils.Common.errorLogger;


public class GetPrinterWorker extends Worker {

    SharedPrefUtils sharedPrefUtils;
    Context context;
    String TAG=GetPrinterWorker.class.getSimpleName();

    public GetPrinterWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.sharedPrefUtils = new SharedPrefUtils(context);
        this.context = context;
    }

    @Override
    public Result doWork() {

        try {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            DataService service = retrofit.create(DataService.class);
            Call<List<Order>> call = service.getPrinterData();
            Response<List<Order>> response = call.execute();
            if (response != null) {
                List<Order> orderList = response.body();
                prepareData(orderList);
                Result.success();
            }
        } catch (Exception e) {
            errorLogger(e, "DataService", context.getClass().getSimpleName());
            return Result.failure();
        }
        return Result.failure();
    }


    private void prepareData(List<Order> orders) {
        debugLogger(TAG, "prepareData");
        StringBuilder str1;
        StringBuilder str2;
        String TRANSID;
        for (Order order : orders) {
            str1 = new StringBuilder();
            str2 = new StringBuilder();
            TRANSID = order.getTransid();
            str1.append("[L]\n");
            str1.append("[L]\n");
            str1.append("[C]<font size='normal'>CURRY AND KETCHUP</font>\n");
            str1.append("[C]<font size='big'>ORDER NO:" + order.getTransid() + "</font>\n");
            str1.append("[L]================================\n");
            str1.append("[L]<font size='big'>" + order.getMembername() + "</font>\n");
            str1.append("[L]<font size='normal'>" + order.getDate() + "</font>\n");
            str1.append("[L]<font size='normal'>" + order.getSpecialnotes() + "</font>\n");
            str1.append("[C]================================\n");
            int totalPrice = 0;
            int ordersno = 1;
            for (OrderItem orderItem : order.getOrderItems()) {
                str2.append("[L]"+orderItem.getItemcategory()+"\n");
                str2.append("[L]<font size='big'>"+ordersno + ":" + orderItem.getItemname() + "[R](" + orderItem.getQuantity() + ")</font>\n");
                totalPrice += Integer.parseInt(orderItem.getItemprice());
                ordersno++;
            }
            str2.append("[C]================================\n");
            str2.append("[R]<font size='big'>TOTAL PRICE :[R]" + totalPrice + ".kr</font>\n");
            callPrinter(str1, str2, TRANSID);
        }


    }

    private void callPrinter(StringBuilder str1, StringBuilder str2, String TRANSID) {
        debugLogger(TAG, "callPrinter"+str1.toString()+ str2.toString()+ TRANSID);

       new Thread(() -> {
            try {
                EscPosPrinter printer = new EscPosPrinter(new TcpConnection(sharedPrefUtils.getIpaddress(), 9100), 203, 48f, 32);
                printer.printFormattedTextAndCut(str1.toString()+str2.toString());
                printer.printFormattedTextAndCut(str1.toString()+str2.toString());//two copies
                callUpdate(TRANSID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void callUpdate(String transid) {
        debugLogger(TAG, "callUpdate");

        Data data = new Data.Builder()
                .putInt("transid", Integer.parseInt(transid))
                .build();

        Constraints myConstraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        OneTimeWorkRequest runWorldDataRetrievalWorker = new OneTimeWorkRequest.Builder(PostPrinterWorker.class).setConstraints(myConstraints)
                .setInputData(data)
                .addTag(Common.WORKER_TAG_LATESTDATA)
                .build();
        WorkManager.getInstance(getApplicationContext()).enqueue(runWorldDataRetrievalWorker);

    }


}
