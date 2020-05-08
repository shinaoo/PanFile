package com.panfile.service;

import android.util.Log;

import com.panfile.model.network.FileSrv;
import com.panfile.model.network.RetrofitCls;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileService {

    private FileSrv fileSrv;

    public FileService() {
        fileSrv = RetrofitCls.getInstance().getFileSrv();
    }

    public void uploadFile(String uploadPath) {
        new Thread(() -> {
            Log.e("MyTag","upload thread start");
            File uFile = new File(uploadPath);
            if (!uFile.exists())
                return;
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("userName", "lanpa").addFormDataPart("projectName", "lanpa");
            File file = new File(uploadPath);
            RequestBody uploadBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("uploadFile", file.getName(), uploadBody);
            List<MultipartBody.Part> parts = builder.build().parts();

            Call<String> call = fileSrv.uploadFile(parts);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("MyTag", "upload :" + response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("MyTag", "upload failed:" + t.toString());
                }
            });
        }).start();

    }
}
