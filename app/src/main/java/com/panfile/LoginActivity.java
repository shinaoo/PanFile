package com.panfile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.panfile.event.BusEvent;
import com.panfile.service.ClientService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private EditText et_name,et_pass,et_token;
    private Button btn_login;

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

        et_name = findViewById(R.id.et_login_name);
        et_pass = findViewById(R.id.et_login_pass);
        et_token = findViewById(R.id.et_login_token);

        btn_login = findViewById(R.id.btn_login_login);

//        btn_login.setOnClickListener(v ->{
//            clientService.login(et_name.getText().toString(),et_pass.getText().toString(),et_token.getText().toString());
//        });
    }

    @OnClick({R.id.btn_login_login})
    public void onBtnClick(View v){
        Log.e("MyTag","login click");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void processEvent(BusEvent event){
        switch (event.getType()){
            case UNKNOWN:
                Log.e("MyTag","eventbus msg");
                break;
        }
    }

}
