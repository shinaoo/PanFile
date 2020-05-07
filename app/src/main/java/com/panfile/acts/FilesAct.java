package com.panfile.acts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.panfile.R;
import com.panfile.adapters.FilesAdapter;
import com.panfile.entity.json.PanFile;
import com.panfile.event.MainThreadEvent;
import com.panfile.service.PathService;
import com.panfile.utils.FileChooseUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilesAct extends Activity {

    private final int REQUEST_CHOOSEFILE = 1;

    private PathService pathService;
    private String pathCurrent = "";

    @BindView(R.id.rv_file_datas)
    RecyclerView rv_files;

    @BindView(R.id.tv_file_path)
    TextView tv_path;

    private FilesAdapter filesAdapter;

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
                openFileBrowser();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainThreadEvent(MainThreadEvent event){
        switch (event.getType()){
            case FILES_SHOW_FILES:
                filesAdapter.setFiles((List<PanFile>) event.getData());
                break;
            case FILES_FILE_CLICK:
                PanFile pf1 = (PanFile) event.getData();
                if (pf1.getFileType() == 1){
                    return;
                }
                this.pathCurrent = String.format("%s%s%s",this.pathCurrent, File.separator,pf1.getName());
                this.tv_path.setText(this.pathCurrent);
                this.pathService.getFiles(this.pathCurrent);
                break;
            case FILES_FILE_LONGCLICK:
                PanFile pf2 = (PanFile) event.getData();
                Log.e("MyTag","long click:" + pf2.getName());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CHOOSEFILE:
                    Uri uri = data.getData();
                    String path = FileChooseUtil.getInstance(this).getChooseFileResultPath(uri);
                    Log.e("MyTag","choose path:" + path);
                    break;
            }
        }
    }

    private void init(){
        pathService = new PathService();
        pathService.getFiles(pathCurrent);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv_files.setLayoutManager(linearLayoutManager);
        filesAdapter = new FilesAdapter(this);
        rv_files.setAdapter(filesAdapter);

        tv_path.setText(this.pathCurrent);
    }

    private void openFileBrowser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,REQUEST_CHOOSEFILE);
    }


}
