package com.panfile.model.network;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileSrv {

    @Multipart
    @POST("/file/upload")
    Call<String> uploadFile(@Part List<MultipartBody.Part> partList);

}
