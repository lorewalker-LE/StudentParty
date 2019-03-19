package com.zhbitsoft.studentparty.module.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zhbitsoft.studentparty.R;
import com.zhbitsoft.studentparty.main.Main2Activity;
import com.zhbitsoft.studentparty.module.beans.Student;
import com.zhbitsoft.studentparty.module.login.present.LoginPresent;
import com.zhbitsoft.studentparty.module.login.present.LoginPresentImpl;
import com.zhbitsoft.studentparty.module.login.view.LoginView;
import com.zhbitsoft.studentparty.utils.Base64Utils;
import com.zhbitsoft.studentparty.utils.SharedPreferencesUtils;
import com.zhbitsoft.studentparty.widget.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements LoginView,View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private EditText et_account;
    private EditText et_password;
    private Button mLoginBtn;
    private CheckBox checkBox_password;
    private CheckBox checkBox_login;
    private ImageView iv_see_password;
    private LoginPresent loginPresent;
    private String msg="";
    private LoadingDialog mLoadingDialog;

    // 返回的数据
    private String info;
    // 返回主线程更新数据
    private static Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresent = new LoginPresentImpl(this);
        setContentView(R.layout.activity_login);
        //初始化控件
        initViews();
        //初始化事件
        event();
        initData();

    }


    public void initData() {


        //判断用户第一次登陆
        if (loginPresent.firstLogin()) {
            checkBox_password.setChecked(false);//取消记住密码的复选框
            checkBox_login.setChecked(false);//取消自动登录的复选框
        }
        //判断是否记住密码
        if (loginPresent.remenberPassword()) {
            checkBox_password.setChecked(true);//勾选记住密码
            setTextNameAndPassword();//把密码和账号输入到输入框中
        } else {
            setTextName();//把用户账号放到输入账号的输入框中
        }

        //判断是否自动登录
        if (loginPresent.autoLogin()) {
            checkBox_login.setChecked(true);
            //先做一些基本的判断，比如输入的用户命为空，密码为空，网络不可用多大情况，都不需要去链接服务器了，而是直接返回提示错误
            if (getAccount().isEmpty()){
                msg="你输入的账号为空！";
                showToast();
                return;
            }

            if (getPassword().isEmpty()){
                msg="你输入的密码为空！";
                showToast();
                return;
            }
            //登录一般都是请求服务器来判断密码是否正确，要请求网络，要子线程
            showLoading();//显示加载框
            LoginRequest(getAccount(),getPassword());//去登录就可以

        }
    }



    /**
     * 把本地保存的数据设置数据到输入框中
     */
    public void setTextNameAndPassword() {
        et_account.setText("" + loginPresent.getLocalName());
        et_password.setText("" + loginPresent.getLocalPassword());
    }

    /**
     * 设置数据到输入框中
     */
    public void setTextName( ) {
        et_account.setText("" + loginPresent.getLocalName());
    }



    public void initViews() {
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        checkBox_password = (CheckBox) findViewById(R.id.checkBox_password);
        checkBox_login = (CheckBox) findViewById(R.id.checkBox_login);
        iv_see_password = (ImageView) findViewById(R.id.iv_see_password);
    }


    public void event(){
        mLoginBtn.setOnClickListener(this);
        checkBox_password.setOnCheckedChangeListener(this);
        checkBox_login.setOnCheckedChangeListener(this);
        iv_see_password.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                loginPresent.loadUserName(getAccount());    //无论如何保存一下用户名
                // 检测网络，无法检测wifi
                if (!checkNetwork()) {
                    Toast toast = Toast.makeText(LoginActivity.this,"网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
                }
                //先做一些基本的判断，比如输入的用户命为空，密码为空，网络不可用多大情况，都不需要去链接服务器了，而是直接返回提示错误
                if (getAccount().isEmpty()){
                    msg="你输入的账号为空！";
                    showToast();
                    return;
                }

                if (getPassword().isEmpty()){
                    msg="你输入的密码为空！";
                    showToast();
                    return;
                }
                //登录一般都是请求服务器来判断密码是否正确，要请求网络，要子线程
                showLoading();//显示加载框
                LoginRequest(getAccount(),getPassword()); //登陆
                break;
            case R.id.iv_see_password:
                setPasswordVisibility();    //改变图片并设置输入框的文本可见或不可见
                break;

        }
    }


    /**
     * 设置密码可见和不可见的相互转换
     */
    public void setPasswordVisibility() {
        if (iv_see_password.isSelected()) {
            iv_see_password.setSelected(false);
            //密码不可见
            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        } else {
            iv_see_password.setSelected(true);
            //密码可见
            et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }

    }

    /**
     * 获取账号
     */
    public String getAccount() {
        return et_account.getText().toString().trim();//去掉空格
    }

    /**
     * 获取密码
     */
    public String getPassword() {
        return et_password.getText().toString().trim();//去掉空格
    }


    /**
     * 保存用户选择“记住密码”和“自动登陆”的状态
     */
    private void loadCheckBoxState() {
        loginPresent.loadCheckBoxState(checkBox_password, checkBox_login,getPassword());
    }


    /**
     * 是否可以点击登录按钮
     *
     * @param clickable
     */
    public void setLoginBtnClickable(boolean clickable) {
        mLoginBtn.setClickable(clickable);
    }


    /**
     * 显示加载的进度款
     */
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(LoginActivity.this, getString(R.string.loading), false);
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


    /**
     * CheckBox点击时的回调方法 ,不管是勾选还是取消勾选都会得到回调
     *
     * @param buttonView 按钮对象
     * @param isChecked  按钮的状态
     */
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == checkBox_password) {  //记住密码选框发生改变时
            if (!isChecked) {   //如果取消“记住密码”，那么同样取消自动登陆
                checkBox_login.setChecked(false);
            }
        } else if (buttonView == checkBox_login) {   //自动登陆选框发生改变时
            if (isChecked) {   //如果选择“自动登录”，那么同样选中“记住密码”
                checkBox_password.setChecked(true);
            }
        }
    }
    /**
     * 监听回退键
     */
    @Override
    public void onBackPressed() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.cancel();
            } else {
                finish();
            }
        } else {
            finish();
        }

    }

    /**
     * 页面销毁前回调的方法
     */
    protected void onDestroy() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
            mLoadingDialog = null;
        }
        super.onDestroy();
    }


    public void showToast() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }


    // 检测网络
    private boolean checkNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    public  void LoginRequest(final String accountNumber, final String password) {
        //请求地址
        String url = "http://127.0.0.1:8080/WebApplication1/LoginServlet";
        String tag = "Login";    //注②
        setLoginBtnClickable(false);//点击登录后，设置登录按钮不可点击状态
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");

                            String result = jsonObject.getString("Result");

                            if (result.equals("success")) {
                                JSONObject stu = (JSONObject)new  JSONObject(response).get("student");
                                Student student=null;
                                student.setStudentId(stu.getString("studentId"));
                                student.setStudentName(stu.getString("studentName"));
                                student.setPassword(stu.getString("password"));
                                student.setSex(stu.getString("sex"));
                                student.setProfessional(stu.getString("professional"));
                                student.setClassId(stu.getString("classId"));
                                student.setCollegeId(stu.getString("collegeId"));
                                student.setStuTel(stu.getString("stuTel"));
                                loadCheckBoxState();//记录下当前用户记住密码和自动登录的状态
                                Intent i = new Intent(LoginActivity.this,Main2Activity.class);
                                i.putExtra("student",student);
                                startActivity(i);
                                finish();//关闭页面
                            } else {
                                msg="输入的登录账号或密码不正确";
                                showToast();
                            }
                        } catch (JSONException e) {
                            setLoginBtnClickable(true);  //这里解放登录按钮，设置为可以点击
                            hideLoading();//隐藏加载框
                            System.err.println(e.getMessage());//打印异常原因                   ==》  一般给用户看
                            System.err.println(e.toString());//打印异常名称以及异常原因  ==》 很少使用
                            e.printStackTrace();//打印异常原因+异常名称+出现异常的位置
                            msg="异常";
                            showToast();
                        }
                        setLoginBtnClickable(true);  //这里解放登录按钮，设置为可以点击
                        hideLoading();//隐藏加载框
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoginBtnClickable(true);  //这里解放登录按钮，设置为可以点击
                hideLoading();//隐藏加载框
                msg="网络异常，请检查网络";
                showToast();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AccountNumber", accountNumber);  //注⑥
                params.put("Password", password);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }
}

