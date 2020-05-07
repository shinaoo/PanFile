package com.panfile.service;

import android.util.Log;

import com.panfile.model.network.PathSrv;
import com.panfile.model.network.RetrofitCls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PathService {
    private PathSrv pathSrv;

    public PathService(){
        pathSrv = RetrofitCls.getInstance().getPathSrv();
    }

    public void getFiles(String path){

        Call<String> call = pathSrv.getFiles(path);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("MyTag","path:" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("MyTag","path fail:" + t.toString());
            }
        });
    }

}
