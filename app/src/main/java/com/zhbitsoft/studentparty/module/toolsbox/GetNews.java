package com.zhbitsoft.studentparty.module.toolsbox;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.login.LoginActivity;
import com.zhbitsoft.studentparty.widget.LoadingDialog;
import com.zhbitsoft.studentparty.widget.TitleOnlyBack;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetNews extends AppCompatActivity {

    public final int UPDATE=1;

    private String msg;
    private TitleOnlyBack titleOnlyBack;
    private List<News> newsList = new ArrayList(){{this.add(new News(" "," ","2019-01-01 00:00:00"));}};
    private NewsAdapter adapter = new NewsAdapter(this,newsList);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_news);
        init();
        event();
    }

    private void init(){
        titleOnlyBack=findViewById(R.id.title);
        titleOnlyBack.setTitle("资讯");
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycle_view_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void event(){
        getNewsRequest();
    }
    public void getNewsRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = "http://192.168.43.65:8080/studentParty/GetNewsServlet";
                    Request request = new Request.Builder()
                            .url(url)
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (response.isSuccessful()){
                        Log.d("response", responseData);
                        Type listType = new TypeToken<List<News>>() {
                        }.getType();
                        newsList.clear();
                        Gson gson = new Gson();
                        if (gson.fromJson(responseData, listType) != null) {
                            List<News> webList = gson.fromJson(responseData, listType);
                            newsList.addAll(webList);
                        } else {
                            News news = new News();
                            news.setTitle(" ");
                            news.setImgPath("");
                            newsList.add(news);
                        }
                        Message message = new Message();
                        message.what = UPDATE;
                        handler.sendMessage(message);
                    }else {
                        throw new IOException("Unexpected code"+response);
                    }
                } catch (IOException e) {
                    if (e instanceof SocketTimeoutException) {
                        //判断超时异常
                        msg = "连接超时";
                        showToast();
                    }
                    if (e instanceof ConnectException) {
                        msg = "连接异常";
                        showToast();
                    }
                    e.printStackTrace();
                }

            } }).start();

    }
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE:
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onDestroy(){
        super.onDestroy();
        newsList.clear();
        adapter.notifyDataSetChanged();
    }
    public void showToast(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GetNews.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }


}
