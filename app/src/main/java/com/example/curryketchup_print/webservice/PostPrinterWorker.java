package com.example.curryketchup_print.webservice;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.JsonArray;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.curryketchup_print.utils.Common.baseURL;

public class PostPrinterWorker extends Worker {


    public PostPrinterWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork()
    {
        try {
            int transId = getInputData().getInt("transid", 0);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            DataService service = retrofit.create(DataService.class);
            Call<Void> call = service.updatePrinterData(transId);
            call.execute();


            Result.success();
        } catch (Exception e) {
            Result.failure();
        }
        return Result.failure();

    }
}
