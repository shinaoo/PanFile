package com.panfile.model.network;

import com.alibaba.fastjson.support.retrofit.Retrofit2ConverterFactory;

import retrofit2.Retrofit;

public class RetrofitCls {
    private String basrUrl = "http://192.168.1.100:8080";
    private  Retrofit retrofit =null;
    private static RetrofitCls retrofitCls = null;
    public static RetrofitCls getInstance(){
        if (null == retrofitCls){
            retrofitCls = new RetrofitCls();
        }
        return retrofitCls;
    }

    private RetrofitCls(){
        retrofit = new Retrofit.Builder().baseUrl(basrUrl).addConverterFactory(Retrofit2ConverterFactory.create()).build();
    }

    public ClientSrv getClientSrv(){
        return retrofit.create(ClientSrv.class);
    }

    public PathSrv getPathSrv(){
        return retrofit.create(PathSrv.class);
    }

    public FileSrv getFileSrv(){
        return retrofit.create(FileSrv.class);
    }
}
