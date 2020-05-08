package com.panfile.model.network;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface FileSrv {

    @Multipart
    @POST("/file/upload")
    Call<String> uploadFile(@Part List<MultipartBody.Part> partList);

    @GET("/file/download")
    Call<ResponseBody> download(@Query("fileName") String fileurl, @Query("projectName")String proName, @Query("userName")String username);

}
