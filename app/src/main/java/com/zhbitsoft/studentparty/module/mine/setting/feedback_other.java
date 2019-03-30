package com.zhbitsoft.studentparty.module.mine.setting;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.utils.HideKeybordUtils;
import com.zhbitsoft.studentparty.widget.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class feedback_other extends AppCompatActivity {

    private EditText content;
    private EditText contact;
    private ImageView back;
    private LinearLayout submit;
    private String msg;
    private LoadingDialog mLoadingDialog;
    public static final String DEGREE_OTHER = "2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_other);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        event();
    }
    public void init(){
        content = findViewById(R.id.content);
        contact = findViewById(R.id.qq);
        back = (ImageView) findViewById(R.id.back);
        submit = (LinearLayout) findViewById(R.id.submit);
    }
    public void event(){
        submit.setOnClickListener(new submitOnclickListener());
        back.setOnClickListener(new submitOnclickListener());
    }

    class submitOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.submit:
                    if ("".equals(content.getText().toString())||content==null){
                        msg="内容不能为空";
                        showToast();
                        break;
                    }
                    if("".equals(contact.getText().toString())||contact==null){
                        msg="联系方式不能为空";
                        showToast();
                        break;
                    }
                    showLoading();
                    submit();
                    break;
                case R.id.back:
                    finish();
                    break;
                default:
                     break;
            }
        }
    }

    public void submit(){
        String url = "http://192.168.43.65:8080/studentParty/FeedbackServlet";
        MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
        StringBuilder stringBuilder = new StringBuilder("qq:");
        stringBuilder.append(contact.getText().toString());
        Map<String,String> params = new HashMap<>();
        params.put("degree",DEGREE_OTHER);
        params .put("content",content.getText().toString());
        params .put("contact",stringBuilder.toString());
        if (params != null && !params.isEmpty()){
            for (String key:params.keySet()){
                multiBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, params.get(key)));
            }
        }
        RequestBody mulitiBody = multiBuilder.build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(mulitiBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e instanceof SocketTimeoutException) {
                    //判断超时异常
                    msg = "连接超时";
                    showToast();
                    setBtnClickable(true);
                }
                if (e instanceof ConnectException) {
                    msg = "连接异常";
                    showToast();
                    setBtnClickable(true);
                }
                hideLoading();//隐藏加载框
                e.printStackTrace();//打印异常原因+异常名称+出现异常的位置
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    Log.d("response", responseData);
                    JSONObject jsonObject = (JSONObject) new JSONObject(responseData).get("feedback");
                    String result = jsonObject.getString("Result");
                    if (result.equals("success")){
                        msg="提交成功";
                        showToast();
                        hideLoading();
                        finish();
                    }else {
                        msg="提交失败,请稍后再试";
                        showToast();
                        hideLoading();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    msg = "异常";
                    setBtnClickable(true);
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
                Toast.makeText(feedback_other.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void showLoading() {
        if (mLoadingDialog == null) {

            mLoadingDialog = new LoadingDialog(feedback_other.this, "正在提交", false);
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
    public void setBtnClickable(boolean clickable) {
        submit.setClickable(clickable);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                HideKeybordUtils.hideKeyboard(ev,view,feedback_other.this);
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
