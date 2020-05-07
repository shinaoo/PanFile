package com.panfile.service;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.panfile.entity.json.PanFile;
import com.panfile.event.MainThreadEvent;
import com.panfile.model.network.PathSrv;
import com.panfile.model.network.RetrofitCls;
import com.panfile.utils.JsonResult;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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
                JsonResult jsonResult = JSON.parseObject(response.body(),JsonResult.class);
                if (jsonResult.getStatus() == 200){
                    List<PanFile> files = JSON.parseArray((String)jsonResult.getData(),PanFile.class);
                    EventBus.getDefault().postSticky(new MainThreadEvent(MainThreadEvent.Type.FILES_SHOW_FILES,files));
                }else{
                    EventBus.getDefault().postSticky(new MainThreadEvent(MainThreadEvent.Type.ERR_GETFILES,jsonResult.getMsg()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                EventBus.getDefault().postSticky(new MainThreadEvent(MainThreadEvent.Type.ERR_GETFILES,t.toString()));
            }
        });
    }

}
