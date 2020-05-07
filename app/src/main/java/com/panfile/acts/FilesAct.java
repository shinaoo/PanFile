package com.panfile.acts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.panfile.R;
import com.panfile.event.MainThreadEvent;
import com.panfile.service.PathService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilesAct extends Activity {

    private PathService pathService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_file);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.iv_file_back,R.id.iv_file_upload})
    public void onClickEvent(View v){
        switch (v.getId()){
            case R.id.iv_file_back:
                this.finish();
                break;
            case R.id.iv_file_upload:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainThreadEvent(MainThreadEvent event){

    }

    private void init(){
        pathService = new PathService();
    }

}
