package com.zhbitsoft.studentparty.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhbitsoft.studentparty.MySubject;
import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.beans.Student;
import com.zhbitsoft.studentparty.module.mine.MineFragment;
import com.zhbitsoft.studentparty.module.timetable.TimeTableFragment;
import com.zhbitsoft.studentparty.module.toolsbox.ToolsBoxFragment;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity {

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private String msg;
    private Student student = Student.getStudent();
    private void setDefaultFragment(){
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content,new ToolsBoxFragment());
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_toolsbox:
                    transaction.replace(R.id.content,new ToolsBoxFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_timetable:
                    transaction.replace(R.id.content,new TimeTableFragment());
                    transaction.commit();
                    return true;
                case R.id.navigation_mine:
                    transaction.replace(R.id.content,new MineFragment());
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSubjectRequest(student.getStudentId());
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setDefaultFragment();
    }
    public  void getSubjectRequest(final String studentId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = "http://192.168.43.65:8080/studentParty/stuTimeTableServlet";
                    // setBtnClickable(false);//点击登录后，设置登录按钮不可点击状态
                    RequestBody requestBody = new FormBody.Builder()
                            .add("studentId",studentId)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (response.isSuccessful()){
                        Log.d("response", responseData);
                        Type listType = new TypeToken<List<MySubject>>(){}.getType();
                        Gson gson = new Gson();
                        List<MySubject>listWeb = new ArrayList<>();
                        DataSupport.deleteAll(MySubject.class);
                        listWeb = gson.fromJson(responseData,listType);
                        if(listWeb!=null&&!listWeb.isEmpty()) {
                            Iterator<MySubject> iter = listWeb.iterator();
                            while (iter.hasNext()){
                                MySubject mySubject = iter.next();
                                List<MySubject> rep = DataSupport.select("name").where("name = ?",mySubject.getName()).find(MySubject.class);
                                if (rep.size()==0) {
                                    mySubject.save();
                                }
                            }
                        }

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
    public void showToast() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Main2Activity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
