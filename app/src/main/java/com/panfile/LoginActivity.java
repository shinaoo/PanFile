package com.panfile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.panfile.event.BusEvent;
import com.panfile.service.ClientService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_login_login)
    public void onClickEvent(View v){
        Log.e("MyTag","login click");
        switch (v.getId()){
            case R.id.btn_login_login:
                clientService.login(et_name.getText().toString(),et_pass.getText().toString(),et_token.getText().toString());
                break;
        }
    }

    private void init(){
        clientService = new ClientService();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void processEvent(BusEvent event){

    }

}
