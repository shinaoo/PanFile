package com.panfile.service;

import android.util.Log;

import com.panfile.model.network.FileSrv;
import com.panfile.model.network.RetrofitCls;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileService {

    private FileSrv fileSrv;
    private String pathSDCard;

    public FileService(String pathSDCard) {
        fileSrv = RetrofitCls.getInstance().getFileSrv();
        this.pathSDCard = pathSDCard;
    }

    public void uploadFile(String uploadPath) {
        new Thread(() -> {
            Log.e("MyTag","upload thread start" + uploadPath);
            File uFile = new File(uploadPath);
            if (!uFile.exists())
                return;
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("userName", "lanpa").addFormDataPart("projectName", "lanpa");
            File file = new File(uploadPath);
            RequestBody uploadBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("uploadFile", file.getName(), uploadBody);
            List<MultipartBody.Part> parts = builder.build().parts();

            Call<String> call = fileSrv.uploadFile(parts);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("MyTag", "upload :" + response.body());

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("MyTag", "upload failed:" + t.toString());
                }
            });
        }).start();
    }

    public void downloadFile(String path){
        new Thread(()->{
            Call<ResponseBody> download = fileSrv.download(path,"lanpa","lanpa");
            download.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response != null && response.isSuccessful()){
                        String saveFilePath = path;
                        saveFilePath = saveFilePath.substring(saveFilePath.lastIndexOf(File.separator),saveFilePath.length());
                        boolean toDisk = writeResponseBodyToDisk(response.body(),saveFilePath);
                        if (toDisk){
                            Log.e("MyTag","下载成功");
                        }else{
                            Log.e("MyTag","下载失败");
                        }
                    }else{
                        Log.e("MyTag","response is null or fail");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("MyTag","download fail:" + t.toString());
                }
            });
        }).start();

    }

    private boolean writeResponseBodyToDisk(ResponseBody body,String filename){
        try {
            //判断文件夹是否存在
            File files = new File(pathSDCard);//跟目录一个文件夹
            if (!files.exists()) {
                //不存在就创建出来
                files.mkdirs();
            }
            //创建一个文件
            File futureStudioIconFile = new File(pathSDCard, filename);
            //初始化输入流
            InputStream inputStream = null;
            //初始化输出流
            OutputStream outputStream = null;
            try {
                //设置每次读写的字节
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                //请求返回的字节流
                inputStream = body.byteStream();
                //创建输出流
                outputStream = new FileOutputStream(futureStudioIconFile);
                //进行读取操作
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    //进行写入操作
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }

                //刷新
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    //关闭输入流
                    inputStream.close();
                }
                if (outputStream != null) {
                    //关闭输出流
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
