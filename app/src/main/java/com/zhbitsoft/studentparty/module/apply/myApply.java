package com.zhbitsoft.studentparty.module.apply;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.beans.Student;
import com.zhbitsoft.studentparty.module.toolsbox.GetNotification;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class myApply extends AppCompatActivity {
    public final int UPDATE=1;
    private String msg;
    private Student student = Student.getStudent();
    private LinearLayout layout;
    private ImageView back;
    List<ApplyItem> myApplyList = new ArrayList(){{this.add(new ApplyItem("","暂无请假信息",0,0));}};
    myApplyAdapter adapter = new myApplyAdapter(this,myApplyList);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apply);
        layout=findViewById(R.id.qingjia);
        back=findViewById(R.id.back);
        getApplyRequest(student.getStudentId());
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycle_view_qingjia);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myApply.this,apply.class);
                startActivity(intent);
            }
        });
    }


    public void getApplyRequest(final String studentId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = "http://192.168.43.65:8080/studentParty/ApplyServlet";
                    RequestBody requestBody = new FormBody.Builder()
                            .add("type","select")
                            .add("applyer", studentId)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (response.isSuccessful()){
                        Log.d("response", responseData);
                        Type listType = new TypeToken<List<ApplyItem>>() {
                        }.getType();
                        Gson gson = new Gson();
                        if (gson.fromJson(responseData, listType) != null) {
                            List<ApplyItem> webList = gson.fromJson(responseData, listType);
                            myApplyList.clear();
                            myApplyList.addAll(webList);
                        }
                        for (ApplyItem applyItem:myApplyList){
                            Log.d("name",applyItem.content);
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
        adapter.notifyDataSetChanged();
    }
    public void showToast(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(myApply.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
