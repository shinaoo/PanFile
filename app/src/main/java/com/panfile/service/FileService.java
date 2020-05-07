package com.panfile.service;

import android.util.Log;

import com.panfile.model.network.FileSrv;
import com.panfile.model.network.RetrofitCls;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileService {

    private FileSrv fileSrv;
    public FileService(){
        fileSrv = RetrofitCls.getInstance().getFileSrv();
    }

    public void uploadFile(String uploadPath){
        File uFile = new File(uploadPath);
        if (!uFile.exists())
            return;
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),uFile);
        MultipartBody.Part pFile = MultipartBody.Part.createFormData("uploadFile",uFile.getName(),requestBody);

        RequestBody username = RequestBody.create(MediaType.parse("multipart/form-data"),"lanpa");
        RequestBody projectName = RequestBody.create(MediaType.parse("ultipart/form-data"),"lanpa");
        Call<String> call = fileSrv.uploadFile(pFile,username,projectName);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("MyTag","upload :" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("MyTag","upload failed:" + t.toString());
            }
        });

    }
}
