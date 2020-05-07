package com.panfile.model.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCls {
    private  Retrofit retrofit =null;
    private static RetrofitCls retrofitCls = null;
    public static RetrofitCls getInstance(String url){
        if (null == retrofitCls){
            retrofitCls = new RetrofitCls(url);
        }
        return retrofitCls;
    }

    private RetrofitCls(String url){
        retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public ClientSrv getClientSrv(){
        return retrofit.create(ClientSrv.class);
    }
}
