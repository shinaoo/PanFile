package com.panfile.model.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PathSrv {

    @FormUrlEncoded
    @POST("/path/getFiles")
    public Call<String> getFiles(@Field("path")String path);

}
