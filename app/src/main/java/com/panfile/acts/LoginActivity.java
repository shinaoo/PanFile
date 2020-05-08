package com.panfile.acts;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.panfile.R;
import com.panfile.event.BusEvent;
import com.panfile.event.MainThreadEvent;
import com.panfile.service.ClientService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private final int REQUEST_WRITE_STORAGE = 10;

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
        init_permission(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("MyTag","resultcode:" + resultCode);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_WRITE_STORAGE:
                    break;
            }
        }else{
            finish();
        }
    }

    private void init(){
        clientService = new ClientService();
    }

    @OnClick({R.id.btn_login_login})
    public void onBtnClick(View v){
        Log.e("MyTag","login click");
        clientService.login(et_name.getText().toString(),et_pass.getText().toString(),et_token.getText().toString());
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
            case ERR_LOGIN:
                String msg = (String) event.getData();
                Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void init_permission(Activity act){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //版本判断
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            }
        }
    }

}
