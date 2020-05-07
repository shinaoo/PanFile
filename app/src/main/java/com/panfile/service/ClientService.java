package com.panfile.service;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.panfile.event.MainThreadEvent;
import com.panfile.model.network.ClientSrv;
import com.panfile.model.network.RetrofitCls;
import com.panfile.utils.JsonResult;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientService {

    private ClientSrv clientSrv;

    public ClientService(){
        clientSrv = RetrofitCls.getInstance().getClientSrv();
    }

    public boolean login(String cName,String cPass,String cToken){
        Call<String> call = clientSrv.login(cName,cPass,cToken);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("MyTag","response:" + response.body());
                JsonResult jsonResult = JSON.parseObject(response.body(),JsonResult.class);
                if (jsonResult.getStatus() == 200){
                    EventBus.getDefault().postSticky(new MainThreadEvent(MainThreadEvent.Type.ACT_LOGIN_2_FILES,""));
                }else{
                    EventBus.getDefault().postSticky(new MainThreadEvent(MainThreadEvent.Type.ERR_LOGIN,jsonResult.getMsg()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                EventBus.getDefault().postSticky(new MainThreadEvent(MainThreadEvent.Type.ERR_LOGIN,t.toString()));
            }
        });
        return false;
    }
}
