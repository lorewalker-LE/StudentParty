package com.zhbitsoft.studentparty.utils;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class HttpUtil {
    public static void sendOkHttpRequestPost( RequestBody requestBody,String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        //client.newBuilder()
               // .connectTimeout(30, TimeUnit.SECONDS)
              //  .readTimeout(20, TimeUnit.SECONDS).build();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }


}
