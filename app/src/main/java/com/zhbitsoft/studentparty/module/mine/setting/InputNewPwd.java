package com.zhbitsoft.studentparty.module.mine.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.module.beans.Student;
import com.zhbitsoft.studentparty.module.login.LoginActivity;
import com.zhbitsoft.studentparty.utils.HttpUtil;
import com.zhbitsoft.studentparty.utils.SharedPreferencesUtils;
import com.zhbitsoft.studentparty.widget.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InputNewPwd extends AppCompatActivity {

    private Student student = Student.getStudent();
    private ImageView back;
    private EditText newpassword;
    private LinearLayout submit;
    private String msg;
    private LoadingDialog mLoadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_new_pwd);
        init();
        event();
    }
    private void init(){
        back = findViewById(R.id.to_back);
        submit = findViewById(R.id.submit);
        newpassword = findViewById(R.id.newpwd);
    }
    private void event(){
        back.setOnClickListener(new NextClickListener());
        submit.setOnClickListener(new NextClickListener());
    }
    class NextClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.to_back:
                    finish();
                    break;
                case R.id.submit:
                    showLoading();
                    chgPwdRequest(student.getStudentId(),newpassword.getText().toString());
                    break;
                default:
                    break;
            }
        }
    }

    public void chgPwdRequest(final String accountNumber,final String newpassword){
        String url = "http://192.168.43.65:8080/studentParty/ChangePwdServlet";
        RequestBody requestBody = new FormBody.Builder()
                .add("AccountNumber",accountNumber)
                .add("NewPassword",newpassword)
                .build();
        HttpUtil.sendOkHttpRequestPost(requestBody,url,new okhttp3.Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
               msg="请检查网络";
                showToast();
                hideLoading();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    Log.d("response", responseData);
                    JSONObject jsonObject = (JSONObject) new JSONObject(responseData).get("updatePwd");
                    String result = jsonObject.getString("Result");
                    if (result.equals("success")){
                        msg="修改成功";
                        showToast();
                        SharedPreferencesUtils helper = new SharedPreferencesUtils(getBaseContext(), "setting");
                        helper.putValues( new SharedPreferencesUtils.ContentValue("autoLogin", false),
                                          new SharedPreferencesUtils.ContentValue("remenberPassword", false),
                                          new SharedPreferencesUtils.ContentValue("password", ""));

                        Intent intent = new Intent(InputNewPwd.this,LoginActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivity(intent);
                    }else {
                        msg="修改失败，请重试";
                        showToast();
                        hideLoading();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    msg="异常";
                    showToast();
                    hideLoading();
                }
            }
        });
    }
    public void showToast() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(InputNewPwd.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showLoading() {
        if (mLoadingDialog == null) {

            mLoadingDialog = new LoadingDialog(InputNewPwd.this, "正在登陆", false);
        }
        mLoadingDialog.show();
    }


    /**
     * 隐藏加载的进度框
     */
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
}
