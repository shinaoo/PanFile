package com.panfile.acts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.panfile.R;
import com.panfile.event.BusEvent;
import com.panfile.event.MainThreadEvent;
import com.panfile.service.ClientService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_login_name)
    EditText et_name;
    @BindView(R.id.et_login_pass)
    EditText et_pass;
    @BindView(R.id.et_login_token)
    EditText et_token;

    private ClientService clientService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void init(){
        clientService = new ClientService();
    }

    @OnClick({R.id.btn_login_login})
    public void onBtnClick(View v){
        Log.e("MyTag","login click");
        EventBus.getDefault().postSticky(new MainThreadEvent(MainThreadEvent.Type.ACT_LOGIN_2_FILES,""));
//        clientService.login(et_name.getText().toString(),et_pass.getText().toString(),et_token.getText().toString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void processEvent(BusEvent event){
        switch (event.getType()){
            case UNKNOWN:
                Log.e("MyTag","eventbus msg");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainThreadEvent(MainThreadEvent event){
        switch (event.getType()){
            case ACT_LOGIN_2_FILES:
                startActivity(new Intent(this,FilesAct.class));
                break;
        }
    }

}
