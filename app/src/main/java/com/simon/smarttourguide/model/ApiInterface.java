package com.simon.smarttourguide.model;


import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("auth/getaccesstoken")
    Call<JsonObject> getaccesstoken(@Field("authToken") String authtoken, @Header("buildnumber") int buildnumber);

    @GET("signin")
    Call<JsonObject> getSignIn(@Query("email") String email, @Query("password") String password);

    @GET("signin")
    Call<JsonObject> postVerifyCustomerPhNumber(@Query("email") String email, @Query("password") String password);

    @GET("signin")
    Call<JsonObject> postCashCollect(@Query("email") String email, @Query("password") String password);


    @GET("appversion?app=android_merchant")
    Call<JsonObject> getAppVersion();

    @GET("orderhistory")
    Call<JsonObject> getOrderHistory(@Query("merchant_id") String merchant_id, @Query("all_orders") String all_orders, @Header("accesstoken") String accesstoken);

    @GET("allowinstoreoperations")
    Call<JsonObject> allowInStoreOperations(@Header("accesstoken") String accesstoken);

    @GET("orderhistory")
    Call<JsonObject> getOrderHistoryToday(@Query("merchant_id") String merchant_id, @Query("today_orders") String all_orders, @Header("accesstoken") String accesstoken);

    @GET("getmenu")
    Call<JsonObject> getMenu(@Query("merchant_id") String merchant_id, @Header("accesstoken") String accesstoken);

//    @POST("syncofflineorders")
//    Call<JsonObject> getSync( @Header("accesstoken") String accesstoken,@Body CartArray jsonObject);
//
//    @POST("orderstatusupdate")
//    Call<JsonObject> getOrderStatusUpdate(@Header("accesstoken") String accesstoken, @Body Status jsonObject);

    @FormUrlEncoded
    @POST("rechargeuseramount")
    Call<JsonObject> postRechargeUserAmount(@Header("accesstoken") String accesstoken,  @Field("mobile") String mobile, @Field("pin") String storePin, @Field("amount") String amount);

    @FormUrlEncoded
    @POST("getuserdetails")
    Call<JsonObject> getUserDetails(@Header("accesstoken") String accesstoken, @Field("mobile") String mobile);

    @GET("orderconflict")
    Call<JsonObject> getOrderConflict(@Header("accesstoken") String accesstoken, @Query("order_id") String order_id, @Query("conflict_text") String conflict_text, @Query("conflict_order_price") int conflict_order_price);

    ///auth/getfirebasetoken
    @GET("stockupdate")
    Call<JsonObject> getStockUpdate(@Header("accesstoken") String accesstoken, @Query("store_products_id") String store_products_id, @Query("stock_check") String stock_check, @Query("merchant_id") String merchant_id);

    @GET("billinfoupdate")
    Call<JsonObject> getBillInfoUpdate(@Header("accesstoken") String accesstoken, @Query("order_id") int order_id, @Query("store_order_number") String store_order_number, @Query("store_bill_number") String store_bill_number);

    // https://testapi.grabbngo.com/storeadmin/updatemerchantstatus?store_status=OPEN
    // parameters - accesstoken,store_status
    @GET("updatemerchantstatus")
    Call<JsonObject> getCheckMerchantStatus(@Header("accesstoken") String accesstoken, @Query("store_status") String store_status);


}
