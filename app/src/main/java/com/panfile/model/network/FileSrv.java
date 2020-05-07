package com.panfile.model.network;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileSrv {
    @Multipart
    @POST("/file/upload")
    Call<String> uploadFile(@Part MultipartBody.Part file,@Part("userName") RequestBody username,@Part("projectName") RequestBody projectName);

}
