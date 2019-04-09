package com.zhbitsoft.studentparty.module.toolsbox;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.beans.Student;
import com.zhbitsoft.studentparty.utils.HttpUtil;
import com.zhbitsoft.studentparty.widget.TitleOnlyBack;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class getGrade extends AppCompatActivity {

    private static final int UPDATE_ITEM=1;
    private String msg;
    private int totalItem;
    private int term;
    private int per;
    private  List<Grade> gradeList = new ArrayList(){{
        this.add(new Grade("init",1));
    }};
    GradeAdapter adapter = new GradeAdapter(gradeList);
    private TitleOnlyBack titleOnlyBack;
    private Spinner spinner;
    private  RecyclerView recyclerView;
    private Student student = Student.getStudent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_grade);
        init();
        even();
    }


    private void init(){
        spinner = findViewById(R.id.spinner);
        titleOnlyBack = findViewById(R.id.title);
        titleOnlyBack.setTitle("成绩查询");
        String[] strs1=getTerm().toArray(new String[getTerm().size()]);
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,strs1);
        spinner.setAdapter(arrayAdapter);

        recyclerView = findViewById(R.id.recycle_view);
        adapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    private synchronized void even(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        getTerm_per(position);
                        getGradeRequest(student.getStudentId(),term,per);
                        Iterator<Grade> itera3tor =gradeList.iterator();
                        while (itera3tor.hasNext()){
                            Grade mySubject = itera3tor.next();
                            Log.d("tes",mySubject.getCourseName());
                        }
                        break;
                    case 1:
                        getTerm_per(position);
                        getGradeRequest(student.getStudentId(),term,per);
                        Iterator<Grade> iterator =gradeList.iterator();
                        while (iterator.hasNext()){
                            Grade mySubject = iterator.next();
                            Log.d("tes",mySubject.getCourseName());
                        }

                        break;
                    case 2:
                        getTerm_per(position);
                        getGradeRequest(student.getStudentId(),term,per);
                        Iterator<Grade> itor =gradeList.iterator();
                        while (itor.hasNext()){
                            Grade mySubject = itor.next();
                            Log.d("tes",mySubject.getCourseName());
                        }
                        break;
                    case 3:
                        getTerm_per(position);
                        getGradeRequest(student.getStudentId(),term,per);
                        break;
                    case 4:
                        getTerm_per(position);
                        getGradeRequest(student.getStudentId(),term,per);
                        break;
                    case 5:
                        getTerm_per(position);
                        getGradeRequest(student.getStudentId(),term,per);
                        break;
                    case 6:
                        getTerm_per(position);
                        getGradeRequest(student.getStudentId(),term,per);
                        break;
                    case 7:
                        getTerm_per(position);
                        getGradeRequest(student.getStudentId(),term,per);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private  List<String> getTerm(){
        List<String> term = new ArrayList<>();
        String s = student.getStudentId().substring(0,2);
        int t = Integer.parseInt(s);
        Calendar calendar = Calendar.getInstance();
        int year = (calendar.get(Calendar.YEAR))%100;
        int month = calendar.get(Calendar.MONTH);
        if (year==t){
            String str="大一第一学期";
            totalItem=1;
            term.add(str);
        }
        if (year-t==1){
            if (month<3){
                String str1="大一第一学期";
                totalItem=1;
                term.add(str1);
            }
            if (month>=9) {
                String str1="大一第一学期";
                String str2= "大一第二学期";
                String str3="大二第一学期";
                totalItem=3;
                term.add(str3);
                term.add(str2);
                term.add(str1);
            }
            if (month>3&&month<9){
                String str1="大一第一学期";
                String str2= "大一第二学期";
                totalItem=2;
                term.add(str2);
                term.add(str1);
            }
        }
        if (year-t==2) {
            if (month < 3) {
                String str1 = "大一第一学期";
                String str2 = "大一第二学期";
                String str3 = "大二第一学期";
                totalItem=3;
                term.add(str3);
                term.add(str2);
                term.add(str1);
            }
            if (month >= 9) {
                String str1 = "大一第一学期";
                String str2 = "大一第二学期";
                String str3 = "大二第一学期";
                String str4 = "大二第二学期";
                String str5 = "大三第一学期";
                totalItem=5;
                term.add(str5);
                term.add(str4);
                term.add(str3);
                term.add(str2);
                term.add(str1);
            }
            if (month > 3 && month < 9) {
                String str1 = "大一第一学期";
                String str2 = "大一第二学期";
                String str3 = "大二第一学期";
                String str4 = "大二第二学期";
                totalItem=4;
                term.add(str4);
                term.add(str3);
                term.add(str2);
                term.add(str1);
            }
        }
            if (year - t == 3) {
                if (month < 3) {
                    String str1 = "大一第一学期";
                    String str2 = "大一第二学期";
                    String str3 = "大二第一学期";
                    String str4 = "大二第二学期";
                    String str5 = "大三第一学期";
                    totalItem=5;
                    term.add(str5);
                    term.add(str4);
                    term.add(str3);
                    term.add(str2);
                    term.add(str1);
                }
                if (month >= 9) {
                    String str1 = "大一第一学期";
                    String str2 = "大一第二学期";
                    String str3 = "大二第一学期";
                    String str4 = "大二第二学期";
                    String str5 = "大三第一学期";
                    String str6 = "大三第二学期";
                    String str7 = "大四第一学期";
                    totalItem=7;
                    term.add(str7);
                    term.add(str6);
                    term.add(str5);
                    term.add(str4);
                    term.add(str3);
                    term.add(str2);
                    term.add(str1);
                }
                if (month > 3 && month < 9) {
                    String str1 = "大一第一学期";
                    String str2 = "大一第二学期";
                    String str3 = "大二第一学期";
                    String str4 = "大二第二学期";
                    String str5 = "大三第一学期";
                    String str6 = "大三第二学期";
                    totalItem=6;
                    term.add(str6);
                    term.add(str5);
                    term.add(str4);
                    term.add(str3);
                    term.add(str2);
                    term.add(str1);
                }

            }
            if (year - t == 4) {
                String str1 = "大一第一学期";
                String str2 = "大一第二学期";
                String str3 = "大二第一学期";
                String str4 = "大二第二学期";
                String str5 = "大三第一学期";
                String str6 = "大三第二学期";
                String str7 = "大四第一学期";
                String str8 = "大四第二学期";
                totalItem=8;
                term.add(str8);
                term.add(str7);
                term.add(str6);
                term.add(str5);
                term.add(str4);
                term.add(str3);
                term.add(str2);
                term.add(str1);
            }
            return term;
        }
    private void getTerm_per(int position){
        term=((int)Math.ceil(totalItem/2))-(position/2);
        per=2-(position%2);
    }
    public void getGradeRequest(final String studentId,final int term,final int per){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = "http://192.168.43.65:8080/studentParty/GetGradeServlet";
                    RequestBody requestBody = new FormBody.Builder()
                            .add("studentId", studentId)
                            .add("term", String.valueOf(term))
                            .add("per", String.valueOf(per))
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (response.isSuccessful()){
                        Log.d("response", responseData);
                        Type listType = new TypeToken<List<Grade>>() {
                        }.getType();
                        gradeList.clear();
                        Gson gson = new Gson();
                        if (gson.fromJson(responseData, listType) != null) {
                            List<Grade> webList = gson.fromJson(responseData, listType);
                            gradeList.addAll(webList);
                        } else {
                            Grade grade = new Grade();
                            grade.setCourseName("暂无结果");
                            grade.setGrade(1);
                            gradeList.add(grade);
                        }
                       Message message = new Message();
                        message.what = UPDATE_ITEM;
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
                case UPDATE_ITEM:
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
    }
    public void showToast(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getGrade.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
