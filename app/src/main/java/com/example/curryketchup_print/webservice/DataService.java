package com.example.curryketchup_print.webservice;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataService {

    @GET("getprinterdata.php")
    Call<List<Order>> getPrinterData();


    @GET("updateprinterdata.php")
    Call<Void> updatePrinterData(@Query("transid") int transid);

}
