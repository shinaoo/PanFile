package com.panfile.model.network;

import com.panfile.utils.JsonResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ClientSrv {

    @FormUrlEncoded
    @POST("/client/login")
    Call<String> login(@Field("cName")String cName, @Field("cPass")String cPass, @Field("cToken")String cToken);
}
