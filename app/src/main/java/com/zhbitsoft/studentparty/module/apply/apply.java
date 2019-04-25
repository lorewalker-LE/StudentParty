package com.zhbitsoft.studentparty.module.apply;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.beans.Student;
import com.zhbitsoft.studentparty.utils.HideKeybordUtils;
import com.zhbitsoft.studentparty.widget.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class apply extends AppCompatActivity{
    private TextView mTvSelectedDate, mTvSelectedTime;
    private CustomDatePicker mDatePicker, mTimerPicker;
    private Student student=Student.getStudent();
    private RadioGroup radioGroup;
    private LinearLayout day;
    private LinearLayout time;
    private LinearLayout submit;
    private EditText reason;
    private ImageView back;
    private String msg;
    private String title="事假";
    private RadioButton radioButton;
    private LoadingDialog mLoadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        radioGroup=findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton=(RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                title=radioButton.getText().toString();
            }
        });
        reason=findViewById(R.id.content);
        back=findViewById(R.id.back);
        back.setOnClickListener(new submitOnclickListener());
        day=findViewById(R.id.bb_time);
        day.setOnClickListener(new submitOnclickListener());
        mTvSelectedDate = findViewById(R.id.bb_selected_time);
        initTimerPicker1();
        time=findViewById(R.id.ll_time);
        time.setOnClickListener(new submitOnclickListener());
        mTvSelectedTime = findViewById(R.id.tv_selected_time);
        initTimerPicker();
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new submitOnclickListener());
    }


    class submitOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.submit:
                    if (mTvSelectedTime.getText().toString().equals(mTvSelectedDate.getText().toString())){
                        msg="请选择正确的时间";
                        showToast();
                        break;
                    }
                    if (reason.getText().toString().trim().equals("")||reason.getText().toString()==null){
                        msg="请输入请假原因";
                        showToast();
                        break;
                    }
                    showLoading();
                   // setBtnClickable(false);
                    submit(title, reason.getText().toString(), mTvSelectedDate.getText().toString(), mTvSelectedTime.getText().toString(),student.getClassId(), student.getStudentId());
                    break;
               case R.id.back:
                        finish();
                        break;
                case R.id.bb_time:
                        // 日期格式为yyyy-MM-dd
                        mTimerPicker.show(mTvSelectedDate.getText().toString());
                        break;

                case R.id.ll_time:
                        // 日期格式为yyyy-MM-dd HH:mm
                        mTimerPicker.show(mTvSelectedTime.getText().toString());
                        break;
                default:
                    break;
            }
        }
    }
    private void initTimerPicker() {
        String beginTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        StringBuilder builder = new StringBuilder();
        builder.append(String.valueOf(year+1)).append("-").append(String.valueOf(month)).append("-").append(String.valueOf(day)).append(" ").append("00:00");
        String endTime = builder.toString();
        Log.d("test",endTime);
        mTvSelectedTime.setText(beginTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTvSelectedTime.setText(DateFormatUtils.long2Str(timestamp, true));
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true);
        // 显示时和分
        mTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mTimerPicker.setCanShowAnim(true);
    }

    private void initTimerPicker1() {
        String beginTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        StringBuilder builder = new StringBuilder();
        builder.append(String.valueOf(year+1)).append("-").append(String.valueOf(month)).append("-").append(String.valueOf(day)).append(" ").append("00:00");
        String endTime = builder.toString();
        Log.d("test",endTime);
        mTvSelectedDate.setText(beginTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTvSelectedDate.setText(DateFormatUtils.long2Str(timestamp, true));
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(true);
        // 显示时和分
        mDatePicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mDatePicker.setScrollLoop(true);
        // 允许滚动动画
        mDatePicker.setCanShowAnim(true);
    }

    public void submit(final String title,final String content,final String applyTime,final String backTime,final String handler,final String applyer){
        String url = "http://192.168.43.65:8080/studentParty/insertAppliServlet";
        RequestBody requestBody = new FormBody.Builder()
                .add("title",title)
                .add("content",content)
                .add("applyTime",applyTime)
                .add("backTime",backTime)
                .add("handler",handler)
                .add("applyer",applyer)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e instanceof SocketTimeoutException) {
                    //判断超时异常
                    msg = "连接超时";
                    showToast();
                   // setBtnClickable(true);
                }
                if (e instanceof ConnectException) {
                    msg = "连接异常";
                    showToast();
                    //setBtnClickable(true);
                }
                hideLoading();//隐藏加载框
                e.printStackTrace();//打印异常原因+异常名称+出现异常的位置
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            try {
                    String responseData = response.body().string();
                    Log.d("response", responseData);
                    hideLoading();
                   JSONObject jsonObject = (JSONObject) new JSONObject(responseData).get("feedback");
                   String result = jsonObject.getString("Result");
                    if (result.equals("success")){
                        msg="提交成功";
                        showToast();
                        hideLoading();
                        Intent intent = new Intent(apply.this,myApply.class);
                        startActivity(intent);
                    }else {
                        msg="提交失败,请稍后再试";
                        showToast();
                        hideLoading();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    msg = "异常";
                   // setBtnClickable(true);
                    showToast();
                    hideLoading();
                }
            }
        });
    }
    public void showToast(){
        runOnUiThread(new Runnable(){

            @Override
            public void run() {
                Toast.makeText(apply.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void showLoading() {
        if (mLoadingDialog == null) {

            mLoadingDialog = new LoadingDialog(apply.this, "正在提交", false);
        }
        mLoadingDialog.show();
    }
    public void hideLoading() {
        if (mLoadingDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.hide();
                }
            });

        }
    }
    //public void setBtnClickable(boolean clickable) {
      //  submit.setClickable(clickable);
   // }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                HideKeybordUtils.hideKeyboard(ev,view,apply.this);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    @Override
    protected void onDestroy(){
        if (mLoadingDialog != null){
            mLoadingDialog.dismiss();
        }
        super.onDestroy();
    }

}
