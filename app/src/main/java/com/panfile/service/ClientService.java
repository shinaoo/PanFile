package com.panfile.service;

import android.util.Log;

import com.panfile.event.BusEvent;
import com.panfile.model.network.ClientSrv;
import com.panfile.model.network.RetrofitCls;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientService {

    private RetrofitCls retrofitCls = null;

    public ClientService(){
        retrofitCls = RetrofitCls.getInstance("http://192.168.1.100:8080");
    }

    public boolean login(String cName,String cPass,String cToken){
        Log.e("MyTag","login click");
        ClientSrv clientSrv = retrofitCls.getClientSrv();
        Call<String> call = clientSrv.login(cName,cPass,cToken);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("MyTag","login succ back:" + response.body());
                EventBus.getDefault().postSticky(new BusEvent(BusEvent.Type.UNKNOWN,""));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("MyTag","login faile:" + t.toString());
                EventBus.getDefault().postSticky(new BusEvent(BusEvent.Type.UNKNOWN,""));
            }
        });
        return false;
    }
}
